package pl.ms.saper.web.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.services.GameStatusService

@Validated
@CrossOrigin
@RestController
class GameStatusController {

    @Autowired
    private lateinit var gameStatusService: GameStatusService

    @GetMapping("/api/v1/is/win")
    fun isWin() = gameStatusService.isWin

    @GetMapping("/api/v1/is/lose")
    fun isLose() = gameStatusService.isLose

    @GetMapping("/api/v1/get/configuration")
    fun getConfiguration() = gameStatusService.getCurrentConfig()

    @GetMapping("/api/v1/get/board/all")
    fun getAllBoard() = gameStatusService.getAllSpots()

    @GetMapping("/api/v1/get/board")
    fun getSpotsCheckedAndFlagged() = gameStatusService.getCheckedOrFlagged()

}