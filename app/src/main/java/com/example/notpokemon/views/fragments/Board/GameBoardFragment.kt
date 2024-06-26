package com.example.notpokemon.views.fragments.Board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.notpokemon.R
import com.example.notpokemon.views.fragments.Board.Builder.Scripts.CandyLandBuilder
import com.example.notpokemon.views.fragments.Board.Builder.Utilities.GameBoardBuilder
import com.example.notpokemon.views.fragments.Board.Elements.SteppableTile
import com.example.notpokemon.initialization.InitializationCheck

/**
 * A simple [Fragment] subclass.
 * Use the [GameBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameBoardFragment : Fragment() {
    private lateinit var squareContainers: ArrayList<FragmentContainerView> // parallel lists
    private lateinit var squares: ArrayList<SteppableTile>
    private lateinit var boardBuilder: GameBoardBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        InitializationCheck.gameBoardFragment = this
        squareContainers = ArrayList()
        squares = ArrayList()
        boardBuilder = CandyLandBuilder(this)
    }

    public fun getStartSquare(): SteppableTile {
        return this.getSquare(0)
    }

    public fun getSquare(squareNumber: Int): SteppableTile {
        return this.squares.get(squareNumber);
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("VIEW CREATED")
        return inflater.inflate(R.layout.game_board_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        boardBuilder.build()
        this.squares = boardBuilder.tiles
    }

    companion object {
        lateinit var instance: GameBoardFragment

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *

         * @return A new instance of fragment GameBoardFragment.
         */
        @JvmStatic
        fun newInstance(): GameBoardFragment = GameBoardFragment()
    }
}