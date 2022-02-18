package pl.ms.saper.app.configuration

import pl.ms.saper.app.exceptions.InvalidValueException

interface Configuration {

    var configName: String

    fun saveValue(boardId: Int, configKey: ConfigKey, value: String)

    fun getValue(boardId: Int, configKey: ConfigKey)

    fun deleteValue(boardId: Int, configKey: ConfigKey)

    fun getEntry(boardId: Int, configKey: ConfigKey)

    fun getEntries(boardId: Int)

    private fun fieldValidation(width: Int, height: Int, mines: Int, configKey: ConfigKey) {
        if (width * height <= mines)
            throw InvalidValueException(configKey.configName)
    }

}