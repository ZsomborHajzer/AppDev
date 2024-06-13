package com.example.notpokemon.Board.Elements.Tiles

import com.example.notpokemon.BattleManager
import com.example.notpokemon.Fighter
import com.example.notpokemon.PlayableCharacter
import com.example.notpokemon.R

class GrassTile : BaseTile() {
    override val baseImageResource: Int
        get() = R.drawable.ortho_grass_tile

    override fun onTileStay(character: PlayableCharacter) {
        val battleManager = BattleManager()
        battleManager.fighter1 = character
        val randomEncounterFighter = Fighter("the whispers in the woods")
        randomEncounterFighter.addCreature(BattleManager.generateCreature())
        battleManager.fighter2 = randomEncounterFighter
        battleManager.run()
        
    }
}