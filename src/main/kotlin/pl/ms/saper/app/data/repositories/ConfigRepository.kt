package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pl.ms.saper.app.data.entites.ConfigEntity
import java.util.*

interface ConfigRepository: JpaRepository<ConfigEntity, Int> {

    @Query(
        value = "select * from configuration where board_id = (select board_id from boards where user_id = :userId",
        nativeQuery = true
    )
    fun findByUserId(@Param(value = "userId") userId: Int): Optional<ConfigEntity>
}