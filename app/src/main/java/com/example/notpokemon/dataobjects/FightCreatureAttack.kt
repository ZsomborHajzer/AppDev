package com.example.notpokemon.dataobjects

data class FightCreatureAttack(
    val event:String = "fightCreatureAttack",
    val attackingCreatureId:Int,
    val defendingCreatureId:Int,
    val attackMoveId:Int,
    val chanceModifier:Int,
    val timeStamp:Long
)