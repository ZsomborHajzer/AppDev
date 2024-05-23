package com.example.notpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.notpokemon.BoardElements.SteppableTile

/**
 * A simple [Fragment] subclass.
 * Use the [GameBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameBoardFragment : Fragment(){
    private lateinit var squareContainers: ArrayList<FragmentContainerView> // parallel lists
    private lateinit var squares: ArrayList<SteppableTile>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameBoardFragment.instance = this
        InitializationCheck.gameBoardFragment = this
        squareContainers = ArrayList()
        squares = ArrayList()
    }

    public fun addSquare(square: SteppableTile){
        this.squares.add(square);
    }

    public fun getStartSquare() : SteppableTile {
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
        setUpSquares();
    }

    private fun setUpSquares(){
        fetchChildSquares()
        assignLinearOrderToSquares()
        arrangeTiles()
    }

    private fun fetchChildSquares(){
        var viewGroup = this.view as ViewGroup;
        for (child in viewGroup.children){

            if(child::class == FragmentContainerView::class){

                val derivedFragment = (child as FragmentContainerView).getFragment<Fragment>()

                if(derivedFragment is SteppableTile){
                    squareContainers.add(child)
                    squares.add(derivedFragment as SteppableTile);
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

    //temporary
    private fun arrangeTiles(){
        val arranger = TileArranger(requireContext())
        arranger.initializeTileNudgeVariables()
        var i = 0;
        while(i < squareContainers.size*0.25){
                arranger.attachTileBottomRight(squareContainers[i], squareContainers[i+1])
            i++;
        }

        while(i < squareContainers.size*0.5){
            arranger.attachTileTopRight(squareContainers[i], squareContainers[i+1])

            i++;
        }

        while(i < squareContainers.size*0.75){
            arranger.attachTileTopLeft(squareContainers[i], squareContainers[i+1])
            i++;
        }

        while(i < squareContainers.size){
            if(i != squareContainers.size - 1){ // if not final element
                arranger.attachTileBottomLeft(squareContainers[i], squareContainers[i+1])
            }
            else{ // if final element
                arranger.attachTileBottomLeft(squareContainers[i], squareContainers[0])
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