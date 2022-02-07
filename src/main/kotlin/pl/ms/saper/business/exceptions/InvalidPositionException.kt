package pl.ms.saper.business.exceptions

import pl.ms.saper.business.values.Position

class InvalidPositionException(invalidPosition: Position):
    Exception("Selected spot(x = ${invalidPosition.x}, y = ${invalidPosition.y}) is invalid")