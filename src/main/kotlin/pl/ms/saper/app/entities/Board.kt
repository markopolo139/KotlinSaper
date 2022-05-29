package pl.ms.saper.app.entities

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.app.configuration.ConfigurationImpl
import pl.ms.saper.app.data.entites.ConfigEntity
import pl.ms.saper.app.data.entites.UserEntity
import pl.ms.saper.business.entities.Board
import pl.ms.saper.business.exceptions.InvalidPositionException
import pl.ms.saper.business.values.Position
import pl.ms.saper.business.values.Spot
import javax.annotation.PostConstruct

class Board(
    val boardId: Int,
    val userEntity: UserEntity,
    override var spotMap: MutableMap<Position, Spot>,
    val configEntity: ConfigEntity,
    val configuration: Configuration
) : Board {

    override fun validatePosition(position: Position) {
        if (
            !(position.x in 1..configuration.getValue(ConfigKeyImpl.WIDTH).toInt()
                    && position.y in 1..configuration.getValue(ConfigKeyImpl.HEIGHT).toInt())
        )
            throw InvalidPositionException(position)
    }

    override fun getSpot(position: Position): Spot {
        validatePosition(position)

        return spotMap[position]
            ?: Spot(0, position, isChecked = false, isMined = false, isFlagged = false, 0, boardId)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as pl.ms.saper.app.entities.Board

        if (boardId != other.boardId) return false
        if (userEntity != other.userEntity) return false
        if (spotMap != other.spotMap) return false
        if (configEntity != other.configEntity) return false
        if (configuration != other.configuration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = boardId
        result = 31 * result + userEntity.hashCode()
        result = 31 * result + spotMap.hashCode()
        result = 31 * result + configEntity.hashCode()
        result = 31 * result + configuration.hashCode()
        return result
    }


}