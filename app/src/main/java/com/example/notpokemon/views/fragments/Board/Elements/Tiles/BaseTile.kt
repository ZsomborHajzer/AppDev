package com.example.notpokemon.views.fragments.Board.Elements.Tiles

import com.example.notpokemon.websocketHandlers.EventHandlers
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.notpokemon.views.fragments.Board.Elements.SteppableTile
import com.example.notpokemon.views.fragments.Board.GameBoardFragment
import com.example.notpokemon.playerObjects.PlayableCharacter
import com.example.notpokemon.R

/**
 * A simple [Fragment] subclass.
 * Use the [BaseTile.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseTile : SteppableTile() {
    protected lateinit var baseImage: ImageView
    protected lateinit var overlayImage: ImageView
    protected var onTile = ArrayList<PlayableCharacter>()

    protected open val baseImageResource = R.drawable.cube_orthographic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.game_board_square_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseImage = requireView().findViewById(R.id.gameBoardSquareBaseImage)
        overlayImage = requireView().findViewById(R.id.gameBoardSquareOverlayImage)
        baseImage.setImageResource(baseImageResource)
    }

    override fun onTileEntry(playableCharacter: PlayableCharacter): Boolean {
        this.setOverlayFromResource(playableCharacter.icon)
        onTile.add(playableCharacter)
        return false
    }

    override fun onTileStay(playableCharacter: PlayableCharacter): Boolean {
        if (onTile.size > 1) {
            EventHandlers.instance.sendInterruptFightAgainstPlayer(onTile[0], onTile[1])
            return true
        }
        return false
    }

    override fun onTileExit(playableCharacter: PlayableCharacter) {
        onTile.remove(playableCharacter)
        clearSquareOverlay()
        if (onTile.isNotEmpty()) {
            setOverlayFromResource(onTile[0].icon)
        }
    }

    private fun clearSquareOverlay() {
        setOverlayFromResource(transparentOverlay)
    }

    private fun setOverlayFromResource(resource: Int) {
        Handler(GameBoardFragment.instance.requireContext().mainLooper).post(
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
            BaseTile()
    }
}