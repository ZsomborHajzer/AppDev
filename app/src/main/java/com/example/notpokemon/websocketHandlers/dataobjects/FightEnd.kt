package com.example.notpokemon.websocketHandlers.dataobjects

data class FightEnd(val event: String = "fightEnd", val winnerId:Int, val timeStamp:Long)
