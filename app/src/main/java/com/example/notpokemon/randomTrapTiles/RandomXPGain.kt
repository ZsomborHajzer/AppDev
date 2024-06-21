package com.example.notpokemon.randomTrapTiles

import com.example.notpokemon.playerObjects.PlayableCharacter

class RandomXPGain(override var player: PlayableCharacter) : RandomCard(player) {
    var XPAmount = 3

    override fun appliedEffect(){
        player.addXP(XPAmount)
    }
}