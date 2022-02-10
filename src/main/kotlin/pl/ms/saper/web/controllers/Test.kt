package pl.ms.saper.web.controllers

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.exceptions.InvalidUserException
import pl.ms.saper.app.security.CustomUser
import java.lang.Exception

@CrossOrigin
@RestController
@Validated
class Test {

    @GetMapping("/test")
    fun test(): String {
        return "TEST"
    }

    @GetMapping("/test/get/id")
    fun test2(): Int {
        return (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
    }
}