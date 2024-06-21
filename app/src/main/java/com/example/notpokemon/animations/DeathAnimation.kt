package com.example.notpokemon.animations

import android.widget.ImageView
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.R

class DeathAnimation(fight: Fight) : Animation(fight) {

    val battleMapView = fight.requireView().findViewById<ImageView>(R.id.battlemapImage)
    override fun execute() {
        setBattleFieldImage(R.drawable.clouds)
        Thread.sleep(4000)
        setBattleFieldImage(R.drawable.grass_field)
    }

    private fun setBattleFieldImage(image: Int) {
        runOnUiThread(Runnable {
            run {
                battleMapView.setImageResource(image)
            }
        })
    }

}