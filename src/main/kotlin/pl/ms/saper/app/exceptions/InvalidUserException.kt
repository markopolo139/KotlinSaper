package pl.ms.saper.app.exceptions

class InvalidUserException: AppExceptions {

    constructor() : super()
    constructor(username: String): super("Invalid username: $username")

}