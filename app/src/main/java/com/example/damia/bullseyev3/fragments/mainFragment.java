package com.example.damia.bullseyev3.fragments;


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

public class mainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button submitBtn;

    OnGuessSubmittedListener guessSubmittedListener;

    public interface OnGuessSubmittedListener{
        public void guessSubmitted(String guess);
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
        final EditText guess_txt = (EditText)v.findViewById(R.id.guess_text);

        guessSubmittedListener = (OnGuessSubmittedListener) getActivity();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Guess = guess_txt.getText().toString();
                Log.d("tag", Guess);
                //check if guess is valid
                guessSubmittedListener.guessSubmitted(Guess);
                guess_txt.setText("");
            }
        });

        return v;
    }
}
