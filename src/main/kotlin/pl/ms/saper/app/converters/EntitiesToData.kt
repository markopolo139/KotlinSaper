package pl.ms.saper.app.converters

import org.springframework.beans.factory.annotation.Autowired
import pl.ms.saper.app.configuration.ConfigEntryImpl
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.configuration.Configuration
import pl.ms.saper.app.data.embeddable.ConfigEntryEmbeddable
import pl.ms.saper.app.data.embeddable.PositionEmbeddable
import pl.ms.saper.app.data.embeddable.SpotStatus
import pl.ms.saper.app.data.entites.BoardEntity
import pl.ms.saper.app.data.entites.ConfigEntity
import pl.ms.saper.app.data.entites.SpotEntity
import pl.ms.saper.app.data.repositories.BoardRepository
import pl.ms.saper.app.entities.Board
import pl.ms.saper.app.entities.Spot
import pl.ms.saper.business.values.Position


@Autowired
private lateinit var boardRepository: BoardRepository


fun ConfigEntryEmbeddable.toEntity() = ConfigEntryImpl(ConfigKeyImpl.valueOf(entryName), value)
fun ConfigEntryImpl.toData() = ConfigEntryEmbeddable(key.configName, value)

fun SpotEntity.toBusiness() =
    Spot(id, position.toBusiness(), spotStatus.isChecked, spotStatus.isMined, spotStatus.isFlagged, minesAround, boardEntity.id)

fun BoardEntity.toBusiness(config: Configuration) = Board(
    id, user, spots.asSequence().map { it.position.toBusiness() to it.toBusiness() }.toMap().toMutableMap(), configuration ?: ConfigEntity(0, "Default"), config
)

fun PositionEmbeddable.toBusiness() = Position(x, y)

fun Position.toData() = PositionEmbeddable(x, y)