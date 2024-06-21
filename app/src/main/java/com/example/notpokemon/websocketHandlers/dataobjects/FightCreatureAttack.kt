package com.example.notpokemon.websocketHandlers.dataobjects

data class FightCreatureAttack(
    val attackingCreatureIndex: Int,
    val defendingCreatureIndex: Int,
    val attackMoveIndex: Int,
    val chanceModifier: Int,
    val timeStamp: Long = System.currentTimeMillis(),
    val event: String = "creatureAttacks"
)