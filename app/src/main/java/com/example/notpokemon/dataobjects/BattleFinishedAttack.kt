package com.example.notpokemon.dataobjects

data class BattleFinishedAttack(
    val creatureDied: Boolean,
    val hasNextCreature: Boolean,
    val teamWonId: String = "",
    val timeStamp:Long = System.currentTimeMillis(),
    val eventString: String = "finishedAttack"
)