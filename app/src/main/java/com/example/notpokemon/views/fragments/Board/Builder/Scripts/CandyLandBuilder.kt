package com.example.notpokemon.views.fragments.Board.Builder.Scripts

import com.example.notpokemon.views.fragments.Board.Elements.SteppableTile
import com.example.notpokemon.views.fragments.Board.Elements.Tiles.BaseTile
import com.example.notpokemon.views.fragments.Board.Elements.Tiles.GrassTile
import com.example.notpokemon.views.fragments.Board.Elements.Tiles.SwitchTile
import com.example.notpokemon.views.fragments.Board.Builder.Utilities.GameBoardBuilder
import com.example.notpokemon.views.fragments.Board.Builder.Utilities.GameBoardConstructorHelper
import com.example.notpokemon.views.fragments.Board.GameBoardFragment

class CandyLandBuilder(caller: GameBoardFragment) : GameBoardBuilder {

    override var tiles = ArrayList<SteppableTile>()
    private val builderHelper = GameBoardConstructorHelper(caller)
    override fun build() {
        // down to the right
        val startSquare = builderHelper.createStarterTile(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomRight(GrassTile::class.java)

        builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
        val middleBottomTile = builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
        for (i in 1..2) {
            builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
        }
        builderHelper.asyncCreateTileBottomRight(GrassTile::class.java)
        for (i in 1..2) {
            builderHelper.asyncCreateTileBottomRight(BaseTile::class.java)
        }


        // up to the right
        builderHelper.asyncCreateTileTopRight(BaseTile::class.java)
        builderHelper.asyncCreateTileTopRight(BaseTile::class.java)
        builderHelper.asyncCreateTileTopRight(BaseTile::class.java)
        builderHelper.asyncCreateTileTopRight(GrassTile::class.java)

        // up to the left
        for (i in 1..4) {
            builderHelper.asyncCreateTileTopLeft(BaseTile::class.java)
        }

        val switchTile = builderHelper.asyncCreateTileTopLeft(SwitchTile::class.java)

        // down left through the middle
        builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomLeft(GrassTile::class.java).nextSquare = middleBottomTile

        // back from the middle top, to the top left
        builderHelper.asyncCreateTileTopLeft(switchTile, BaseTile::class.java)
        builderHelper.asyncCreateTileTopLeft(BaseTile::class.java)
        builderHelper.asyncCreateTileTopLeft(BaseTile::class.java)
        builderHelper.asyncCreateTileTopLeft(GrassTile::class.java)

        // left to the bottom
        builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java)
        builderHelper.asyncCreateTileBottomLeft(BaseTile::class.java).nextSquare = startSquare

        this.tiles = builderHelper.squares
    }

}