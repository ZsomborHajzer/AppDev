package com.example.notpokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class SteppableTile: Fragment() {
    protected var _nextSquare: SteppableTile? = null
    public var nextSquare: SteppableTile
        get(){
            return _nextSquare ?: throw UninitializedPropertyAccessException("\"nextSquare\" was queried before being initialized")
        }
        set(value) {
            println(this.javaClass.simpleName)
            _nextSquare = value
            onNextSquareSet()
        }

    protected open fun onNextSquareSet(){
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitializationCheck.addToCheckNumber()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitializationCheck.haveInitialized()
    }

    abstract fun onTileEntry(playableCharacter: PlayableCharacter)

    abstract fun onTileStay(playableCharacter: PlayableCharacter)
    abstract fun onTileExit()
}