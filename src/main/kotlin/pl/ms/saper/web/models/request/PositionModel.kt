package pl.ms.saper.web.models.request

import javax.validation.constraints.Min

class PositionModel(
    @Min(1) var x: Int,
    @Min(1) var y: Int
)