package pl.ms.saper.app.utils

import org.springframework.security.core.userdetails.UserDetails
import pl.ms.saper.app.security.CustomUser

fun UserDetails.toCustomUser(): CustomUser = CustomUser(username, password, authorities.toMutableSet())