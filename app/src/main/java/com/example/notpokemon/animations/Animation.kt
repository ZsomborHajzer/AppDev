package com.example.notpokemon.animations

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.view.get
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.R

/**
 * either use class().run() to run on current thread
 * or use Thread(class()).start() to run async
 */
abstract class Animation(protected val fight: Fight) : Runnable {
    val player1CreaturesView =
        fight.requireView().findViewById<TableLayout>(R.id.firstFighterCreatures)
    val player2CreaturesView =
        fight.requireView().findViewById<TableLayout>(R.id.secondFighterCreatures)
    val player1ActiveCreatureView =
        fight.requireView().findViewById<ImageView>(R.id.activeCreatureFighter1)
    val player2ActiveCreatureView =
        fight.requireView().findViewById<ImageView>(R.id.activeCreatureFighter2)
    val activeAttackerHealth =
        fight.requireView().findViewById<TextView>(R.id.attackingCreatureHealth)
    val activeDefenderHealth =
        fight.requireView().findViewById<TextView>(R.id.defendingCreatureHealth)
    val attackDamageText = fight.requireView().findViewById<TextView>(R.id.damageText)

    protected var finished = false
    override fun run() {
        execute()
        onFinish()
    }

    fun runAsync() {
        Thread(this).start()
    }

    protected abstract fun execute()

    fun onFinish() {
        finished = true
    }

    protected fun runOnUiThread(r: Runnable) {
        val handler = Handler(fight.requireContext().mainLooper)
        handler.post(r)
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w("thread concurrency", "runOnUiThread has been called from main thread")
        }
    }

    fun getCreatureImageView(playerNumber: Int, creatureIndex: Int): ImageView {
        return getCreatureContainer(playerNumber, creatureIndex)[0] as ImageView
    }

    fun getCreatureNameTextView(playerNumber: Int, creatureIndex: Int): TextView {
        return getCreatureContainer(playerNumber, creatureIndex)[1] as TextView
    }

    fun getCreatureContainer(playerNumber: Int, creatureIndex: Int): ViewGroup {
        val containerParent =
            getCreatureLayoutByPlayerNumber(playerNumber)[creatureIndex] as ViewGroup
        return containerParent[0] as ViewGroup
    }

    fun getCreatureLayoutByPlayerNumber(playerNumber: Int): TableLayout {
        if (playerNumber == 1) {
            return player1CreaturesView
        } else if (playerNumber == 2) {
            return player2CreaturesView
        } else {
            throw IllegalArgumentException("there cannot be more than 2 players per battle as of now. ${this.javaClass}")
        }
    }

    fun getActiveCreatureViewByPlayerNumber(playerNumber: Int): ImageView {
        if (playerNumber == 1) {
            return player1ActiveCreatureView
        } else if (playerNumber == 2) {
            return player2ActiveCreatureView
        } else {
            throw IllegalArgumentException("there cannot be more than 2 players per battle as of now. ${this.javaClass}")
        }
    }

}