package pl.ms.saper.app.data.entites

import pl.ms.saper.app.data.embeddable.PositionEmbeddable
import pl.ms.saper.app.data.embeddable.SpotStatus
import javax.persistence.*

@Entity
@Table(name = "spots")
class SpotEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "spot_id")
    val id: Int,

    @Embedded
    val position: PositionEmbeddable,

    @Embedded
    val spotStatus: SpotStatus,

    @Column(name = "mines_around")
    val minesAround: Int,

    @ManyToOne
    @JoinColumn(name = "board_id", referencedColumnName = "board_id")
    var boardEntity: BoardEntity
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpotEntity

        if (id != other.id) return false
        if (position != other.position) return false
        if (spotStatus != other.spotStatus) return false
        if (minesAround != other.minesAround) return false
        if (boardEntity != other.boardEntity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + position.hashCode()
        result = 31 * result + spotStatus.hashCode()
        result = 31 * result + minesAround
        result = 31 * result + boardEntity.hashCode()
        return result
    }
}