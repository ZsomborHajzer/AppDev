package com.example.notpokemon.views.fragments.Board.Builder.Utilities

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.notpokemon.views.fragments.Board.Elements.SteppableTile
import com.example.notpokemon.views.fragments.Board.GameBoardFragment
import com.example.notpokemon.R

class GameBoardConstructorHelper(val caller: GameBoardFragment) {

    public var squares = ArrayList<SteppableTile>()

    private val arranger = UiTileArranger(caller.requireContext())

    init {
        arranger.initializeTileNudgeVariables()
    }

    public fun asyncCreateTileTopLeft(attachedTileClass: Class<out SteppableTile>): SteppableTile {
        var baseTile = squares.last()
        return asyncCreateTileTopLeft(baseTile, attachedTileClass)
    }

    public fun asyncCreateTileTopLeft(
        baseTile: SteppableTile,
        attachedTileClass: Class<out SteppableTile>
    ): SteppableTile {
        val attachedTile = createTile(attachedTileClass)
        val thread = Thread {
            run {
                waitUntilBothViewsAreCreated(baseTile, attachedTile)
                val baseContainer = baseTile.view as View
                val attachedContainer = attachedTile.view as View
                arranger.attachTileTopLeft(baseContainer, attachedContainer)
                baseTile.nextSquare = attachedTile
                attachedTile.cardinalDirection = "NW"
                clearItemCreationQueue()
            }
        }
        viewCreationQueue.add(thread)
        thread.start()
        return attachedTile
    }

    public fun asyncCreateTileTopRight(attachedTileClass: Class<out SteppableTile>): SteppableTile {
        var baseTile = squares.last()
        return asyncCreateTileTopRight(baseTile, attachedTileClass)
    }

    public fun asyncCreateTileTopRight(
        baseTile: SteppableTile,
        attachedTileClass: Class<out SteppableTile>
    ): SteppableTile {
        val attachedTile = createTile(attachedTileClass)
        val thread = Thread {
            run {
                waitUntilBothViewsAreCreated(baseTile, attachedTile)
                val baseContainer = baseTile.view as View
                val attachedContainer = attachedTile.view as View
                arranger.attachTileTopRight(baseContainer, attachedContainer)
                baseTile.nextSquare = attachedTile
                attachedTile.cardinalDirection = "NE"
                clearItemCreationQueue()
            }
        }
        viewCreationQueue.add(thread)
        thread.start()
        return attachedTile
    }

    public fun asyncCreateTileBottomLeft(attachedTileClass: Class<out SteppableTile>): SteppableTile {
        var baseTile = squares.last()
        return asyncCreateTileBottomLeft(baseTile, attachedTileClass)
    }

    public fun asyncCreateTileBottomLeft(
        baseTile: SteppableTile,
        attachedTileClass: Class<out SteppableTile>
    ): SteppableTile {
        val attachedTile = createTile(attachedTileClass)
        val thread = Thread {
            run {
                waitUntilBothViewsAreCreated(baseTile, attachedTile)
                val baseContainer = baseTile.view as View
                val attachedContainer = attachedTile.view as View
                arranger.attachTileBottomLeft(baseContainer, attachedContainer)
                baseTile.nextSquare = attachedTile
                attachedTile.cardinalDirection = "SW"
                clearItemCreationQueue()
            }
        }
        viewCreationQueue.add(thread)
        thread.start()
        return attachedTile
    }

    public fun asyncCreateTileBottomRight(attachedTileClass: Class<out SteppableTile>): SteppableTile {
        var baseTile = squares.last()
        return asyncCreateTileBottomRight(baseTile, attachedTileClass)
    }

    public fun asyncCreateTileBottomRight(
        baseTile: SteppableTile,
        attachedTileClass: Class<out SteppableTile>
    ): SteppableTile {
        val attachedTile = createTile(attachedTileClass)

        var thread = Thread {
            run {
                waitUntilBothViewsAreCreated(baseTile, attachedTile)
                val baseContainer = baseTile.view as View
                val attachedContainer = attachedTile.view as View
                arranger.attachTileBottomRight(baseContainer, attachedContainer)
                baseTile.nextSquare = attachedTile
                attachedTile.cardinalDirection = "SE"
                clearItemCreationQueue()
            }
        }

        viewCreationQueue.add(thread)
        thread.start()
        return attachedTile
    }

    public fun createStarterTile(tileClass: Class<out SteppableTile>): SteppableTile {
        val tile = createTile(tileClass)
        var thread = Thread {
            run {
                waitUntilViewIsCreated(tile)
                val tileContainer = tile.view as View
                arranger.standardizeViewSize(tileContainer)
            }
        }
        thread.start()

        return tile
    }

    public fun createTile(tileClass: Class<out SteppableTile>): SteppableTile {
        val fragmentManager = caller.childFragmentManager
        val tile = tileClass.getDeclaredConstructor().newInstance()
        fragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.gameboardLayout, tile, "Tile${advanceTileNumber()}")
        }
        squares.add(tile)
        return tile
    }

    private fun waitUntilBothViewsAreCreated(frag1: Fragment, frag2: Fragment) {
        while (frag1.view == null || frag2.view == null || !isCurrentThreadHeadOfCreationQueue()) {
            Thread.sleep(200)
        }
    }

    private fun waitUntilViewIsCreated(frag: Fragment) {
        while (frag.view == null) {
            Thread.sleep(200)
        }
    }

    private fun isCurrentThreadHeadOfCreationQueue(): Boolean {
        if (viewCreationQueue.first() == Thread.currentThread()) {
            return true
        } else {
            return false
        }
    }

    companion object {
        var tileNumber = 0
        fun advanceTileNumber(): Int {
            tileNumber++
            return tileNumber
        }

        var viewCreationQueue = ArrayList<Thread>()
        fun clearItemCreationQueue() {
            viewCreationQueue.removeFirst()
        }
    }
}