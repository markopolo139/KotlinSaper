package pl.ms.saper.business.mock

import pl.ms.saper.business.entities.Board
import pl.ms.saper.business.exceptions.InvalidPositionException
import pl.ms.saper.business.exceptions.InvalidSpotException
import pl.ms.saper.business.values.Position
import pl.ms.saper.business.values.Spot

class BoardMock(override var spotMap: MutableMap<Position, Spot>) : Board {
    override fun validatePosition(position: Position) {
        if (position.x !in 1..50 && position.y !in 1..50)
            throw InvalidPositionException(position)
    }

    override fun getSpot(position: Position): Spot {
        validatePosition(position)
        return spotMap[position] ?: SpotMock(position, isChecked = false, isMined = false, isFlagged = false, 0)
    }
}