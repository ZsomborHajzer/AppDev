package com.example.notpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameBoardFragment : Fragment(){
    private lateinit var squares: ArrayList<GameBoardSquareFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        squares = ArrayList()
    }

    public fun addSquare(square: GameBoardSquareFragment){
        this.squares.add(square);
    }

    public fun getSquare(squareNumber: Int): GameBoardSquareFragment {
        return this.squares.get(squareNumber);
    }

    public fun getNextSquares(squareNumber: Int): ArrayList<GameBoardSquareFragment>{
        return this.getSquare(squareNumber).getNextSquares();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction();

        val boardBuilder = BoardBuilder(fragmentTransaction, view.parent as View);
        boardBuilder.generateSquare()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *

         * @return A new instance of fragment GameBoardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameBoardFragment()
    }
}