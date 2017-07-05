package com.example.damia.bullseyev3.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.damia.bullseyev3.R;

public class summaryFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String Guess;
    private int bulls;
    private int hits;

    TextView prev_guess_txt;
    TextView bullsTxt;
    TextView hitsTxt;

    public summaryFrag() {
        // Required empty public constructor
    }

    public static summaryFrag newInstance(String guess, int bulls, int hits) {
        summaryFrag fragment = new summaryFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, guess);
        args.putInt(ARG_PARAM2, bulls);
        args.putInt(ARG_PARAM3, hits);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Guess = getArguments().getString(ARG_PARAM1);
            bulls = getArguments().getInt(ARG_PARAM2);
            hits = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.summary, container, false);

        prev_guess_txt = (TextView)v.findViewById(R.id.prev_guess_txt);
        bullsTxt = (TextView)v.findViewById(R.id.bulls_txt);
        hitsTxt = (TextView)v.findViewById(R.id.hits_txt);

        bullsTxt.setText("" + bulls);
        hitsTxt.setText("" + hits);

        setPrev_guess_txt(Guess);

        return v;
    }

    public void setPrev_guess_txt(String Guess){
        prev_guess_txt.setText(Guess);
    }
}
