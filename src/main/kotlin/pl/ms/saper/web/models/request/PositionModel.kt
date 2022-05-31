package pl.ms.saper.web.models.request

import pl.ms.saper.business.values.Position
import javax.validation.constraints.Min

class PositionModel(
    @field:Min(value = 1) var x: Int,
    @field:Min(value = 1) var y: Int
)

fun PositionModel.toBusiness() = Position(x, y)