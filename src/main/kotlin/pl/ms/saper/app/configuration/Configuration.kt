package pl.ms.saper.app.configuration

import pl.ms.saper.app.exceptions.InvalidValueException

interface Configuration {

    fun setConfigName(newName: String)

    fun defaultConfigName()

    fun getConfigName(): String

    fun saveValue(configKey: ConfigKey, value: String)

    fun getValue(configKey: ConfigKey): String

    fun deleteValue(configKey: ConfigKey)

    fun getEntry(configKey: ConfigKey): ConfigEntry

    fun getEntries(): Set<ConfigEntry>

    fun fieldValidation(width: Int, height: Int, mines: Int, configKey: ConfigKey) {
        if (width * height <= mines)
            throw InvalidValueException(configKey.configName)
    }

}