package pl.ms.saper.app.data.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pl.ms.saper.app.data.entites.SpotEntity

interface SpotRepository: JpaRepository<SpotEntity, Int>