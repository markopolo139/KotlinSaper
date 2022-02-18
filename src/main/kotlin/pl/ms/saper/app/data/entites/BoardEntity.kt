package pl.ms.saper.app.data.entites

import javax.persistence.*

@Entity
@Table(name = "boards")
class BoardEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    val id: Int,

    @OneToOne(fetch = FetchType.LAZY)
    val user: UserEntity,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val spots: Set<SpotEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BoardEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}