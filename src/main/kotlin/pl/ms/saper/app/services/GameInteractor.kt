package pl.ms.saper.app.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.app.converters.toBusiness
import pl.ms.saper.app.converters.toData
import pl.ms.saper.app.data.embeddable.SpotStatus
import pl.ms.saper.app.data.entites.BoardEntity
import pl.ms.saper.app.data.entites.SpotEntity
import pl.ms.saper.app.data.repositories.BoardRepository
import pl.ms.saper.app.data.repositories.SpotRepository
import pl.ms.saper.app.entities.Board
import pl.ms.saper.app.entities.Spot
import pl.ms.saper.app.exceptions.BoardNotFoundException
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.business.services.GameService
import pl.ms.saper.business.values.Position

@Service
class GameInteractor {

    private val gameService: GameService = GameService()

    @Autowired
    private lateinit var boardRepository: BoardRepository

    @Autowired
    private lateinit var spotRepository: SpotRepository

    @Autowired
    private lateinit var configuration: Configuration

    private val userId: Int
        get() = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

    fun checkSpot(position: Position) {
        val board = boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }.toBusiness(configuration)
        val editedSpots = gameService.check(position, board).map { (it as Spot).toData() }
        val boardEntity = board.toData()
        boardEntity.spots.addAll(editedSpots)
        spotRepository.saveAll(editedSpots)
        //boardRepository.save(boardEntity)
    }

    fun flagSpot(position: Position) {
        val board = boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }.toBusiness(configuration)
        val editedSpot = ( gameService.flag(position, board) as Spot ).toData()
        val boardEntity = board.toData()
        boardEntity.spots.add(editedSpot)
        spotRepository.save(editedSpot)
        //boardRepository.save(boardEntity)
    }

    fun revealClickedSpot(position: Position) {
        val board = boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }.toBusiness(configuration)
        val editedSpots = gameService.revealOnClicked(position, board).map { (it as Spot).toData() }
        val boardEntity = board.toData()
        boardEntity.spots.addAll(editedSpots)
        spotRepository.saveAll(editedSpots)
        //boardRepository.save(boardEntity)
    }

    fun Spot.toData() =
        SpotEntity(spotId, position.toData(), SpotStatus(isMined, isChecked, isFlagged), minesAround, boardRepository.getById(boardId))

    fun Board.toData() =
        BoardEntity(boardId, userEntity, spotMap.values.asSequence().map { (it as Spot).toData() }.toMutableSet(), configEntity)

}