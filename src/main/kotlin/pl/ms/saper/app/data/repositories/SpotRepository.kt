package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import pl.ms.saper.app.data.entites.SpotEntity

interface SpotRepository: JpaRepository<SpotEntity, Int> {

    @Query("Select * from spots where x = :x and y = :y and board_id = :boardId", nativeQuery = true)
    fun findByPosition(@Param("boardId") boardId: Int, @Param("x") x: Int, @Param("y") y: Int)

    @Modifying
    @Transactional
    @Query("Delete from spots where board_id = :boardId", nativeQuery = true)
    fun deleteAllSpotsByBoardId(@Param("boardId") boardId: Int)

}