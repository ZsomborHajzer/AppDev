package com.example.notpokemon.Board.Elements.Tiles

import EventHandlers
import com.example.notpokemon.Board.Elements.SteppableTile
import com.example.notpokemon.PlayableCharacter
import com.example.notpokemon.R

class SwitchTile : BaseTile() {

    var nextSquaresList = ArrayList<SteppableTile>()
    override val baseImageResource: Int
        get() = R.drawable.switcheroo_tile

    override var nextSquare: SteppableTile
        get() {
            if(_nextSquare == null){
                throw IllegalStateException("nextSquare has not been initialized yet")
            }
            return _nextSquare as SteppableTile
        }
        set(value) {
            initializeNextTile()
            _nextSquare = value
            nextSquaresList.add(value)
        }

    fun switchNextTileByIndex(index:Int){
        _nextSquare = nextSquaresList[index]
    }

    private fun getRandomSquare(): SteppableTile {
        return nextSquaresList.random()
    }

    override fun onTileEntry(playableCharacter: PlayableCharacter): Boolean {
        super.onTileEntry(playableCharacter)
        EventHandlers.instance.sendInterruptDirectionChange(playableCharacter.id, playableCharacter.stepsLeft)
        return true
    }
}