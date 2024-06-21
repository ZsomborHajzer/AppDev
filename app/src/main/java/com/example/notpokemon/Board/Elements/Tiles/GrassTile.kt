package com.example.notpokemon.Board.Elements.Tiles

import EventHandlers
import com.example.notpokemon.BattleManager
import com.example.notpokemon.Fighter
import com.example.notpokemon.PlayableCharacter
import com.example.notpokemon.R

class GrassTile : BaseTile() {
    override val baseImageResource: Int
        get() = R.drawable.ortho_grass_tile

    override fun onTileStay(character: PlayableCharacter): Boolean {
        EventHandlers.instance.sendInterruptFightAgainstAI(character, 0)
        return true
    }


}