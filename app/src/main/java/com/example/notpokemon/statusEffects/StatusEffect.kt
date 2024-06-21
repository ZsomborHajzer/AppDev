package com.example.notpokemon.statusEffects

import com.example.notpokemon.playerObjects.PlayableCharacter

abstract class StatusEffect(
    open var character: PlayableCharacter,
    open var applicant: StatusEffectApplicant
) {

    open fun effectStatusAction() {}
    open fun applicationOfEffectStatus(trigger: EffectTrigger) {}
}