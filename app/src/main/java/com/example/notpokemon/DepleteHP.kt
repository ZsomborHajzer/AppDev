package com.example.notpokemon

class DepleteHP(override var player: PlayableCharacter) : RandomCard(player) {
    var damage = 10.0

    override fun appliedEffect(){
        for(creature in player.team){
            creature.takeDamage(damage)
        }
    }
}