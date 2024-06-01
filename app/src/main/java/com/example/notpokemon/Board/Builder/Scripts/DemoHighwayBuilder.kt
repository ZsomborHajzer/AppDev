package com.example.notpokemon.Board.Builder.Scripts
import com.example.notpokemon.Board.Builder.Utilities.GameBoardBuilder
import com.example.notpokemon.Board.Builder.Utilities.GameBoardConstructorHelper
import com.example.notpokemon.Board.Elements.SteppableTile
import com.example.notpokemon.Board.Elements.Tiles.BaseTile
import com.example.notpokemon.Board.Elements.Tiles.GrassTile
import com.example.notpokemon.GameBoardFragment

class DemoHighwayBuilder(caller: GameBoardFragment): GameBoardBuilder {
    override var tiles = ArrayList<SteppableTile>()
    private val builderHelper = GameBoardConstructorHelper(caller)
    private var downRightMovement = 0
    override fun build() {
        val startSquare = builderHelper.createStarterTile(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
        for (i in 1..6){
            for (i in 1..2){
                builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
                downRightMovement++
            }
            for(i in 1..3){
                builderHelper.asyncCreateTileTopRight(BaseTile::class.java)
            }
            for(i in 1..2){
                builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
                downRightMovement++
            }
            for(i in 1..3){
                builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java)
            }
        }
        for(i in 1..5){
            builderHelper.asyncCreateTileTopRight(BaseTile::class.java)
        }
        for(i in 0..downRightMovement){
            builderHelper.asyncCreateTileTopLeft(BaseTile::class.java)
        }
        for(i in 1..3){
            builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java)
        }

        builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java).nextSquare = startSquare
        this.tiles = builderHelper.squares
    }
}