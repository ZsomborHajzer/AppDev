package com.example.notpokemon

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
/**
 * A simple [Fragment] subclass.
 * Use the [GameBoardSquareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameBoardSquareFragment : Fragment() {

    private lateinit var nextSquares: ArrayList<GameBoardSquareFragment>
    private lateinit var constraintSet: ConstraintSet
    private lateinit var constraintLayout: ConstraintLayout

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
        println("VIEW CREATED")
        return inflater.inflate(R.layout.fragment_game_board_square, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InitializationCheck.haveInitialized()
    }

    fun setImageFromResource(resource: Int){
        val imageView = getImageView();
        imageView.setImageResource(resource)
    }

    fun getImageView() : ImageView{
        return this.requireView().findViewById<ImageView>(R.id.gameBoardSquareImageView)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GameBoardSquareFragment.
         */
        @JvmStatic
        fun newInstance() =
            GameBoardSquareFragment()
    }
}