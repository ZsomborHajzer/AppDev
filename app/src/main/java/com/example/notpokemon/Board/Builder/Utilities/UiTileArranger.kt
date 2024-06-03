package com.example.notpokemon.Board.Builder.Utilities

import android.content.Context
import android.os.Handler
import android.view.View

class UiTileArranger(context: Context) : TileArranger(context) {
    fun attachTopLeft(baseTile: View, targetTile: View){}

    override fun attachTileTopLeft(baseTile: View, targetTile: View) {
        val runnable = Runnable {
            run {
                super.attachTileTopLeft(baseTile, targetTile)
            }
        }
        runOnUiThread(runnable)
    }

    override fun attachTileTopRight(baseTile: View, targetTile: View) {
        val runnable = Runnable {
            run {
                super.attachTileTopRight(baseTile, targetTile)
            }
        }
        runOnUiThread(runnable)
    }

    override fun attachTileBottomLeft(baseTile: View, targetTile: View) {
        val runnable = Runnable {
            run {
                super.attachTileBottomLeft(baseTile, targetTile)
            }
        }
        runOnUiThread(runnable)
    }

    override fun attachTileBottomRight(baseTile: View, targetTile: View) {
        val runnable = Runnable {
            run {
                super.attachTileBottomRight(baseTile, targetTile)
            }
        }
        runOnUiThread(runnable)
    }

    protected fun runOnUiThread(r: Runnable) {
        val handler = Handler(context.mainLooper)
        handler.post(r)
    }
}