package com.example.studinotes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Theme.Utils;

import java.util.ArrayList;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab1 extends Fragment  {

    Tab1_adapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab1, container, false);

        sharedpreference = new Sharedpreference(getActivity());
        main_layout = rootView.findViewById(R.id.main_layout);


        final TextView text3 = (TextView) rootView.findViewById(R.id.text3);
        text3.setText("XYZ");
        final TextView text4 = (TextView) rootView.findViewById(R.id.text4);
        text4.setText("XYZ");


        ArrayList<String> planetList= new ArrayList<>();
        planetList.add("Mustard");
        planetList.add("Navy");
        planetList.add("Black");
        planetList.add("Dark Grey");
        planetList.add("Terracotta");

     /*   ArrayList<Integer>images = new ArrayList<>();
        images.add(0,R.mipmap.foldermustard);
        images.add(0,R.mipmap.foldernavy);
        images.add(0,R.mipmap.folderblack);
        images.add(0,R.mipmap.folderdarkgery);
        images.add(0,R.mipmap.folderterracotta);
*/

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new Tab1_adapter(planetList,getActivity());
        recyclerView.setAdapter(adapter);

        final ImageButton search = (ImageButton) rootView.findViewById(R.id.search);
        final LinearLayout searchfield = (LinearLayout) rootView.findViewById(R.id.searchfield);
        final ImageButton cross = (ImageButton) rootView.findViewById(R.id.cross);
        final ImageButton settings = (ImageButton) rootView.findViewById(R.id.settings);
        final ImageButton add = (ImageButton) rootView.findViewById(R.id.add);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.VISIBLE);
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.INVISIBLE);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogtab1 cdd = new Dialogtab1(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });

        final ImageButton setting = (ImageButton) rootView.findViewById(R.id.settings);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Setting.class);
                startActivity(intent);
            }
        });

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return rootView;

    }

    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout, sharedpreference.getStyle());

        super.onResume();
    }
}
