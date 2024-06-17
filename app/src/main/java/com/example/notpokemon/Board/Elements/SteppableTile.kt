package com.example.notpokemon.Board.Elements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notpokemon.InitializationCheck
import com.example.notpokemon.PlayableCharacter

abstract class SteppableTile: Fragment() {

    var _nextSquare: SteppableTile? = null
    public open var nextSquare: SteppableTile
        get() {
            if(_nextSquare == null){
                throw IllegalStateException("nextSquare has not been initialized yet")
            }
            return _nextSquare as SteppableTile
        }
        set(value) {
            _nextSquare = value
            initializeNextTile()
        }

    protected var nextSquareInitialized = false
    protected var viewInitialized = false
    protected var initialized = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitializationCheck.addToCheckNumber()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("CREATEVIEW")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initializeView()
    }

    protected fun initializeNextTile(){
        nextSquareInitialized = true
        initialize()
    }

    protected fun initializeView(){
        viewInitialized = true
        initialize()
    }

    private fun initialize(){
        if(viewInitialized && nextSquareInitialized && !initialized){
            this.initialized = true
            InitializationCheck.haveInitialized()
        }
    }

    abstract fun onTileEntry(playableCharacter: PlayableCharacter)

    abstract fun onTileStay(playableCharacter: PlayableCharacter): Boolean // triggered event
    abstract fun onTileExit(playableCharacter: PlayableCharacter)
}