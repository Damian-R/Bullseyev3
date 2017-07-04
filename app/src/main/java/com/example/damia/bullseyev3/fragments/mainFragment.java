package com.example.damia.bullseyev3.fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
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
        public void guessSubmitted(String guess);
    }

    public interface OnGameCreatedListener{
        public void gameCreated(BullGame game);
    }

    public enum ErrorList{
        OK,
        Wrong_Length,
        Not_Isogram,
        Not_Lowercase,
        Invalid_Status
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
                    //TODO calculate bulls and cows
                    //TODO also pass in bulls and cows to the guessSubmittedListener
                    guessSubmittedListener.guessSubmitted(Guess);
                }
                triesLeft.setText("Tries Left: " + (game.getMaxTries() - game.getCurrentTry() + 1));
                guess_txt.setText("");
            }
        });

        return v;
    }

    public boolean checkGuessValidity(String Guess){
        ErrorList Status = checkForErrors(Guess);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // get error message
        switch (Status){
            case Wrong_Length: builder.setMessage("Wrong Length");
                break;
            case Not_Isogram: builder.setMessage("Not Isogram");
                break;
            case Not_Lowercase: builder.setMessage("Not Lowercase");
                break;
        }

        //if status is not ok
        if(Status != ErrorList.OK){
            AlertDialog alert = builder.create(); //create an alert with message describing error
            alert.show(); //show the alert
            return false; //return false so the guess is not submitted
        }
        else {
            game.tryComplete();
            return true; //if guess is valid, return true and add to current try
        }
    }

    public ErrorList checkForErrors(String Guess){
        if(Guess.length() != game.getHiddenWordLength()) return ErrorList.Wrong_Length;
        else if(!isIsogram(Guess)) return ErrorList.Not_Isogram;
        else if(!isLowercase(Guess)) return ErrorList.Not_Lowercase;
        else return ErrorList.OK;
    }

    public boolean isIsogram(String Guess){
        return true;
    }

    public boolean isLowercase(String Guess){
        return true;
    }
}
