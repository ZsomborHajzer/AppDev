package com.example.notpokemon.websocketHandlers.dataobjects

data class BattleFinishedAttack(
    val creatureDied: Boolean,
    val hasNextCreature: Boolean,
    val teamWonIndex: Int = -1,
    val timeStamp: Long = System.currentTimeMillis(),
    val event: String = "finishedAttack"
)