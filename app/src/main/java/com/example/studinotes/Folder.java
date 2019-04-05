package com.example.studinotes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Theme.Utils;

import java.util.ArrayList;

public class Folder extends Fragment {

    ImageView next;
    ImageButton add,back4,search,cross,settings;
    LinearLayout searchfield;
    TextView forgot, register,text4,text5,text6,text7,textView;
    Folderadapter1 adapter;
    Folderadapter2 adapter2;
    Folderadapter3 adapter3;
    Folderadapter4 adapter4;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1,mLayoutManager2,mLayoutManager3 ;
    RelativeLayout main_layout;
    Sharedpreference sharedpreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.folder, container, false);

        sharedpreference = new Sharedpreference(getActivity());
        main_layout = rootView.findViewById(R.id.main_layout);

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
        RecyclerView recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recycle1);
        RecyclerView recyclerView2 = (RecyclerView) rootView.findViewById(R.id.recycle2);
        RecyclerView recyclerView3 = (RecyclerView) rootView.findViewById(R.id.recycle3);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView2.setLayoutManager(mLayoutManager2);
        recyclerView3.setLayoutManager(mLayoutManager3);

        adapter = new Folderadapter1(planetList,getActivity());
        adapter2 = new Folderadapter2(planetList,getActivity());
        adapter3 = new Folderadapter3(planetList,getActivity());
        adapter4 = new Folderadapter4(planetList,getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(adapter2);
        recyclerView2.setAdapter(adapter3);
        recyclerView3.setAdapter(adapter4);


        text4 = rootView.findViewById(R.id.text4);
        text4.setText(R.string.suggest);
        text5 = rootView.findViewById(R.id.text5);
        text5.setText(R.string.documents);
        text6 = rootView.findViewById(R.id.text6);
        text6.setText(R.string.video);
        text7 = rootView.findViewById(R.id.text7);
        text7.setText(R.string.audio);

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        add = rootView.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogtab4 cdd = new Dialogtab4(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });

        search = rootView.findViewById(R.id.search);
        searchfield = rootView.findViewById(R.id.searchfield);
        textView = rootView.findViewById(R.id.textView);
        cross = rootView.findViewById(R.id.cross);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.VISIBLE);
               /* textView.setVisibility(View.GONE);
                back4.setVisibility(View.GONE);
*/
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchfield.setVisibility(View.INVISIBLE);
                /*textView.setVisibility(View.VISIBLE);
                back4.setVisibility(View.VISIBLE);*/

            }
        });

        back4 = rootView.findViewById(R.id.back4);


        back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HomePage.class);
                startActivity(intent);
            }
        });

        settings = rootView.findViewById(R.id.settings);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Setting.class);
                startActivity(intent);
            }
        });

        return rootView;


        }


    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout, sharedpreference.getStyle());

        super.onResume();
    }
}

