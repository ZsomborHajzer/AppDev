package com.example.notpokemon.statusEffects

import com.example.notpokemon.playerObjects.PlayableCharacter
import com.example.notpokemon.randomTrapTiles.RandomCard

class DepleteHP(override var player: PlayableCharacter) : RandomCard(player) {
    var damage = 10.0

    override fun appliedEffect(){
        for(creature in player.team){
            creature.takeDamage(damage)
        }
    }
}