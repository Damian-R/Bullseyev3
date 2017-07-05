package com.example.damia.bullseyev3.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.damia.bullseyev3.R;

public class GameWonActivity extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamewonactivity);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.87), (int)(height*0.7));

        Button randomWord = (Button)findViewById(R.id.randomwordbtn);

        randomWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("random", true);
                setResult(RESULT_OK,i);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
