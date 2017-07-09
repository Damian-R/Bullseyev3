package com.example.damia.bullseyev3.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.damia.bullseyev3.R;

public class GameWonActivity extends Activity{

    LinearLayout container;
    Spinner spinner;
    TextView banner;

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
        Button sameWord = (Button)findViewById(R.id.samewordbtn);
        Button goButton = (Button)findViewById(R.id.goButton);

        banner = (TextView)findViewById(R.id.bannerTxt);
        if(getIntent().getBooleanExtra("result", true)) {
            banner.setText("Congratulations, You win!");
        }else{
            banner.setText("Out of Tries, Game over!");
        }


        container = (LinearLayout)findViewById(R.id.container);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this, R.array.lengths, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        randomWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.VISIBLE);
            }
        });

        sameWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("random", false);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = Integer.parseInt(spinner.getSelectedItem().toString());
                Log.d("selected", "" + length);
                Intent i = new Intent();
                i.putExtra("random", true);
                i.putExtra("length", length);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
