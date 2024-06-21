package com.example.notpokemon.websocketHandlers.dataobjects

data class BattleReadyToFight(
    val timeStamp:Long = System.currentTimeMillis(),
    val event: String = "readyToFight"
)