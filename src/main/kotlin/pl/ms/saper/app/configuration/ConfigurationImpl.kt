package pl.ms.saper.app.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import pl.ms.saper.app.converters.toEntity
import pl.ms.saper.app.data.embeddable.ConfigEntryEmbeddable
import pl.ms.saper.app.data.repositories.ConfigRepository
import pl.ms.saper.app.exceptions.ConfigNotFoundException
import pl.ms.saper.app.security.CustomUser

class ConfigurationImpl: Configuration {

    companion object {
        private const val DEFAULT_CONFIG_NAME = "custom_config"
    }

    @Autowired
    private lateinit var configRepository: ConfigRepository

    private val currentUserId: Int
        get() = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

    override fun setConfigName(newName: String) {
        val currentConfig = getCurrentConfigurationEntity()
        currentConfig.name = newName
        configRepository.save(currentConfig)
    }

    override fun defaultConfigName() {
        val currentConfig = getCurrentConfigurationEntity()
        currentConfig.name = DEFAULT_CONFIG_NAME
        configRepository.save(currentConfig)
    }

    override fun getConfigName(): String = getCurrentConfigurationEntity().name

    override fun saveValue(configKey: ConfigKey, value: String) {

        configKey.validateValue(value)
        validateSaveValue(value, configKey)

        deleteValue(configKey)

        val currentConfig = getCurrentConfigurationEntity()
        val newEntry = ConfigEntryEmbeddable(configKey.configName, value)
        currentConfig.configEntries.add(newEntry)

        configRepository.save(currentConfig)
    }

    override fun getValue(configKey: ConfigKey): String = getCurrentConfigurationEntity()
        .configEntries.find { it.entryName == configKey.configName }?.value ?: configKey.defaultValue

    override fun deleteValue(configKey: ConfigKey) {
        val currentConfig = getCurrentConfigurationEntity()
        configRepository.deleteByConfigKeyAndConfigId(currentConfig.id, configKey.configName)
    }

    override fun getEntry(configKey: ConfigKey): ConfigEntry {
        val currentConfig = getCurrentConfigurationEntity()
        return ConfigEntryImpl(
            configKey,
            currentConfig.configEntries.find { it.entryName == configKey.configName }?.value ?: configKey.defaultValue
        )
    }

    override fun getEntries(): Set<ConfigEntry> {
        val currentConfig = getCurrentConfigurationEntity()
        return currentConfig.configEntries.asSequence().map { it.toEntity() }.toSet()
    }

    private fun getCurrentConfigurationEntity() =
        configRepository.findByUserId(currentUserId).orElseThrow { throw ConfigNotFoundException(currentUserId) }

    private fun validateSaveValue(saveValue: String, configKey: ConfigKey) {

        if (configKey.configName == ConfigKeyImpl.HEIGHT.configName)
            fieldValidation(
                getValue(ConfigKeyImpl.WIDTH).toInt(), saveValue.toInt(), getValue(ConfigKeyImpl.MINES).toInt(), configKey
            )
        else {
            if (configKey.configName == ConfigKeyImpl.MINES.configName)
                fieldValidation(
                    getValue(ConfigKeyImpl.WIDTH).toInt(), saveValue.toInt(), getValue(ConfigKeyImpl.MINES).toInt(), configKey
                )
            else
                if (configKey.configName == ConfigKeyImpl.WIDTH.configName)
                    fieldValidation(
                        getValue(ConfigKeyImpl.WIDTH).toInt(), saveValue.toInt(), getValue(ConfigKeyImpl.MINES).toInt(), configKey
                    )
        }

    }
}