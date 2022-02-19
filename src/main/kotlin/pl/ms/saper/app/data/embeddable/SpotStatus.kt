package pl.ms.saper.app.data.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class SpotStatus(
    @Column(name = "isMined")
    val isMined: Boolean,

    @Column(name = "isChecked")
    val isChecked: Boolean,

    @Column(name = "isFlagged")
    val isFlagged: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpotStatus

        if (isMined != other.isMined) return false
        if (isChecked != other.isChecked) return false
        if (isFlagged != other.isFlagged) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isMined.hashCode()
        result = 31 * result + isChecked.hashCode()
        result = 31 * result + isFlagged.hashCode()
        return result
    }
}