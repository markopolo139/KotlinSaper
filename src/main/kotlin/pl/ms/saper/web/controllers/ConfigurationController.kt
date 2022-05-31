package pl.ms.saper.web.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.ms.saper.app.configuration.ConfigEntry
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.web.models.request.ConfigEntryModel
import pl.ms.saper.web.validation.ValidConfigurationKey
import javax.validation.Valid

@CrossOrigin
@RestController
@Validated
class ConfigurationController {

    @Autowired
    private lateinit var configuration: Configuration

    @PutMapping("/api/v1/set/config/name/{name}")
    fun setConfigName(@PathVariable("name") name: String) = configuration.setConfigName(name)

    @PutMapping("/api/v1/set/default/name")
    fun setDefaultName() = configuration.defaultConfigName()

    @GetMapping("/api/v1/get/config/name")
    fun getConfigName() = configuration.getConfigName()

    @PostMapping("/api/v1/configuration/entry/save")
    fun saveNewConfigEntry(
        @RequestBody configEntryModel: ConfigEntryModel
    ) = configuration.saveValue(ConfigKeyImpl.valueOf(configEntryModel.configKey), configEntryModel.value)

    @GetMapping("/api/v1/configuration/get/value")
    fun getValue(
        @RequestParam @Valid @ValidConfigurationKey configurationKey: String
    ) = configuration.getValue(ConfigKeyImpl.valueOf(configurationKey))

    @DeleteMapping("/api/v1/configuration/delete/value")
    fun deleteValue(
        @RequestParam @Valid @ValidConfigurationKey configurationKey: String
    ) = configuration.deleteValue(ConfigKeyImpl.valueOf(configurationKey))

    @GetMapping("/api/v1/configuration/get/entry")
    fun getEntry(
        @RequestParam @Valid @ValidConfigurationKey configurationKey: String
    ) = configuration.getEntry(ConfigKeyImpl.valueOf(configurationKey)).toConfigEntryModel()

    @GetMapping("/api/v1/configuration/get/all/entries")
    fun getAllEntry() =
        configuration.getEntries().asSequence().map { it.toConfigEntryModel() }.toSet()

    fun ConfigEntry.toConfigEntryModel() = ConfigEntryModel(key.configName, value)
}