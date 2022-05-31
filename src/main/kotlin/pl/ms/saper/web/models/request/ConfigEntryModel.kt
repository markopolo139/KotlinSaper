package pl.ms.saper.web.models.request

import org.jetbrains.annotations.NotNull
import pl.ms.saper.web.validation.ValidConfigurationKey
import javax.validation.Valid

class ConfigEntryModel(
    @field:Valid @field:ValidConfigurationKey val configKey: String,
    @field:NotNull val value: String
)