package com.example.notpokemon.views.fragments.Board.Elements.Tiles

import com.example.notpokemon.websocketHandlers.EventHandlers
import com.example.notpokemon.playerObjects.PlayableCharacter
import com.example.notpokemon.R

class GrassTile : BaseTile() {
    override val baseImageResource: Int
        get() = R.drawable.ortho_grass_tile

    override fun onTileStay(character: PlayableCharacter): Boolean {
        EventHandlers.instance.sendInterruptFightAgainstAI(character, 0)
        return true
    }


}