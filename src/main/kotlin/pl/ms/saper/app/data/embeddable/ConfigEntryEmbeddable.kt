package pl.ms.saper.app.data.embeddable

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class ConfigEntryEmbeddable(
    @Column(name = "entry_name") val entryName: String,
    @Column(name = "`value`") var value: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConfigEntryEmbeddable

        if (entryName != other.entryName) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entryName.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}


