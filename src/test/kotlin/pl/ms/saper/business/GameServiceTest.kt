package pl.ms.saper.business

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.ms.saper.business.exceptions.InvalidPositionException
import pl.ms.saper.business.exceptions.SpotCheckedException
import pl.ms.saper.business.exceptions.SpotFlaggedException
import pl.ms.saper.business.exceptions.SpotMinedException
import pl.ms.saper.business.mock.BoardMock
import pl.ms.saper.business.mock.SpotMock
import pl.ms.saper.business.services.GameService
import pl.ms.saper.business.values.Position

@SpringBootTest(classes = [
    GameService::class
])
class GameServiceTest {

    @Autowired
    private lateinit var gameService: GameService

    @Test
    @DisplayName("Check test")
    fun `test check function`() {

        val board = BoardMock(mutableMapOf(
            Position(1,1) to SpotMock(position = Position(1,1), false, false, false, 0),
            Position(2,1) to SpotMock(position = Position(2,1), true, true, true, 0),
            Position(3,1) to SpotMock(position = Position(3,1), false, true, false, 0),
            Position(4,1) to SpotMock(position = Position(4,1), false, false, true, 0)
        ))

        Assertions.assertThrows(InvalidPositionException::class.java) { gameService.check(Position(0,0), board) }
        Assertions.assertThrows(SpotCheckedException::class.java) { gameService.check(Position(2,1), board) }
        Assertions.assertThrows(SpotMinedException::class.java) { gameService.check(Position(3,1), board) }
        Assertions.assertThrows(SpotFlaggedException::class.java) { gameService.check(Position(4,1), board) }

        Assertions.assertDoesNotThrow { gameService.check(Position(1,2), board) }

        val spot = gameService.check(Position(1,2), board).first()
        Assertions.assertEquals(true, spot.isChecked)
        Assertions.assertEquals(1, spot.minesAround)
        Assertions.assertEquals(Position(1,2), spot.position)


    }

}