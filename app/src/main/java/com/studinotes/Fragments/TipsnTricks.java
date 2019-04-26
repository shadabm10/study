package com.studinotes.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studinotes.Activities.Feedback;
import com.studinotes.Activities.Report;
import com.studinotes.Activities.Setting;
import com.studinotes.Activities.Suggestion;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;
import com.studinotes.Utils.Utils;

public class TipsnTricks extends Fragment {


    public static final String TAG = "MyFragment";

    private int position;

    // You can add other parameters here
    public static TipsnTricks newInstance(int position) {
        Bundle args = new Bundle();
        // Pass all the parameters to your bundle
        args.putInt("pos", position);
        TipsnTricks fragment = new TipsnTricks();
        fragment.setArguments(args);
        return fragment;
    }

    RelativeLayout main_layout4;
    Sharedpreference sharedpreference;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tipsntricks, container, false);
        super.onCreate(savedInstanceState);
        //this.position = getArguments().getInt("pos");

        sharedpreference = new Sharedpreference(getActivity());
        main_layout4 = rootView.findViewById(R.id.main_layout4);

        final TextView add1 = (TextView) rootView.findViewById(R.id.textView1);
        final TextView add11 = (TextView) rootView.findViewById(R.id.textView11);
        final TextView add2 = (TextView) rootView.findViewById(R.id.textView2);
        final TextView add21 = (TextView) rootView.findViewById(R.id.textView21);
        final TextView add3 = (TextView) rootView.findViewById(R.id.textView3);
        final TextView add31 = (TextView) rootView.findViewById(R.id.textView31);
        final TextView add4 = (TextView) rootView.findViewById(R.id.textView4);
        final TextView add41 = (TextView) rootView.findViewById(R.id.textView41);
        final TextView add5 = (TextView) rootView.findViewById(R.id.white);
        final TextView add6 = (TextView) rootView.findViewById(R.id.white1);
        final TextView add7 = (TextView) rootView.findViewById(R.id.white2);
        final TextView add8 = (TextView) rootView.findViewById(R.id.white3);


        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add5.setVisibility(View.VISIBLE);
                add11.setVisibility(View.VISIBLE);
                add1.setVisibility(View.INVISIBLE);
                add21.setVisibility(View.INVISIBLE);
                add31.setVisibility(View.INVISIBLE);
                add41.setVisibility(View.INVISIBLE);
                add2.setVisibility(View.VISIBLE);
                add3.setVisibility(View.VISIBLE);
                add4.setVisibility(View.VISIBLE);
                add6.setVisibility(View.GONE);
                add7.setVisibility(View.GONE);
                add8.setVisibility(View.GONE);


            }
        });
        add11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add5.setVisibility(View.GONE);
                add11.setVisibility(View.INVISIBLE);
                add1.setVisibility(View.VISIBLE);

            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add6.setVisibility(View.VISIBLE);
                add21.setVisibility(View.VISIBLE);
                add2.setVisibility(View.INVISIBLE);
                add11.setVisibility(View.INVISIBLE);
                add31.setVisibility(View.INVISIBLE);
                add41.setVisibility(View.INVISIBLE);
                add1.setVisibility(View.VISIBLE);
                add3.setVisibility(View.VISIBLE);
                add4.setVisibility(View.VISIBLE);
                add5.setVisibility(View.GONE);
                add7.setVisibility(View.GONE);
                add8.setVisibility(View.GONE);


            }
        });

        add21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add6.setVisibility(View.GONE);
                add21.setVisibility(View.INVISIBLE);
                add2.setVisibility(View.VISIBLE);

            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add7.setVisibility(View.VISIBLE);
                add31.setVisibility(View.VISIBLE);
                add3.setVisibility(View.INVISIBLE);
                add21.setVisibility(View.INVISIBLE);
                add11.setVisibility(View.INVISIBLE);
                add41.setVisibility(View.INVISIBLE);
                add2.setVisibility(View.VISIBLE);
                add1.setVisibility(View.VISIBLE);
                add4.setVisibility(View.VISIBLE);
                add6.setVisibility(View.GONE);
                add5.setVisibility(View.GONE);
                add8.setVisibility(View.GONE);

            }
        });

        add31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add7.setVisibility(View.GONE);
                add31.setVisibility(View.INVISIBLE);
                add3.setVisibility(View.VISIBLE);

            }
        });
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add8.setVisibility(View.VISIBLE);
                add41.setVisibility(View.VISIBLE);
                add4.setVisibility(View.INVISIBLE);
                add21.setVisibility(View.INVISIBLE);
                add31.setVisibility(View.INVISIBLE);
                add11.setVisibility(View.INVISIBLE);
                add2.setVisibility(View.VISIBLE);
                add3.setVisibility(View.VISIBLE);
                add1.setVisibility(View.VISIBLE);
                add6.setVisibility(View.GONE);
                add7.setVisibility(View.GONE);
                add5.setVisibility(View.GONE);

            }
        });

        add41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add8.setVisibility(View.GONE);
                add41.setVisibility(View.INVISIBLE);
                add4.setVisibility(View.VISIBLE);

            }
        });

        final Button suggest = (Button) rootView.findViewById(R.id.suggest);
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Suggestion.class);
                startActivity(intent);
            }
        });
        final Button feedback = (Button) rootView.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Feedback.class);
                startActivity(intent);
            }
        });
        final Button report = (Button) rootView.findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Report.class);
                startActivity(intent);
            }
        });

        final ImageButton settings = rootView.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });



        //Returning the layout file after inflating
        //Change R.layout.studynotes in you classes
        return rootView;


    }

    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout4, sharedpreference.getStyle());
        super.onResume();
    }
}