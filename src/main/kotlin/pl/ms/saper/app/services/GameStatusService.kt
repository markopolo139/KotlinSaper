package pl.ms.saper.app.services

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.app.converters.toModelList
import pl.ms.saper.app.data.repositories.BoardRepository
import pl.ms.saper.app.entities.GameConfiguration
import pl.ms.saper.app.entities.Spot
import pl.ms.saper.app.exceptions.BoardNotFoundException
import pl.ms.saper.app.security.CustomUser
import pl.ms.saper.web.models.request.SpotModel

//TODO: getGameConfiguration(height, mines, width)

@Service
class GameStatusService {

    private lateinit var configuration: Configuration
    private lateinit var boardRepository: BoardRepository
    private lateinit var spotRepository: BoardRepository

    private val userId
        get() = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId

    val isWin: Boolean
        get() {
            return false
        }

    val isLose: Boolean
        get() {
            return false
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