package com.example.notpokemon.randomTrapTiles

import com.example.notpokemon.playerObjects.PlayableCharacter

class AddTypeDisadvantage(override var player: PlayableCharacter) : RandomCard(player) {

    override fun appliedEffect() {
        for (creature in player.team) {
            creature.attack.baseDamage *= 0.5
        }
    }
}