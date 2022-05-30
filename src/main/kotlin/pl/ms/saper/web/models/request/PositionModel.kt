package pl.ms.saper.web.models.request

import javax.validation.constraints.Min

class PositionModel(
    @field:Min(value = 1) var x: Int,
    @field:Min(value = 1) var y: Int
)