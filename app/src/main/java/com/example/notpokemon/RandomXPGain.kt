package com.example.notpokemon

class RandomXPGain(override var player: PlayableCharacter) : RandomCard(player) {
    var XPAmount = 3

    override fun appliedEffect(){
        player.addXP(XPAmount)
    }
}