package pl.ms.saper.app.exceptions

class ConfigNotFoundException(userId: Int): AppExceptions("Config not found for user with this id: $userId")