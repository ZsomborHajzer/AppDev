package com.example.notpokemon.views.activities

import android.os.Bundle
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.example.notpokemon.views.fragments.decisionPanel.DecisionPanel
import com.example.notpokemon.views.fragments.decisionPanel.DecisionTrackingClass
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.GameDirector
import com.example.notpokemon.R
import kotlin.math.max
import kotlin.math.min


class BoardView : FragmentActivity(), DecisionTrackingClass {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private lateinit var scrollGestureDetector: GestureDetector
    private var scaleFactor = 1.0f
    private val minimumGameBoardScale = 0.5f
    private val maximumGameBoardScale = 4f
    private var maxHorizontalScroll = 0f // assigned later based on container
    private var maxVerticalScroll = 0f
    private lateinit var gameBoardView: FragmentContainerView
    private lateinit var bannerParent: FrameLayout
    private lateinit var bannerText: TextView
    private lateinit var bannerImage: ImageView
    private lateinit var decisionPanel: DecisionPanel
    private lateinit var stepsToGoTextView: TextView
    lateinit var currentDecisionPanelExecution: (Int) -> Unit
    private var isAwaitingTap = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        setContentView(R.layout.game_board_activity)
        gameBoardView = findViewById(R.id.fragmentContainerViewForGameBoard)
        bannerParent = findViewById(R.id.bannerParent)
        bannerText = findViewById(R.id.bannerText)
        bannerImage = findViewById(R.id.bannerBackgroundImage)
        decisionPanel =
            findViewById<FragmentContainerView>(R.id.decisionPanelFragmentContainer).getFragment()
        stepsToGoTextView = findViewById(R.id.stepTrackingText)

        val gameBoardFrame = findViewById<FrameLayout>(R.id.gameBoardFrame)
        gameBoardFrame.doOnLayout {
            maxHorizontalScroll = (gameBoardFrame.width * 0.8).toFloat()
            maxVerticalScroll = (gameBoardFrame.height * 0.8).toFloat()
        }

        // initializing touch events
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        scrollGestureDetector = GestureDetector(this, ScrollAndTapListener())
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

    private inner class ScrollAndTapListener : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            val xPosition = gameBoardView.scrollX + (distanceX / scaleFactor)
            val yPosition = gameBoardView.scrollY + (distanceY / scaleFactor)
            gameBoardView.scrollX = getClippedDistanceX(xPosition).toInt()
            gameBoardView.scrollY = getClippedDistanceY(yPosition).toInt()

            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (isAwaitingTap) {
                hasRolled()
            }
            return super.onSingleTapConfirmed(e)
        }
    }

    private fun getClippedScale(number: Float): Float {
        return getClippedValue(minimumGameBoardScale, number, maximumGameBoardScale)
    }

    private fun getClippedDistanceX(number: Float): Float {
        return getClippedValue(-maxHorizontalScroll, number, maxHorizontalScroll)
    }

    private fun getClippedDistanceY(number: Float): Float {
        return getClippedValue(-maxVerticalScroll, number, maxVerticalScroll)
    }

    fun getClippedValue(minNumber: Float, number: Float, maxNumber: Float): Float {
        return max(minNumber, min(number, maxNumber))
    }

    fun initializeFight(fightFragment: Fight) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.add(R.id.fightFragmentContainer, fightFragment)
        fragmentTransaction.commit()
    }

    fun removeFight(fightFragment: Fight) {
        val fragmentManager = this.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.remove(fightFragment)
        fragmentTransaction.commit()
    }

    fun showRollBanner() {
        bannerParent.visibility = View.VISIBLE
        bannerText.text = "tap screen to roll"
        isAwaitingTap = true
    }

    fun hasRolled() {
        bannerParent.visibility = View.INVISIBLE
        isAwaitingTap = false
        GameDirector.instance.onRolled()
    }

    fun spawnOptionsMenu(execution: (Int) -> Unit, options: ArrayList<String>) {
        this.currentDecisionPanelExecution = execution
        decisionPanel.startDecisionPanel(this, options)
    }

    override fun onDecisionMade(decisionIndex: Int) {
        currentDecisionPanelExecution(decisionIndex)
    }

    fun updateStepsToGoText(number: Int) {
        val string = "Steps to go: $number"

        val handler = Handler(mainLooper)
        val runnable = Runnable() {
            run {
                stepsToGoTextView.text = string
            }
        }
        handler.post(runnable)
    }

    companion object {
        lateinit var instance: BoardView
    }

}