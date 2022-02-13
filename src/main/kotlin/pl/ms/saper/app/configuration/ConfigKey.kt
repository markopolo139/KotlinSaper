package pl.ms.saper.app.configuration

interface ConfigKey {
    val configName: String
    val displayName: String
    val defaultValue: String

    fun validateValue(newValue: String)

    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}