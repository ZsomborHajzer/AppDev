package com.example.notpokemon

class GiveStatusEffect(override var player: PlayableCharacter, statusEffect: StatusEffect) : RandomCard(player) {
    var statEffect = statusEffect
    override fun appliedEffect(){
        if(statEffect.applicant == StatusEffectApplicant.PLAYER){
            player.addStatusEffect(statEffect)
        }
        else if(statEffect.applicant == StatusEffectApplicant.CREATURE){
            for(creature in player.team){
                creature.addStatusEffect(statEffect)
            }
        }
    }
}