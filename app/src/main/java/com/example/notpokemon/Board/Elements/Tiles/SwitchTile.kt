package com.example.notpokemon.Board.Elements.Tiles

import com.example.notpokemon.Board.Elements.SteppableTile
import com.example.notpokemon.R

class SwitchTile : BaseTile() {

    private var nextSquaresList = ArrayList<SteppableTile>()
    override val baseImageResource: Int
        get() = R.drawable.switcheroo_tile

    override var nextSquare: SteppableTile
        get() {
            if(nextSquaresList.size<1){
                throw IllegalStateException("nextSquaresList is empty for switchTile")
            }
            return getRandomSquare()
        }
        set(value) {
            initializeNextTile()
            nextSquaresList.add(value)
        }

    private fun getRandomSquare(): SteppableTile {
        return nextSquaresList.random()
    }
}