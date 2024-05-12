package com.example.notpokemon.animations

import com.example.notpokemon.Fight
import com.example.notpokemon.R

class DeathAnimation(fight: Fight): Animation(fight) {
    override fun execute() {
        setBattleFieldImage(R.drawable.clouds)
        Thread.sleep(4000)
        setBattleFieldImage(R.drawable.grass_field)
    }

    private fun setBattleFieldImage(image: Int){
        runOnUiThread(Runnable {
            run {
                fight.battleMapView.setImageResource(image)
            }
        })
    }

}