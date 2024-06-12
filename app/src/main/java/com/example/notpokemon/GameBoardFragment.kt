package com.example.notpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

/**
 * A simple [Fragment] subclass.
 * Use the [GameBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameBoardFragment : Fragment(){
    private lateinit var squares: ArrayList<SteppableTile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameBoardFragment.instance = this
        InitializationCheck.gameBoardFragment = this
        squares = ArrayList()
    }

    public fun addSquare(square: SteppableTile){
        this.squares.add(square);
    }

    public fun getStartSquare() : SteppableTile{
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
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSquares();
    }

    private fun setUpSquares(){
        fetchChildSquares()
        assignLinearOrderToSquares()
    }

    private fun fetchChildSquares(){
        var viewGroup = this.view as ViewGroup;
        for (child in viewGroup.children){

            if(child::class == FragmentContainerView::class){
                val fragmentChild = (child as FragmentContainerView).getFragment<Fragment>()

                if(fragmentChild is SteppableTile){
                    squares.add(fragmentChild as SteppableTile);
                }

            }
        }
        println(squares);
    }

    private fun assignLinearOrderToSquares(){
        var i = 0;
        while(i < squares.size){
            if(i != squares.size - 1){ // if not final element
                squares[i].nextSquare = squares[i+1]
            }
            else{ // if final element
                squares[i].nextSquare = squares[0]
            }
            i++;
        }
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