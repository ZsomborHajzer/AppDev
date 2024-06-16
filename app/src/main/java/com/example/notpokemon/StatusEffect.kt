package com.example.notpokemon

abstract class StatusEffect(open var character: PlayableCharacter, open var applicant: StatusEffectApplicant) {

    open fun effectStatusAction(){}
    open fun applicationOfEffectStatus(trigger: EffectTrigger){}
}