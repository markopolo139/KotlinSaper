package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.ms.saper.app.data.entites.UserEntity
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserEntity, Int> {

    fun findByUsername(username: String): Optional<UserEntity>

}