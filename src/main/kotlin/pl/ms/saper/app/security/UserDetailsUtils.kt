package pl.ms.saper.app.security

import org.springframework.security.core.userdetails.UserDetails

fun UserDetails.toCustomUser(): CustomUser = CustomUser(username, password, authorities.toMutableSet())