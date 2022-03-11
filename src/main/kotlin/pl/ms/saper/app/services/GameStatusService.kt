package pl.ms.saper.app.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.app.converters.toBusiness
import pl.ms.saper.app.converters.toModelList
import pl.ms.saper.app.data.repositories.BoardRepository
import pl.ms.saper.app.entities.GameConfiguration
import pl.ms.saper.app.entities.Spot
import pl.ms.saper.app.exceptions.BoardNotFoundException
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.web.models.request.SpotModel

@Service
class GameStatusService {

    @Autowired
    private lateinit var configuration: Configuration

    @Autowired
    private lateinit var boardRepository: BoardRepository

    @Autowired
    private lateinit var spotRepository: BoardRepository

    private val userId
        get() = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

    val isWin: Boolean
        get() {
            val userBoard = boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }.toBusiness()
            val width = configuration.getValue(ConfigKeyImpl.WIDTH).toInt()
            val height = configuration.getValue(ConfigKeyImpl.HEIGHT).toInt()
            val mines = configuration.getValue(ConfigKeyImpl.MINES).toInt()
            return (userBoard.minesLeft == 0 && userBoard.allSpots == width * height)
                    || (!isLose && userBoard.spotChecked == width * height - mines)
        }

    val isLose: Boolean
        get() {
            val userBoard = boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }
            return userBoard.spots.any { it.spotStatus.isMined && it.spotStatus.isChecked }
        }

    fun getAllSpots(): List<SpotModel> {
        if (isLose || isWin)
            return boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }.spots.toModelList();

        return emptyList();
    }

    fun getCheckedOrFlagged(): List<SpotModel> {
            return boardRepository.findByUser_UserId(userId).orElseThrow { throw BoardNotFoundException() }.spots.asSequence()
                .filter { it.spotStatus.isChecked || it.spotStatus.isFlagged}.toMutableSet().toModelList()
    }

    fun getCurrentConfig(): GameConfiguration {
        return GameConfiguration(
            configuration.getValue(ConfigKeyImpl.HEIGHT).toInt(),
            configuration.getValue(ConfigKeyImpl.WIDTH).toInt(),
            configuration.getValue(ConfigKeyImpl.MINES).toInt()
        )
    }
}