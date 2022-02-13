package pl.ms.saper.app.configuration

interface ConfigEntry {
    val key: ConfigKey
    var value: String
}