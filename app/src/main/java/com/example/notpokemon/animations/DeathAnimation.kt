package com.example.notpokemon.animations

import com.example.notpokemon.Fight
import com.example.notpokemon.R

class DeathAnimation(val fight:Fight): AnimationSequence() {
    override fun mainSequence() {
        fight.setBattleMapImage(R.drawable.clouds)
        Thread.sleep(4000)
        fight.setBattleMapImage(R.drawable.grass_field)
    }

}