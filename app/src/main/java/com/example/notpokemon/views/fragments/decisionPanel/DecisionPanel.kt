package com.example.notpokemon.views.fragments.decisionPanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.notpokemon.R

class DecisionPanel : Fragment(), DecisionTrackingClass {
    lateinit var innerDecisionTableLayout: LinearLayout
    var optionButtons = ArrayList<DecisionSelectableBox>()
    private lateinit var caller: DecisionTrackingClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decision_panel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        innerDecisionTableLayout = requireView().findViewById(R.id.innerDecisionTableLayout)
        super.onViewCreated(view, savedInstanceState)
        this.requireView().visibility = View.INVISIBLE
    }

    fun startDecisionPanel(caller: DecisionTrackingClass, options:ArrayList<String>){
        this.caller = caller
        this.requireView().visibility = View.VISIBLE
        createOptions(options)
    }

    private fun createOptions(options:ArrayList<String>){
        for ((i, option) in options.withIndex()){
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val thisClass = this
            transaction.setReorderingAllowed(true)
            transaction.add(innerDecisionTableLayout.id, DecisionSelectableBox(thisClass, i, option), "SelectableOption${i}")
            transaction.commit()
        }
    }

    override fun onDecisionMade(decisionIndex: Int) {
        caller.onDecisionMade(decisionIndex)
        removeAllOptionButtons()
        requireView().visibility = View.INVISIBLE
    }

    fun removeAllOptionButtons(){
        innerDecisionTableLayout.removeAllViewsInLayout()
        optionButtons.clear()
    }
}