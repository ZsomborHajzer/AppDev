package com.example.notpokemon.dataobjects

data class FightCreatureAttack(
    val attackingCreatureId:Int,
    val defendingCreatureId:Int,
    val attackMoveIndex:Int,
    val chanceModifier:Int,
    val timeStamp:Long = System.currentTimeMillis(),
    val event:String = "creatureAttacks"
)