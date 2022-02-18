package pl.ms.saper.app.data.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class PositionEmbeddable(
    @Column(name = "x")
    val x: Int,

    @Column(name = "y")
    val y: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PositionEmbeddable

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}