package pl.ms.saper.web.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.services.GameStartService
import pl.ms.saper.business.values.Position
import pl.ms.saper.web.models.request.PositionModel
import javax.validation.Valid

@CrossOrigin
@RestController
@Validated
class GameStartController {

    @Autowired
    private lateinit var gameStartService: GameStartService

    @GetMapping("/api/v1/board/exists")
    fun doesBoardExists() = gameStartService.doesBoardExists

    @PostMapping("/api/v1/game/start")
    fun startGame(
        @Valid @RequestBody position: PositionModel
    ) = gameStartService.createGame(Position(position.x, position.y))
}