package com.example.notpokemon.Board.Builder.Utilities

import com.example.notpokemon.Board.Elements.SteppableTile

interface GameBoardBuilder {

    public var tiles: ArrayList<SteppableTile>
    public fun build()
}