package pl.ms.saper.business.services

import pl.ms.saper.business.entities.Board
import pl.ms.saper.business.exceptions.SpotCheckedException
import pl.ms.saper.business.exceptions.SpotFlaggedException
import pl.ms.saper.business.exceptions.SpotRevealException
import pl.ms.saper.business.values.Position
import pl.ms.saper.business.values.Spot

class GameService {

    fun check(position: Position, board: Board): Set<Spot> {

        val selectedSpot = board.getSpot(position)

        if (selectedSpot.isChecked)
            throw SpotCheckedException()

        if (selectedSpot.isFlagged)
            throw SpotFlaggedException()

        selectedSpot.isChecked = true
        board.calculateMinesAround(selectedSpot)

        val resultSpotList = mutableSetOf(selectedSpot)

        if (!selectedSpot.isMined && selectedSpot.minesAround == 0)
            reveal(selectedSpot, board, resultSpotList)

        return resultSpotList
    }

    fun flag(position: Position, board: Board): Spot {

        val selectedSpot = board.getSpot(position)

        if (selectedSpot.isChecked)
            throw SpotCheckedException()

        selectedSpot.isFlagged = !selectedSpot.isFlagged

        return selectedSpot

    }

    fun revealOnClicked(position: Position, board: Board): MutableSet<Spot> {

        val selectedSpot = board.getSpot(position)

        val spotsAround = board.getSpotsAround(selectedSpot)

        if (selectedSpot.minesAround != spotsAround.count { it.isFlagged })
            throw SpotRevealException()

        return reveal(selectedSpot, board, mutableSetOf())

    }

    private fun reveal(spot: Spot, board: Board, editedSpotList: MutableSet<Spot>): MutableSet<Spot> {

        val spotsAround = board.getSpotsAround(spot)

        for (spotIteration in spotsAround) {

            if (spotIteration.isFlagged || spotIteration.isChecked || editedSpotList.contains(spotIteration))
                continue

            spotIteration.isChecked = true
            board.calculateMinesAround(spotIteration)
            editedSpotList.add(spotIteration)

            board.changeSpotsStatus(spotIteration)

            if (spotIteration.minesAround == 0 && !spotIteration.isMined)
                reveal(spotIteration, board, editedSpotList)

        }

        return editedSpotList

    }

}