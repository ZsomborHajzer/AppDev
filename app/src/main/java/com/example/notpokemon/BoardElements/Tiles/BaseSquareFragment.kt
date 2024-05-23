package com.example.notpokemon.BoardElements.Tiles

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.notpokemon.BoardElements.SteppableTile
import com.example.notpokemon.PlayableCharacter
import com.example.notpokemon.R

/**
 * A simple [Fragment] subclass.
 * Use the [BaseSquareFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseSquareFragment : SteppableTile() {
    protected lateinit var baseImage: ImageView
    protected lateinit var overlayImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.game_board_square_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseImage = requireView().findViewById(R.id.gameBoardSquareBaseImage)
        overlayImage = requireView().findViewById(R.id.gameBoardSquareOverlayImage)
    }

    override fun onTileEntry(playableCharacter: PlayableCharacter) {
        this.setOverlayFromResource(playableCharacter.icon)
    }

    override fun onTileStay(playableCharacter: PlayableCharacter) {
        return
    }

    override fun onTileExit() {
        clearSquareOverlay()
    }

    private fun clearSquareOverlay(){
        setOverlayFromResource(transparentOverlay)
    }

    private fun setOverlayFromResource(resource: Int){
        Handler(this.requireContext().mainLooper).post(
            Runnable {
                run {
                    overlayImage.setImageResource(resource)
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
            BaseSquareFragment()
    }
}