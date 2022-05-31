package pl.ms.saper.web.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.data.entites.BoardEntity
import pl.ms.saper.app.data.entites.ConfigEntity
import pl.ms.saper.app.data.entites.UserEntity
import pl.ms.saper.app.data.repositories.BoardRepository
import pl.ms.saper.app.data.repositories.ConfigRepository
import pl.ms.saper.app.security.CustomUser

@CrossOrigin
@RestController
@Validated
class Test {

    @Autowired
    lateinit var configRepository: ConfigRepository

    @Autowired
    lateinit var boardRepository: BoardRepository

    @GetMapping("/test")
    fun test() {
        boardRepository.save(BoardEntity(
            0, UserEntity(0, "test", "test", "email"), mutableSetOf(),
            ConfigEntity(
                0, "test", boardEntity = null
            )
        ))
    }

    @GetMapping("/test/get/id")
    fun test2(): Int {
        return (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
    }
}