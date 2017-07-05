package com.example.damia.bullseyev3.game;

public class BullGame {
    private String hiddenWord;
    private int maxTries;
    private int currentTry;
    private int[] bullsAndHits = {0,0};

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
        reset();
    }

    public void reset(){
        hiddenWord = "ship";
        currentTry = 1;
        maxTries = 5;
        return;
    }
}
