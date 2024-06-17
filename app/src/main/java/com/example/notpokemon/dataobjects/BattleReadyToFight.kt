package com.example.notpokemon.dataobjects

data class BattleReadyToFight(
    val timeStamp:Long = System.currentTimeMillis(),
    val event: String = "readyToFight"
)