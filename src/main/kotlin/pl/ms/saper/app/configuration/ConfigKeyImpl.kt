package pl.ms.saper.app.configuration

import pl.ms.saper.app.exceptions.InvalidValueException

enum class ConfigKeyImpl: ConfigKey {

    WIDTH {

        override val displayName: String
            get() = "This configuration set width of field"
        override val defaultValue: String
            get() = "8"

        override fun validateValue(newValue: String) {
            try {
                newValue.toInt()
            }
            catch (ex: NumberFormatException) {
                throw InvalidValueException(newValue)
            }
        }

    },
    HEIGHT {

        override val displayName: String
            get() = "This configuration set height of field"
        override val defaultValue: String
            get() = "8"

        override fun validateValue(newValue: String) {
            try {
                newValue.toInt()
            }
            catch (ex: NumberFormatException) {
                throw InvalidValueException(newValue)
            }
        }

    },
    MINES {

        override val displayName: String
            get() = "This configuration set how many mines will be on the field"
        override val defaultValue: String
            get() = "24"

        override fun validateValue(newValue: String) {
            try {
                newValue.toInt()
            }
            catch (ex: NumberFormatException) {
                throw InvalidValueException(newValue)
            }
        }

    };

    override val configName: String
        get() = this.name
}