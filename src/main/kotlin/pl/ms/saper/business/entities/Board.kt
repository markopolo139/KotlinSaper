package pl.ms.saper.business.entities

import pl.ms.saper.business.exceptions.InvalidPositionException
import pl.ms.saper.business.values.Position
import pl.ms.saper.business.values.Spot

interface Board {

    var spotMap: MutableMap<Position, Spot>

    val minesLeft: Int
        get() = spotMap.values.asSequence().filter { it.isMined && !it.isFlagged }.count()

    val spotChecked: Int
        get() = spotMap.values.asSequence().filter { it.isChecked }.count()

    val spotFlagged: Int
        get() = spotMap.values.asSequence().filter { it.isFlagged }.count()

    val allSpots: Int
        get() = spotMap.values.count()

    fun validatePosition(position: Position)

    fun getSpot(position: Position): Spot {
        validatePosition(position)
        return spotMap[position] ?: throw InvalidPositionException(position)
    }

    fun getSpotsAround(spot: Spot): List<Spot> {

        val resultList: MutableList<Spot> = mutableListOf()

        for (x in -1..1) {
            for (y in -1..1) {

                if (x == 0 && y == 0)
                    continue

                try {
                    resultList.add(getSpot(Position(spot.position.x - x, spot.position.y - y)))
                } catch (exception: InvalidPositionException) {
                    continue
                }

            }
        }

        return resultList
    }

    fun calculateMinesAround(spot: Spot) {
        spot.minesAround = getSpotsAround(spot).asSequence().filter { it.isMined }.count()
    }

    fun changeSpotsStatus(vararg spots: Spot) {
        spots.asSequence().forEach { spotMap[it.position] = it }
    }

}