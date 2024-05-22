package com.example.notpokemon.animations

import android.os.Handler
import com.example.notpokemon.Fight

/**
 * either use class().run() to run on current thread
 * or use Thread(class()).start() to run async
 */
abstract class Animation(protected val fight: Fight): Runnable {
    protected var finished = false
    override fun run(){
        execute()
        onFinish()
    }

    fun runAsync(){
        Thread(this).start()
    }

    protected abstract fun execute()

    fun onFinish(){
        finished = true
    }

    protected fun runOnUiThread(r: Runnable) {
        val handler = Handler(fight.requireContext().mainLooper)
        handler.post(r)
    }

}