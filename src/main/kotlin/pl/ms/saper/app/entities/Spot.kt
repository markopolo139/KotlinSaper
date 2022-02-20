package pl.ms.saper.app.entities

import pl.ms.saper.business.values.Position
import pl.ms.saper.business.values.Spot

class Spot(
    val spotId: Int,
    override val position: Position,
    override var isChecked: Boolean,
    override var isMined: Boolean,
    override var isFlagged: Boolean,
    override var minesAround: Int
) : Spot {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as pl.ms.saper.app.entities.Spot

        if (position != other.position) return false
        if (isChecked != other.isChecked) return false
        if (isMined != other.isMined) return false
        if (isFlagged != other.isFlagged) return false
        if (minesAround != other.minesAround) return false

        return true
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + isChecked.hashCode()
        result = 31 * result + isMined.hashCode()
        result = 31 * result + isFlagged.hashCode()
        result = 31 * result + minesAround
        return result
    }
}