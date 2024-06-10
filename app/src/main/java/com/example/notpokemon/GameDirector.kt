package com.example.notpokemon

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characterAmount = 2
    public var characters = ArrayList<PlayableCharacter>()
    private var playerTurn = 0
    private val characterSprites = ArrayList<Int>()
    private var spriteNumber = 0

    init {
        characterSprites.add(R.drawable.char_aquaboy)
        characterSprites.add(R.drawable.char_emogirl)
        characterSprites.add(R.drawable.char_nerdyboy)
        characterSprites.add(R.drawable.char_spoopygirl)
    }

    fun addCharacter(): PlayableCharacter{
        val character = PlayableCharacter(gameBoardFragment.getStartSquare(), "sally")
        character.addCreature(ButterPig(BiteAttack()))
        character.icon = iterateOverSprites()
        characters.add(character)
        return character
    }

    private fun iterateOverSprites(): Int{
        var sprite = characterSprites[spriteNumber]
        spriteNumber++
        if(spriteNumber >= characterSprites.size){
            spriteNumber = 0
        }
        return sprite
    }

    fun playTurn(character: PlayableCharacter){
        val number = DiceRoller.rollD6()
        character.moveThisManySpaces(number)
        playerTurn++
    }
    override fun run(){
        while(true){
            if(playerTurn < characters.size){
                playTurn(characters[playerTurn])
            }
            else if(characters.size < characterAmount){
                val character = addCharacter()
                playTurn(character)
            }
            else{
                playerTurn = 0
                playTurn(characters[playerTurn])
            }
        }
    }

}