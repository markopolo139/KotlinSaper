package pl.ms.saper.web.models.request

import org.hibernate.validator.constraints.Length
import pl.ms.saper.business.values.Position
import javax.validation.constraints.*

class SpotModel(
    @Min(1) val position_x: Int,
    @Min(1) val position_y: Int,
    @NotNull var isChecked: Boolean,
    @NotNull var isMined: Boolean,
    @NotNull var isFlagged: Boolean,
    @Min(0) @Max(8)var minesAround: Int
)