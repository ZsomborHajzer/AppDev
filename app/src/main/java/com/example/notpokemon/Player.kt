package com.example.notpokemon

import android.credentials.CreateCredentialException

//General Player class
class Player(private var name: String, private var team: ArrayList<Creature>, private var XP: Int) {

    fun getName(): String {
        return name
    }

    fun getTeam(): ArrayList<Creature> {
        return team
    }

    fun getXP(): Int {
        return XP
    }

    fun setTeam(newTeam: ArrayList<Creature>) {
        team = newTeam
    }

    fun setXP(newXPAmount: Int){
        XP = newXPAmount
    }

    //add new creatures to the team
    fun addCreature(newCreature: Creature){
        if(team.size < 4){
            team += newCreature
        }
        else{
            println("Team is currently full!")
        }
    }

    //remove a creature from the team
    fun removeCreature(creatureToRemove: Int) {
        val creatureName = team[creatureToRemove].getName()
        println(creatureName + "HAS DIED IS REMOVED")
        team.removeAt(creatureToRemove)
    }
}
