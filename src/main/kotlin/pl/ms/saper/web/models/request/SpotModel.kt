package pl.ms.saper.web.models.request

import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class SpotModel(
    @Valid var positionModel: PositionModel,
    @NotNull var isChecked: Boolean,
    @NotNull var isMined: Boolean,
    @NotNull var isFlagged: Boolean,
    @Min(0) @Max(8)var minesAround: Int
)