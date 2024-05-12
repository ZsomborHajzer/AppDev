package com.example.notpokemon


class Character(square: SteppableTile) {
    private var currentSquare: SteppableTile;
    private var squareHistory = ArrayList<SteppableTile>();
    val icon = R.drawable.low_res_tanuki

    init {
        this.currentSquare = square;
        onMove(currentSquare)
    }

    fun moveThisManySpaces(number:Int){
        if (number < 1){
            throw IllegalArgumentException("function \"moveThisManySpaces\" from" + this.javaClass + "may not be less than 0")
        }
        var localCurrentSquare = currentSquare
        var waitTime = 500L;
        var i = 1
        while (i <= number){
            localCurrentSquare = localCurrentSquare.nextSquare // TODO:: Implement multiple routes
            onMove(localCurrentSquare)
            i++;
            Thread.sleep(waitTime)
        }
    }

    public fun onInteractSquare(){
        // for now won't be used. but always good to have extra event opportunities
    }

    public fun onMove(square:SteppableTile){
        currentSquare.onTileExit()
        addToSquareHistory(currentSquare)
        currentSquare = square
        currentSquare.onTileEntry(this)
    }

    fun addToSquareHistory(square: SteppableTile){
        this.squareHistory.add(square)
        if(squareHistory.size > maxSquareHistory){
            squareHistory.removeFirst();
        }
    }

    companion object{
        val maxSquareHistory = 10;
    }
}
