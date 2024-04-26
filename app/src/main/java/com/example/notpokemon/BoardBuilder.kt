package com.example.notpokemon

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class BoardBuilder public constructor(private val fragmentTransaction: FragmentTransaction, private val parentView: View) {
    public fun generateSquare(){
        val squareFragment: Fragment = GameBoardSquareFragment.newInstance();
        fragmentTransaction.add(parentView.id, squareFragment, null);
        fragmentTransaction.commit()
    }
}