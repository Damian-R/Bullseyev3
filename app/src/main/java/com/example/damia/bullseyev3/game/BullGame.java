package com.example.damia.bullseyev3.game;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class BullGame {
    private String hiddenWord;
    private int maxTries;
    private int currentTry;

    private String[] threeWords = {"ate", "ape", "mad", "sad"}; //arrays containing all hidden words
    private String[] fourWords = {"ship", "shop", "hike", "bath", "sing"};
    private String[] fiveWords = {"candy", "stair", "build", "trays", "lacks", "dying", "plots"};

    private Map<Integer, String[]> wordLists = new HashMap<Integer, String[]>(){{ //map containing all possible hidden words with different lengths
        put(3, threeWords);
        put(4, fourWords);
        put(5, fiveWords);
    }};

    private Map<Integer, Integer> appropriateMaxTries = new HashMap<Integer, Integer>(){{ //set max tries based on hidden word length
        put(3, 7);
        put(4, 10);
        put(5, 13);
        put(6, 15);
    }};

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
        generateRandomWord();
        reset(false);
    }

    public void reset(boolean randomNewWord){
        if(randomNewWord) generateRandomWord();
        currentTry = 1;
        maxTries = appropriateMaxTries.get(getHiddenWordLength()); //get max tries based on hidden word length
        gameWon = false;
        return;
    }

    private void generateRandomWord(){ //TODO add parameter for user input on word length to determine length variable
        int length = (int)(3 * Math.random() + 3);
        Log.d("word length", "" + length);
        String[] words = wordLists.get(length);
        int wordPosition = (int)(words.length * Math.random());
        hiddenWord = words[wordPosition];
        Log.d("hidden word", hiddenWord);
    }
}
