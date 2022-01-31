package pl.ms.saper.app.exceptions

import org.springframework.security.core.userdetails.UsernameNotFoundException

class InvalidUserException: UsernameNotFoundException {

    constructor() : super("No username specified")
    constructor(username: String): super("Invalid username: $username")

}