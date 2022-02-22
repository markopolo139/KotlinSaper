package pl.ms.saper.app.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    username: String,
    password: String,
    roles: Set<GrantedAuthority>,
    var userId: Int
): User(username, password, roles)