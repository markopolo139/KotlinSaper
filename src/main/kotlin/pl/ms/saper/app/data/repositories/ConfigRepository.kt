package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pl.ms.saper.app.data.entites.ConfigEntity
import java.util.*

interface ConfigRepository: JpaRepository<ConfigEntity, Int> {

    fun findByBoardId(boardId: Int): Optional<ConfigEntity>

}