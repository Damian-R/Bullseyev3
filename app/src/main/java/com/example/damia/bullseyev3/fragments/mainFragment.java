package com.example.damia.bullseyev3.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.damia.bullseyev3.R;
import com.example.damia.bullseyev3.fragments.summaryFrag;
import com.example.damia.bullseyev3.game.BullGame;

import java.util.HashMap;
import java.util.Map;

public class mainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button submitBtn;
    BullGame game;
    TextView hiddenWordLengthtxt;
    TextView triesLeft;

    OnGuessSubmittedListener guessSubmittedListener;
    OnGameCreatedListener gameCreatedListener;

    public interface OnGuessSubmittedListener{
        public void guessSubmitted(String guess, int bulls, int hits);
    }

    public interface OnGameCreatedListener{
        public void gameCreated(BullGame game);
    }

    public enum ErrorList{
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

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Guess = guess_txt.getText().toString();
                Log.d("tag", Guess);
                if(checkGuessValidity(Guess)){
                    submitValidGuess(Guess);
                    int bulls = game.getBullsAndHits()[0];
                    int hits = game.getBullsAndHits()[1];
                    if(!game.gameWon) //if game is not won, submit the guess with bulls and hits
                        guessSubmittedListener.guessSubmitted(Guess, bulls, hits);
                    else //if game is won, call gameWon() method
                        gameWon();
                }
                if(game.getCurrentTry() > game.getMaxTries()) gameLost(); //if user is out of tries, call gameLost method

                triesLeft.setText("Tries Left: " + (game.getMaxTries() - game.getCurrentTry() + 1)); //update tries left on main fragment
                guess_txt.setText("");
            }
        });

        return v;
    }

    public boolean checkGuessValidity(String Guess){
        ErrorList Status = checkForErrors(Guess);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        // set alert message based on error type
        switch (Status){
            case Wrong_Length: builder.setMessage("Wrong Length");
                break;
            case Not_Isogram: builder.setMessage("Your guess must be an isogram. (each character can only appear once)");
                break;
            case Not_Lowercase: builder.setMessage("Your guess must contain only lowercase letters");
                break;
            case Invalid_Characters: builder.setMessage("Invalid input (only characters in the alphabet are permitted)");
        }

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
            if(map.get(Guess.charAt(i)) == null) //if the value for the key (character) is null (has not been changed since map initialization)
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
            if(!Character.isLetter(chars[i])) return false;
        }
        return true;
    }

    public void gameWon(){ //method called when game is won
        game.reset(); //reset game data

        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.linearlayout);
        ll.removeAllViews(); //clear linear layout view

        hiddenWordLengthtxt.setText("Hidden Word Length: " + game.getHiddenWordLength());
        triesLeft.setText("Tries Left: " + game.getMaxTries());

        //TODO fancy game won message
    }

    public void gameLost(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Game over, you ran out of tries");
        builder.setNegativeButton("Reset game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameWon();
            }
        });

        AlertDialog alert = builder.create();

        alert.show();

    }
}
