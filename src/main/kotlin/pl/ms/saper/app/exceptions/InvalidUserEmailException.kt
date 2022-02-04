package pl.ms.saper.app.exceptions

class InvalidUserEmailException: AppExceptions {
    constructor() : super("No email specified")
    constructor(email: String) : super("User with this email does not exists")
}