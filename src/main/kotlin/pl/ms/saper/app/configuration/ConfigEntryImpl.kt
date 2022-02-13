package pl.ms.saper.app.configuration

class ConfigEntryImpl(
    override val key: ConfigKey,
    override var value: String
): ConfigEntry