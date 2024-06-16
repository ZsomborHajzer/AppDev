package com.example.notpokemon

class AddTypeDisadvantage(override var player: PlayableCharacter) : RandomCard(player) {

    override fun appliedEffect(){
        for(creature in player.team){
            creature.attack.damage *= 0.5
        }
    }
}