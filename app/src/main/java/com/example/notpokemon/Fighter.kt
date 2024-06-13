package com.example.notpokemon

//General Player class
open class Fighter(var name: String) {
    var team = ArrayList<Creature>()
    protected var XP = 0
    protected var level = 0

    fun addXP(xpPerBattle: Int){
        XP += xpPerBattle
        addLevel()
    }

    //checks player level and depending on the XP amount will level them up, along with necessary stats
    fun addLevel(){
        when(level){
            0 -> {
                if((XP) >= 10){
                    level = 1
                    teamHPLevelUp(1.2)
                    teamDMGLevelUp(1.2)
                }
            }
            1 -> {
                if((XP) >= 15){
                    level = 2
                    teamHPLevelUp(1.5)
                    teamDMGLevelUp(1.5)
                }
            }
            2 -> {
                if((XP) >= 22.5){
                    level = 3
                    teamHPLevelUp(2.0)
                    teamDMGLevelUp(2.0)
                }
            }
        }
    }

    //increases the team's max health points for leveling up
    fun teamHPLevelUp(inflationNumber: Double){
        for(creature in team){
            creature.maxHealthPoints *= inflationNumber
        }
    }

    fun teamDMGLevelUp(inflationNumber: Double){
        for(creature in team){
            creature.attack.damage *= inflationNumber
        }
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
