package com.example.damia.bullseyev3.game;

public class BullGame {
    private String hiddenWord;
    private int maxTries;
    private int currentTry;

    public int[] bullsAndHits = {0,0};
    public boolean gameWon;

    public String getHiddenWord(){ return hiddenWord; }
    public int getHiddenWordLength(){ return hiddenWord.length(); }
    public int getMaxTries() { return maxTries; }
    public int getCurrentTry(){ return currentTry; }
    public int[] getBullsAndHits(){ return bullsAndHits; }

    public void setBullsAndHits(int bulls, int hits){
        bullsAndHits[0] = bulls;
        bullsAndHits[1] = hits;
    }

    public void addBullsAndHits(int bulls, int hits){
        bullsAndHits[0] += bulls;
        bullsAndHits[1] += hits;
    }

    public void tryComplete(){
        currentTry++;
    }

    public BullGame(){
        reset(true);
    }

    public void reset(boolean randomNewWord){
        hiddenWord = "ship"; //TODO add hiddenWord randomization based on user input
        currentTry = 1;      //TODO add maxTries depending on word length
        maxTries = 5;
        gameWon = false;
        return;
    }
}
