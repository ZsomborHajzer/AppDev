package com.example.notpokemon.animations

import com.example.notpokemon.Fight

class AttackAnimation(fight: Fight) : Animation(fight) {
    override fun execute() {
        Thread.sleep(300)
    }

}