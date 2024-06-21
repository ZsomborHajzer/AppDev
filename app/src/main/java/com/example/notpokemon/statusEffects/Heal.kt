package com.example.notpokemon.statusEffects

import com.example.notpokemon.playerObjects.PlayableCharacter

class Heal(
    override var character: PlayableCharacter,
    applicant: StatusEffectApplicant = StatusEffectApplicant.PLAYER
) : StatusEffect(character, applicant) {
    var healAmount = 5
    var amountOfTurns = 3

    override fun effectStatusAction() {
        for (creature in character.team) {
            creature.heal(healAmount)
        }
    }

    override fun applicationOfEffectStatus(trigger: EffectTrigger) {
        if (trigger == EffectTrigger.ONTURN) {
            var i = 0
            while (i < amountOfTurns) {
                effectStatusAction()
                i++
            }
        }
    }
}