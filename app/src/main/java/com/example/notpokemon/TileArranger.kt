package com.example.notpokemon

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.fragment.app.FragmentContainerView


class TileArranger(val context: Context) {

    private var horizontalNudge = 0

    private var verticalNudge = 0

    fun initializeTileNudgeVariables(){
        if(horizontalNudge==0){
            horizontalNudge = (context.dpToPx(defaultTileWidth_dp)/2)
        }

        if(verticalNudge==0){
            verticalNudge = (context.dpToPx(defaultTileHeight_dp)/2)
        }
    }

    fun attachTileTopLeft(baseTile:FragmentContainerView, targetTile:FragmentContainerView){
        standardizeViewSize(targetTile)
        copyPosition(baseTile, targetTile)
        nudgeByAmount(targetTile, -horizontalNudge, -verticalNudge)
        setLowerThan(baseTile, targetTile)
    }
    fun attachTileTopRight(baseTile:FragmentContainerView, targetTile:FragmentContainerView){
        standardizeViewSize(targetTile)
        copyPosition(baseTile, targetTile)
        nudgeByAmount(targetTile, horizontalNudge, -verticalNudge)
        setLowerThan(baseTile,targetTile)
    }
    fun attachTileBottomLeft(baseTile:FragmentContainerView, targetTile:FragmentContainerView){
        standardizeViewSize(targetTile)
        copyPosition(baseTile, targetTile)
        nudgeByAmount(targetTile, -horizontalNudge, verticalNudge)
        setHigherThan(baseTile,targetTile)
    }
    fun attachTileBottomRight(baseTile:FragmentContainerView, targetTile:FragmentContainerView){
        standardizeViewSize(targetTile)
        copyPosition(baseTile, targetTile)
        nudgeByAmount(targetTile, horizontalNudge, verticalNudge)
        setHigherThan(baseTile, targetTile)
    }

    fun standardizeViewSize(view: View){
        view.layoutParams.width = context.dpToPx(defaultTileWidth_dp)
        view.layoutParams.height = context.dpToPx(defaultTileWidth_dp)
    }

    fun copyPosition(baseView:View, targetView:View){
        val x = getXMarginFromView(baseView)
        val y = getYMarginFromView(baseView)
        positionView(targetView, x, y)
    }

    fun nudgeByAmount(view:View, xMovement:Int, yMovement:Int){
        val x = getXMarginFromView(view)+xMovement
        val y = getYMarginFromView(view)+yMovement
        positionView(view, x, y)
    }

    fun getXMarginFromView(view: View): Int {
        return view.marginStart
    }
    fun getYMarginFromView(view: View): Int {
        return view.marginTop
    }

    fun positionView(view:View, x:Int, y:Int){
        var params = view.layoutParams as ViewGroup.MarginLayoutParams
        params.marginStart = x
        params.topMargin = y
        view.requestLayout()
    }

    fun setLowerThan(baseView:View, targetView:View){
        targetView.z = baseView.z-1
    }

    fun setHigherThan(baseView:View, targetView:View){
        targetView.z = baseView.z+1
    }


    companion object{
        val defaultTileWidth_dp = 60
        val defaultTileHeight_dp = 33

        fun Context.dpToPx(dp: Int): Int {
            return (dp * resources.displayMetrics.density).toInt()
        }
    }
}