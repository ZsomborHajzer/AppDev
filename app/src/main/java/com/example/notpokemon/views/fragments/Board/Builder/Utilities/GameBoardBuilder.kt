package com.example.notpokemon.views.fragments.Board.Builder.Utilities

import com.example.notpokemon.views.fragments.Board.Elements.SteppableTile

interface GameBoardBuilder {

    public var tiles: ArrayList<SteppableTile>
    public fun build()
}