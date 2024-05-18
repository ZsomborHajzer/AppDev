package com.example.notpokemon.animations

import com.example.notpokemon.Fight

class SwitchTeamAnimation(fight: Fight) : Animation(fight) {
    override fun execute() {
        Thread.sleep(2000)
    }


}