package com.example.damia.bullseyev3.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.damia.bullseyev3.R;
import com.example.damia.bullseyev3.activities.GameWonActivity;
import com.example.damia.bullseyev3.game.BullGame;

import java.util.HashMap;
import java.util.Map;

public class mainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button submitBtn;
    private BullGame game;
    private TextView hiddenWordLengthtxt;
    private TextView triesLeft;

    OnGuessSubmittedListener guessSubmittedListener;
    OnGameCreatedListener gameCreatedListener;

    public interface OnGuessSubmittedListener{
        public void guessSubmitted(String guess, int bulls, int hits);
    }

    public interface OnGameCreatedListener{
        public void gameCreated(BullGame game);
    }

    private enum ErrorList{
        OK,
        Wrong_Length,
        Not_Isogram,
        Not_Lowercase,
        Invalid_Characters,
    }


    public mainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static mainFragment newInstance(String param1, String param2) {
        mainFragment fragment = new mainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        submitBtn = (Button)v.findViewById(R.id.submit_guess_btn);
        hiddenWordLengthtxt = (TextView)v.findViewById(R.id.hiddenWordLengthTxt);
        triesLeft = (TextView)v.findViewById(R.id.triesLeftTxt);
        final EditText guess_txt = (EditText)v.findViewById(R.id.guess_text);

        guessSubmittedListener = (OnGuessSubmittedListener) getActivity();
        gameCreatedListener = (OnGameCreatedListener) getActivity();

        game = new BullGame();
        hiddenWordLengthtxt.setText("Hidden Word Length: " + game.getHiddenWordLength());
        triesLeft.setText("Tries Left: " + game.getMaxTries());
        gameCreatedListener.gameCreated(game);

        Log.d("hidden word", game.getHiddenWord());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long start = System.nanoTime();
                String Guess = guess_txt.getText().toString();
                Log.d("guess", Guess);
                if(checkGuessValidity(Guess)){
                    submitValidGuess(Guess);
                    int bulls = game.getBullsAndHits()[0];
                    int hits = game.getBullsAndHits()[1];
                    if(!game.gameWon) //if game is not won, submit the guess with bulls and hits
                        guessSubmittedListener.guessSubmitted(Guess, bulls, hits);
                    else //if game is won, call gameWon() method
                        gameEnd();
                }
                if((game.getCurrentTry() > game.getMaxTries()) && (!Guess.equals(game.getHiddenWord()))) gameEnd(); //if user is out of tries, call gameLost method

                triesLeft.setText("Tries Left: " + (game.getMaxTries() - game.getCurrentTry() + 1)); //update tries left on main fragment
                guess_txt.setText("");
                long end = System.nanoTime();
                Log.d("click","" + (end-start) + "ns");
            }
        });

        return v;
    }

    public boolean checkGuessValidity(String Guess){
        long start = System.nanoTime();
        long start2 = System.nanoTime();
        ErrorList Status = checkForErrors(Guess);
        long end2 = System.nanoTime();

        Log.d("checkerror", "checking for errors took " + (end2 - start2) + "ns");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // set alert message based on error type
        switch (Status){
            case Wrong_Length: builder.setMessage("Wrong length. Your guess must be " + game.getHiddenWordLength() + " letters long.");
                break;
            case Not_Isogram: builder.setMessage("Your guess must be an isogram. (each character can only appear once)");
                break;
            case Not_Lowercase: builder.setMessage("Your guess must contain only lowercase letters");
                break;
            case Invalid_Characters: builder.setMessage("Invalid input (only characters in the latin alphabet are permitted)");
        }
        long end = System.nanoTime();
        Log.d("validity", "checking guess validity took " + (end-start) + "ns");
        //if status is not ok
        if(Status != ErrorList.OK){
            AlertDialog alert = builder.create(); //create an alert with message describing error
            alert.setCanceledOnTouchOutside(true);
            alert.show(); //show the alert
            return false; //return false so the guess is not submitted
        }
        else {
            return true; //if guess is valid, return true and add to current try
        }



    }

    public void submitValidGuess(String Guess){
        game.setBullsAndHits(0,0);
        String hiddenWord = game.getHiddenWord();
        for(int HWChar = 0; HWChar < hiddenWord.length(); HWChar++){ //loop thru all hidden word's characters
            for(int GChar = 0; GChar < hiddenWord.length(); GChar++){ //loop thru all of the guess's characters
                if(hiddenWord.charAt(HWChar) == Guess.charAt(GChar)){ //if a common character is found
                    if(HWChar == GChar) //same place
                        game.bullsAndHits[0]++; //add 1 bull
                    else //not same place
                        game.bullsAndHits[1]++; //add 1 hit
                }
            }
        }
        if(game.bullsAndHits[0] == hiddenWord.length()) game.gameWon = true;

        game.tryComplete();
    }

    public ErrorList checkForErrors(String Guess){
        if(Guess.length() != game.getHiddenWordLength()) return ErrorList.Wrong_Length;
        else if(!isValidInput(Guess)) return ErrorList.Invalid_Characters;
        else if(!isIsogram(Guess)) return ErrorList.Not_Isogram;
        else if(!isLowercase(Guess)) return ErrorList.Not_Lowercase;
        else return ErrorList.OK;
    }

    public boolean isIsogram(String Guess){
        Map<Character, Boolean> map = new HashMap();

        for(int i = 0; i < Guess.length(); i++){
            if(!map.containsKey(Guess.charAt(i)))//if the value for the key (character) is null (has not been changed since map initialization)
                map.put(Guess.charAt(i), true); //then set it to true (indicating that it has been seen)
            else { //else (if the value at the character HAS been changed since initialization, ie. it has been seen)
                Log.d("Character repeated", "" + Guess.charAt(i));
                return false; //return false
            }
        }
        return true; //if loop completes no duplicates were found and guess is an isogram
    }

    public boolean isLowercase(String Guess){
        if(Guess.equals(Guess.toLowerCase())) return true;
        else return false;
    }

    public boolean isValidInput(String Guess){
        char[] chars = Guess.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(!isLatinLetter(chars[i])) return false;
        }
        return true;
    }

    public static boolean isLatinLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    public void gameEnd(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Intent i = new Intent(getActivity(), GameWonActivity.class);
        i.putExtra("result", game.gameWon);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        game.reset(data.getBooleanExtra("random", true), data.getIntExtra("length", 3));
        resetUI();
    }

    public void resetUI(){ //reset UI after game is won or lost
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
        ll.removeAllViews(); //clear linear layout view

        hiddenWordLengthtxt.setText("Hidden Word Length: " + game.getHiddenWordLength());
        triesLeft.setText("Tries Left: " + game.getMaxTries());
    }
}
