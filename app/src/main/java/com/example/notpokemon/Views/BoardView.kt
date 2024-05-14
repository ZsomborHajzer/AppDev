package com.example.notpokemon.Views

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.example.notpokemon.R
import kotlin.math.max
import kotlin.math.min


class BoardView : FragmentActivity() {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var scrollGestureDetector: GestureDetector
    private var scaleFactor = 1.0f
    private val minimumGameBoardScale = 0.5f
    private val maximumGameBoardScale = 4f
    private val maxHorizontalScroll = 1000f
    private val maxVerticalScroll = 500f
    private lateinit var gameBoardView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_board)
        gameBoardView = findViewById(R.id.fragmentContainerViewForGameBoard)

        // initializing touch events
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        scrollGestureDetector = GestureDetector(this, ScrollListener())
    }

    // When touched, GestureDetector records the motion event
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        scrollGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = getClippedScale(scaleFactor)
            gameBoardView.scaleX = scaleFactor
            gameBoardView.scaleY = scaleFactor

            return true
        }
    }

    private inner class ScrollListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            val xPosition = gameBoardView.scrollX + (distanceX/scaleFactor)
            val yPosition = gameBoardView.scrollY + (distanceY/scaleFactor)
            gameBoardView.scrollX = getClippedDistanceX(xPosition).toInt()
            gameBoardView.scrollY = getClippedDistanceY(yPosition).toInt()

            return super.onScroll(e1, e2, distanceX, distanceY)
        }
    }

    private fun getClippedScale(number:Float):Float{
        return getClippedValue(minimumGameBoardScale, number, maximumGameBoardScale)
    }
    private fun getClippedDistanceX(number: Float):Float{
        return getClippedValue(-maxHorizontalScroll, number, maxHorizontalScroll)
    }
    private fun getClippedDistanceY(number: Float):Float{
        return getClippedValue(-maxVerticalScroll, number, maxVerticalScroll)
    }
    fun getClippedValue(minNumber:Float, number:Float, maxNumber:Float):Float{
        return max(minNumber, min(number, maxNumber))
    }

}