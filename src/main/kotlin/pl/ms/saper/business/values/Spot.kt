package pl.ms.saper.business.values

interface Spot {
    val position: Position
    var isChecked: Boolean
    var isMined: Boolean
    var isFlagged: Boolean
    var minesAround: Int

    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}