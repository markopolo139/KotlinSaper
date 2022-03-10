package pl.ms.saper.app.converters

import pl.ms.saper.app.data.entites.SpotEntity
import pl.ms.saper.web.models.request.SpotModel

fun SpotEntity.toModel() = SpotModel(position.x, position.y, spotStatus.isChecked,  spotStatus.isMined, spotStatus.isFlagged, minesAround)

fun MutableSet<SpotEntity>.toModelList() = map { it.toModel() }
