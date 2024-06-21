package com.example.notpokemon.randomTrapTiles

import com.example.notpokemon.playerObjects.PlayableCharacter

abstract class RandomCard(open var player: PlayableCharacter) {

    open fun appliedEffect(){
    }
}