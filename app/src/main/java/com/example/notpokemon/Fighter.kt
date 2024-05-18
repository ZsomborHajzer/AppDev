package com.example.notpokemon

//General Player class
open class Fighter(private var name: String) {
    var team = ArrayList<Creature>()
    protected var XP = 0

    fun getName(): String {
        return name
    }
    
    fun addXP(gainedXP:Int){
        XP += gainedXP
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
        team.removeAt(creatureToRemove)
    }

    fun healCreaturesFully(){
        for(creature in team){
            creature.heal(999)
        }
    }
}
