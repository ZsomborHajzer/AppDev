package com.example.notpokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class DecisionSelectableBox(val caller:DecisionTrackingClass, val answerIndex:Int, val textString:String) : Fragment() {

    lateinit var backgroundImage: ImageView
    lateinit var textView: TextView
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decision_selectable_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundImage = requireView().findViewById<ImageView>(R.id.decisionSelectableBoxBackgroundImage)
        textView = requireView().findViewById<TextView>(R.id.decisionSelectableBoxText)

        textView.text = textString
        backgroundImage.setOnClickListener(buttonOnClickListener(caller, answerIndex))
    }

    inner class buttonOnClickListener(caller:DecisionTrackingClass, answerIndex: Int): View.OnClickListener{
        override fun onClick(p0: View?) {
            caller.onDecisionMade(answerIndex)
        }

    }

    companion object {

    }
}