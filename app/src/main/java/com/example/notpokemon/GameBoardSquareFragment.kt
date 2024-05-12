package com.example.notpokemon

import android.os.Bundle
import android.os.Handler
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

    private lateinit var nextSquare: GameBoardSquareFragment
    protected lateinit var baseImage: ImageView
    protected lateinit var overlayImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_board_square, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseImage = requireView().findViewById(R.id.gameBoardSquareBaseImage)
        overlayImage = requireView().findViewById(R.id.gameBoardSquareOverlayImage)
        InitializationCheck.haveInitialized()
    }

    public fun setNextSquare(nextSquare: GameBoardSquareFragment){
        this.nextSquare = nextSquare
    }
    public fun getNextSquare(): GameBoardSquareFragment{
        return this.nextSquare
    }

    fun setOverlayFromResource(resource: Int){
        // TODO:: temporary solution before merge
        Handler(this.requireContext().mainLooper).post(
            Runnable {
                run {
                    overlayImage.setImageResource(resource)
                }

            }
        )
    }

    fun clearSquareOverlay(){
        // TODO:: also temporary and sub-optimal
        Handler(this.requireContext().mainLooper).post(
            Runnable {
                run {
                    overlayImage.setImageResource(transparentOverlay)
                }

            }
        )
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GameBoardSquareFragment.
         */
        val transparentOverlay = R.drawable.transparency

        @JvmStatic
        fun newInstance() =
            GameBoardSquareFragment()
    }
}