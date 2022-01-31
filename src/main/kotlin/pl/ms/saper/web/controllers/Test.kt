package pl.ms.saper.web.controllers

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.exceptions.InvalidUserException
import java.lang.Exception

@CrossOrigin
@RestController
@Validated
class Test {

    @GetMapping("/test")
    fun test(): String {
        return "TEST"
    }
}