package com.example.damia.bullseyev3.game;

public class BullGame {
    private String hiddenWord;
    private int maxTries;
    private int currentTry;

    public String getHiddenWord(){ return hiddenWord; }
    public int getHiddenWordLength(){ return hiddenWord.length(); }
    public int getMaxTries() { return maxTries; }
    public int getCurrentTry(){ return currentTry; }

    public void tryComplete(){
        currentTry++;
    }

    public BullGame(){
        reset();
    }

    public void reset(){
        hiddenWord = "test";
        currentTry = 1;
        maxTries = 5;
        return;
    }
}
