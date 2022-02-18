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
    val minesAround: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpotEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}