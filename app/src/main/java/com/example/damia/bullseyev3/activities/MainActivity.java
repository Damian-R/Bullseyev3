package com.example.damia.bullseyev3.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.damia.bullseyev3.R;
import com.example.damia.bullseyev3.fragments.mainFragment;
import com.example.damia.bullseyev3.fragments.summaryFrag;
import com.example.damia.bullseyev3.game.BullGame;

public class MainActivity extends AppCompatActivity implements mainFragment.OnGuessSubmittedListener, mainFragment.OnGameCreatedListener{

    private BullGame currentgame;
    private LinearLayout ll;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        mainFragment mainfrag = (mainFragment)fm.findFragmentById(R.id.mainfragment_container);

        scrollView = (ScrollView) findViewById(R.id.scrollview);

        getWindow().setBackgroundDrawableResource(R.drawable.random_bg);

        if(mainfrag == null){
            mainfrag = mainFragment.newInstance("","");
            fm.beginTransaction().add(R.id.mainfragment_container, mainfrag).commit();
        }

    }

    // called when user presses submit guess button
    // creates a new summary fragment and adds the guess to the textview
    @Override
    public void guessSubmitted(String Guess, int bulls, int hits){
        //TODO also update bulls and hits numbers after guess is validated and submitted

        // Status = checkGuessValidity(Guess); <-- loop this until status = OK, use enum
        // bullsAndHits[] = submitValidGuess(Guess); <-- returns array where [0] is bulls and [1] is hits

        addSummaryFragment(Guess, bulls, hits); //<-- and array parameter for bulls and hits, then update the other two text views and the appropriate bulls and hits
    }

    @Override
    public void gameCreated(BullGame game){
        currentgame = game;
    }

    public void addSummaryFragment(String Guess, int bulls, int hits){
        ll = (LinearLayout)findViewById(R.id.linearlayout); //get linear layout
        FragmentManager fm = getSupportFragmentManager();

        FrameLayout frame = new FrameLayout(this); //new framelayout to hold fragment
        frame.setId(R.id.holder); //some id

        // must add a new argument to summaryfrag to hold the int array of bulls and hits
        summaryFrag summary = summaryFrag.newInstance(Guess, bulls, hits); //make a brand new instance of a summaryFrag with argument of Guess
        fm.beginTransaction().add(frame.getId(), summary).commit(); //commit the fragment

        ll.addView(frame,0); //add the frame that holds the new fragment to the linearlayout
        scrollView.fullScroll(View.FOCUS_UP);
    }
}


