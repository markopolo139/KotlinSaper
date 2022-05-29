package pl.ms.saper.app.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.app.converters.toData
import pl.ms.saper.app.data.embeddable.PositionEmbeddable
import pl.ms.saper.app.data.embeddable.SpotStatus
import pl.ms.saper.app.data.entites.BoardEntity
import pl.ms.saper.app.data.entites.ConfigEntity
import pl.ms.saper.app.data.entites.SpotEntity
import pl.ms.saper.app.data.repositories.BoardRepository
import pl.ms.saper.app.data.repositories.ConfigRepository
import pl.ms.saper.app.data.repositories.SpotRepository
import pl.ms.saper.app.data.repositories.UserRepository
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.business.values.Position
import kotlin.random.Random

@Service
class GameStartService {

    @Autowired
    private lateinit var configuration: Configuration

    @Autowired
    private lateinit var boardRepository: BoardRepository

    @Autowired
    private lateinit var spotRepository: SpotRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var gameInteractor: GameInteractor

    @Autowired
    private lateinit var configRepository: ConfigRepository

    companion object {
        private const val RANDOM_SEED = 11122233
    }

    private val userId
        get() = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

    val doesBoardExists: Boolean
        get() = boardRepository.findByUser_UserId(userId).isPresent

    fun createGame(position: Position) {

        var newBoard = boardRepository.findByUser_UserId(userId).orElse(
            BoardEntity(0, userRepository.getById(userId), mutableSetOf(), ConfigEntity(0, "Default"))
        )

        val configurationToSave = newBoard.configuration
        newBoard.configuration = null

        spotRepository.deleteAllSpotsByBoardId(newBoard.id)
        newBoard.spots.clear()
        newBoard = boardRepository.save(newBoard)
        newBoard.configuration = configurationToSave

        generateMines(newBoard, position)

        boardRepository.save(newBoard)

    }

    private fun generateMines(board: BoardEntity, position: Position) {

        val height = configuration.getValue(ConfigKeyImpl.HEIGHT).toInt()
        val width = configuration.getValue(ConfigKeyImpl.WIDTH).toInt()
        var mines = configuration.getValue(ConfigKeyImpl.MINES).toInt()
        val randomGenerator = Random(RANDOM_SEED)

        while (mines > 0) {
            val randomPosition = Position(
                randomGenerator.nextInt(1, width + 1), randomGenerator.nextInt(1, height + 1)
            )

            if (randomPosition == position || board.spots.any { it.position == randomPosition.toData() })
                continue

            val newSpot = SpotEntity(0, randomPosition.toData(), SpotStatus(true, isChecked = false, isFlagged = false), 0, board)
            board.spots.add(newSpot)

            mines--

        }

        spotRepository.saveAll(board.spots)
        gameInteractor.checkSpot(position)

    }

}