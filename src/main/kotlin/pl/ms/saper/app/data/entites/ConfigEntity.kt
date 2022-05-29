package pl.ms.saper.app.data.entites

import pl.ms.saper.app.data.embeddable.ConfigEntryEmbeddable
import javax.persistence.*

@Entity
@Table(name = "board_configuration")
class ConfigEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "configuration_id")
    val id: Int,

    @Column(name = "`name`")
    var name: String,

    @Column(name = "board_id")
    var boardId: Int = 0,

    @Embedded
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "configuration_entry", joinColumns = [ JoinColumn(name = "configuration_id") ])
    val configEntries: MutableSet<ConfigEntryEmbeddable> = mutableSetOf()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfigEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

}