package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pl.ms.saper.app.data.entites.BoardEntity
import java.util.*

@Repository
interface BoardRepository: JpaRepository<BoardEntity, Int> {

    fun findByUser_UserId(userId: Int): Optional<BoardEntity>

}