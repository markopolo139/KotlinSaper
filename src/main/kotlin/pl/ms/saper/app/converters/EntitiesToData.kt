package pl.ms.saper.app.converters

import pl.ms.saper.app.configuration.ConfigEntryImpl
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.data.embeddable.ConfigEntryEmbeddable
import pl.ms.saper.app.data.embeddable.PositionEmbeddable
import pl.ms.saper.app.data.embeddable.SpotStatus
import pl.ms.saper.app.data.entites.BoardEntity
import pl.ms.saper.app.data.entites.SpotEntity
import pl.ms.saper.app.entities.Board
import pl.ms.saper.app.entities.Spot
import pl.ms.saper.business.values.Position

fun ConfigEntryEmbeddable.toEntity() = ConfigEntryImpl(ConfigKeyImpl.valueOf(entryName), value)
fun ConfigEntryImpl.toData() = ConfigEntryEmbeddable(key.configName, value)

fun SpotEntity.toBusiness() =
    Spot(id, Position(position.x, position.y), spotStatus.isChecked, spotStatus.isMined, spotStatus.isFlagged, minesAround)
fun Spot.toData() =
    SpotEntity(spotId, PositionEmbeddable(position.x, position.y), SpotStatus(isMined, isChecked, isFlagged), minesAround)

fun BoardEntity.toBusiness() = Board(
    id, user, spots.asSequence().map { Position(it.position.x, it.position.y) to it.toBusiness() }.toMap().toMutableMap(), configuration
)
fun Board.toData() =
    BoardEntity(boardId, userEntity, spotMap.values.asSequence().map { (it as Spot).toData() }.toMutableSet(), configEntity)