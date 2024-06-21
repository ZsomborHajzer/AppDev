package com.example.notpokemon.views.fragments.Board.Elements.Tiles

import com.example.notpokemon.randomTrapTiles.AddTypeDisadvantage
import com.example.notpokemon.statusEffects.DepleteHP
import com.example.notpokemon.statusEffects.Heal
import com.example.notpokemon.playerObjects.PlayableCharacter
import com.example.notpokemon.randomTrapTiles.RandomCard
import com.example.notpokemon.randomTrapTiles.RandomXPGain
import com.example.notpokemon.statusEffects.GiveStatusEffect

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