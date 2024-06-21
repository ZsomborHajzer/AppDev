package com.example.notpokemon.Board.Elements.PathSequences

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import com.example.notpokemon.DiceRoller
import com.example.notpokemon.R
import com.example.notpokemon.Board.Elements.SteppableTile
import com.example.notpokemon.PlayableCharacter

/**
 * A simple [Fragment] subclass.
 * Use the [MultiPathPattern1.newInstance] factory method to
 * create an instance of this fragment.
 */
class MultiPathPattern1 : SteppableTile() {
    lateinit var startingSquare: SteppableTile
    var paths = ArrayList <ArrayList <SteppableTile>>()
    var viewCreated = false
    var nextSquareSet = false

    override var nextSquare: SteppableTile
        get() = super.nextSquare
        set(value) {
            nextSquare = value
            nextSquareSet = true
            attemptInitialize()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_path_pattern1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated = true
        attemptInitialize()
    }

    private fun attemptInitialize(){
        if(nextSquareSet && viewCreated){
            initialize()
        }
    }
    private fun initialize(){
        setupTiles()
        assignAllTileRoutes()
    }

    private fun setupTiles(){
        for(i in 0..2){
            paths.add(ArrayList())
        }

        startingSquare = getTile(R.id.multiPath1InitialSquare)

        paths[0].add(getTile(R.id.multiPath1PathStart1))
        paths[0].add(getTile(R.id.multiPath1Segment1Index1))

        paths[1].add(getTile(R.id.multiPath1PathStart2))
        paths[1].add(getTile(R.id.multiPath1Segment2Index1))
        paths[1].add(getTile(R.id.multiPath1Segment2Index2))

        paths[2].add(getTile(R.id.multiPath1PathStart3))
        paths[2].add(getTile(R.id.multiPath1Segment3Index1))
        paths[2].add(getTile(R.id.multiPath1Segment3Index2))
    }

    private fun assignAllTileRoutes(){
        for(i in 0..< paths.size){
            assignTilesInRoute(i)
        }
    }

    private fun assignTilesInRoute(routeIndex: Int){
        for (tileIndex in 0..< paths[routeIndex].size) {
            if(tileIndex+1 == paths[routeIndex].size){ // last tile
                paths[routeIndex][tileIndex].nextSquare = this.nextSquare
            }
            else{
                paths[routeIndex][tileIndex].nextSquare = paths[routeIndex][tileIndex+1]
            }
        }
    }

    private fun getTile(resource: Int): SteppableTile {
        return requireView().findViewById<FragmentContainerView>(resource).getFragment<SteppableTile>()
    }

    override fun onTileEntry(playableCharacter: PlayableCharacter):Boolean {
        playableCharacter.currentSquare = startingSquare
        startingSquare.onTileEntry(playableCharacter)
        val pathNumber = DiceRoller.rollArbitraryDice(3)
        startingSquare.nextSquare = paths[pathNumber-1][0]
        return false
    }

    override fun onTileStay(playableCharacter: PlayableCharacter): Boolean {
        return false
    }

    override fun onTileExit(playableCharacter: PlayableCharacter) {
        return
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment multiPathPattern1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MultiPathPattern1()
    }
}