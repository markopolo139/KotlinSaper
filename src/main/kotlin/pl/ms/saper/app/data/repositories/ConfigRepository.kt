package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pl.ms.saper.app.data.entites.ConfigEntity
import java.util.*

interface ConfigRepository: JpaRepository<ConfigEntity, Int> {

    @Query(
        value = "select * from board_configuration where board_id = (select board_id from boards where user_id = :userId)",
        nativeQuery = true
    )
    fun findByUserId(@Param(value = "userId") userId: Int): Optional<ConfigEntity>

    @Modifying
    @Query(
        value = "delete from configuration_entry where configuration_id = :configId and entry_name = :configKey",
        nativeQuery = true
    )
    fun deleteByConfigKeyAndConfigId(@Param(value = "configId") configId: Int, @Param(value = "configKey") configKey: String)
}