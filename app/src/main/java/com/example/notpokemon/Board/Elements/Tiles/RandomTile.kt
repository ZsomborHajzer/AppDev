package com.example.notpokemon.Board.Elements.Tiles

import com.example.notpokemon.AddTypeDisadvantage
import com.example.notpokemon.DepleteHP
import com.example.notpokemon.Heal
import com.example.notpokemon.PlayableCharacter
import com.example.notpokemon.RandomCard
import com.example.notpokemon.RandomXPGain
import com.example.notpokemon.GiveStatusEffect

class RandomTile : BaseTile() {

    fun addEffect(character: PlayableCharacter): Array<RandomCard> {
        var depleteHP = DepleteHP(character)
        var randomXPGain = RandomXPGain(character)
        var addTypeDisadvantage = AddTypeDisadvantage(character)
        var healEffectStatus = Heal(character)
        var giveHealStatus = GiveStatusEffect(character, healEffectStatus)
        var effect = arrayOf(depleteHP, randomXPGain, addTypeDisadvantage, giveHealStatus)

        return effect
    }

    fun randomEffect(character: PlayableCharacter): RandomCard {
        var effect = addEffect(character).random()

        return effect
    }

    override fun onTileStay(character: PlayableCharacter): Boolean {
        randomEffect(character).appliedEffect()
        return true
    }
}