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

    private String mParam1;

    TextView prev_guess_txt;


    public summaryFrag() {
        // Required empty public constructor
    }

    public static summaryFrag newInstance(String param1) {
        summaryFrag fragment = new summaryFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.summary, container, false);

        prev_guess_txt = (TextView)v.findViewById(R.id.prev_guess_txt);
        Log.d("tag2", mParam1);
        setPrev_guess_txt(mParam1);

        return v;
    }

    public void setPrev_guess_txt(String Guess){
        prev_guess_txt.setText(Guess);
    }
}
