package pl.ms.saper.web.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pl.ms.saper.app.services.GameInteractor
import pl.ms.saper.web.models.request.PositionModel
import pl.ms.saper.web.models.request.toBusiness
import javax.validation.Valid

@Validated
@CrossOrigin
@RestController
class GameController {

    @Autowired
    private lateinit var gameInteractor: GameInteractor

    @PostMapping("/api/v1/check")
    fun checkSpot(
        @Valid @RequestBody position: PositionModel
    ) = gameInteractor.checkSpot(position.toBusiness())

    @PostMapping("/api/v1/flag")
    fun flagSpot(
        @Valid @RequestBody position: PositionModel
    ) = gameInteractor.flagSpot(position.toBusiness())

    @PostMapping("/api/v1/reveal")
    fun revealSpot(
        @Valid @RequestBody position: PositionModel
    ) = gameInteractor.revealClickedSpot(position.toBusiness())

}