package pl.ms.saper.app.exceptions

class InvalidUserEmailException: AppExceptions {
    constructor() : super("No email specified")
    constructor(email: String) : super("User with this email($email) does not exists")
}