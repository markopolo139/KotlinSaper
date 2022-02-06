package pl.ms.saper.web.utils

import org.springframework.security.core.context.SecurityContextHolder
import pl.ms.saper.app.security.CustomUser

fun getUserIdFromContext(): Int = ((SecurityContextHolder.getContext().authentication.principal) as CustomUser).userId