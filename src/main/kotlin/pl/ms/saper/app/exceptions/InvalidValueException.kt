package pl.ms.saper.app.exceptions

class InvalidValueException(invalidValue: String): AppExceptions("Typed value is invalid ($invalidValue)") {
}