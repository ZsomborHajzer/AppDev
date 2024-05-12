package com.example.notpokemon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class SteppableTile: Fragment() {
    public lateinit var nextSquare: SteppableTile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitializationCheck.addToCheckNumber()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitializationCheck.haveInitialized()
    }

    abstract fun getEntrySquare():SteppableTile

    abstract fun onTileEntry(character: Character)
    abstract fun onTileExit()
}