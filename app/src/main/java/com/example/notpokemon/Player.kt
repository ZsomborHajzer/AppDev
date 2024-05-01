package com.example.notpokemon

//General Player class
class Player(private var name: String, private var team: Array<Creature>, private var XP: Int) {

    fun getName(): String {
        return name
    }

    fun getTeam(): Array<Creature> {
        return team
    }

    fun getXP(): Int {
        return XP
    }

    fun setTeam(newTeam: Array<Creature>) {
        team = newTeam.clone()
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
    fun removeCreature(creatureToRemove: Creature) {
        team = team.toMutableList().apply { remove(creatureToRemove) }.toTypedArray()
    }
}
