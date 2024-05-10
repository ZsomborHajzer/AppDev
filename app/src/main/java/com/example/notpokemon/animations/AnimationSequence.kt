package com.example.notpokemon.animations

/**
 * either use class().run() to run on current thread
 * or use Thread(class()).start() to run async
 */
abstract class AnimationSequence: Runnable {
    protected var finished = false
    override fun run(){
        mainSequence()
        onFinish()
    }

    abstract fun mainSequence()

    fun onFinish(){
        finished = true
    }



}