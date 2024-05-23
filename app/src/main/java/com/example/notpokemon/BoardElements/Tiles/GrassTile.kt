package com.example.notpokemon.BoardElements.Tiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notpokemon.BattleManager
import com.example.notpokemon.Fighter
import com.example.notpokemon.PlayableCharacter
import com.example.notpokemon.R

class GrassTile : BaseSquareFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_board_grass_tile_fragment, container, false)
    }
    override fun onTileStay(character: PlayableCharacter) {
        val battleManager = BattleManager()
        battleManager.fighter1 = character
        val randomEncounterFighter = Fighter("the whispers in the woods")
        randomEncounterFighter.addCreature(BattleManager.generateCreature())
        battleManager.fighter2 = randomEncounterFighter
        battleManager.run()
        
    }
}