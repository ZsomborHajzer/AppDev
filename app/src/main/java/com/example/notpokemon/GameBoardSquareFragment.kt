package com.example.notpokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameBoardSquareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameBoardSquareFragment : Fragment() {

    private lateinit var nextSquares: ArrayList<GameBoardSquareFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.nextSquares = ArrayList();
    }

    public fun setNextSquares(nextSquares: ArrayList<GameBoardSquareFragment>){
        this.nextSquares = nextSquares
    }
    public fun getNextSquares(): ArrayList<GameBoardSquareFragment>{
        return this.nextSquares
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_board_square, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GameBoardSquareFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            GameBoardSquareFragment()
    }
}