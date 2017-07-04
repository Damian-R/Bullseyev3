package com.example.damia.bullseyev3.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.damia.bullseyev3.R;
import com.example.damia.bullseyev3.fragments.mainFragment;
import com.example.damia.bullseyev3.fragments.summaryFrag;

public class MainActivity extends AppCompatActivity implements mainFragment.OnGuessSubmittedListener{

    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        mainFragment mainfrag = (mainFragment)fm.findFragmentById(R.id.mainfragment_container);

        ll = (LinearLayout)findViewById(R.id.linearlayout);

        if(mainfrag == null){
            mainfrag = mainFragment.newInstance("","");
            fm.beginTransaction().add(R.id.mainfragment_container, mainfrag).commit();
        }

    }

    @Override
    public void guessSubmitted(String Guess){

        FragmentManager fm = getSupportFragmentManager();

        FrameLayout frame = new FrameLayout(this);
        frame.setId(R.id.holder);

        summaryFrag summary = summaryFrag.newInstance(Guess);
        fm.beginTransaction().add(frame.getId(), summary).commit();

        ll.addView(frame,0);


    }
}


