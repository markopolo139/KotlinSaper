package pl.ms.saper.business.mock

import pl.ms.saper.business.values.Position
import pl.ms.saper.business.values.Spot

class SpotMock(
    override val position: Position,
    override var isChecked: Boolean,
    override var isMined: Boolean,
    override var isFlagged: Boolean,
    override var minesAround: Int
) : Spot