package com.example.studinotes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.Theme.Utils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab3 extends Fragment {

    Tab3_adapter adapter;
    Tab3_adapter1 adapter1;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout main_layout3;
    Sharedpreference sharedpreference;
    private int mYear, mMonth, mDay, mHour, mMinute;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab3, container, false);

        sharedpreference = new Sharedpreference(getActivity());
        main_layout3 = rootView.findViewById(R.id.main_layout3);

        ArrayList<String> mData= new ArrayList<>();
        mData.add("day");
        mData.add("day");
        mData.add("day");
        mData.add("day");
        mData.add("day");

        ArrayList<String> mData1= new ArrayList<>();
        mData1.add("day");
        mData1.add("sub");
        mData1.add("day");
        mData1.add("day");
        mData1.add("day");

        ArrayList<String> mData2= new ArrayList<>();
        mData2.add("day");
        mData2.add("sub");
        mData2.add("time");
        mData2.add("social");
        mData2.add("random");

        ArrayList<String> mData3= new ArrayList<>();
        mData3.add("day");
        mData3.add("sub");
        mData3.add("day");
        mData3.add("day");
        mData3.add("day");

        ArrayList<String> mData4= new ArrayList<>();
        mData4.add("day");
        mData4.add("sub");
        mData4.add("day");
        mData4.add("day");
        mData4.add("day");

        /*ArrayList<String> mData5= new ArrayList<>();
        mData5.add("Mustard");
        mData5.add("Navy");
        mData5.add("Black");
        mData5.add("Dark Grey");
        mData5.add("Terracotta");*/



        ArrayList<String> data= new ArrayList<>();
        data.add("Mustard");
        data.add("Navy");
        data.add("Black");
        data.add("Dark Grey");
        data.add("Terracotta");
        ArrayList<String> data1= new ArrayList<>();
        data1.add("Mustard");
        data1.add("Navy");
        data1.add("Black");
        data1.add("Dark Grey");
        data1.add("Terracotta");
        ArrayList<String> data2= new ArrayList<>();
        data2.add("Mustard");
        data2.add("Navy");
        data2.add("Black");
        data2.add("Dark Grey");
        data2.add("Terracotta");


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle2);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new Tab3_adapter(mData,mData1,mData2,mData3,mData4,getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recycle3);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext());
        recyclerView1.setLayoutManager(mLayoutManager);
        adapter1 = new Tab3_adapter1(data,data1,data2,getActivity().getBaseContext());
        recyclerView1.setAdapter(adapter1);

        final ImageButton add2 = (ImageButton) rootView.findViewById(R.id.add2);

        /*add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogtab3 cdd = new Dialogtab3(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });*/

        final Button plan = (Button) rootView.findViewById(R.id.plan);
        final LinearLayout green1 = (LinearLayout) rootView.findViewById(R.id.green1);
        final LinearLayout green = (LinearLayout) rootView.findViewById(R.id.green);
        final LinearLayout tool1 = (LinearLayout) rootView.findViewById(R.id.tool1);
        final RecyclerView recycler3 = (RecyclerView) rootView.findViewById(R.id.recycle3);
        final RecyclerView recycler2 = (RecyclerView) rootView.findViewById(R.id.recycle2);
        final ImageButton back = (ImageButton) rootView.findViewById(R.id.back);
        final ImageButton search = (ImageButton) rootView.findViewById(R.id.search);
        final LinearLayout searchfield = (LinearLayout) rootView.findViewById(R.id.searchfield);
        final ImageButton cross = (ImageButton) rootView.findViewById(R.id.cross);

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               green1.setVisibility(View.VISIBLE);
                green.setVisibility(View.GONE);
               recycler3.setVisibility(View.VISIBLE);
                recycler2.setVisibility(View.GONE);
                plan.setVisibility(View.GONE);
                tool1.setVisibility(View.VISIBLE);
            }
        });

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                green1.setVisibility(View.INVISIBLE);
                green.setVisibility(View.VISIBLE);
                recycler3.setVisibility(View.INVISIBLE);
                recycler2.setVisibility(View.VISIBLE);
                plan.setVisibility(View.VISIBLE);
                tool1.setVisibility(View.INVISIBLE);
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
        add2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialogExamCreateDate();

            }

        });

        return rootView;



    }

    EditText day, start,end,notify3,notify4;

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogtab3, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        day = dialogView.findViewById(R.id.day);
        start = dialogView.findViewById(R.id.start);
        end = dialogView.findViewById(R.id.end);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);



        ImageButton calender2 = dialogView.findViewById(R.id.calendar2);
        ImageButton clock2 = dialogView.findViewById(R.id.clock2);
        ImageButton clock3 = dialogView.findViewById(R.id.clock3);
        ImageButton bell2 = dialogView.findViewById(R.id.bell2);


        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener, mYear, mMonth, mDay).show();


            }


        });

        clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker();

            }
        });

        clock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker1();

            }
        });

        bell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker2();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener1, mYear, mMonth, mDay).show();

            }
        });








        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_no = dialogView.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

    }


    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    day.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener1 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify3.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };


    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                        //  et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                        start.setText(hourOfDay + ":" + minute);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void timePicker1(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                        //  et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                        end.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void timePicker2(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);

                        //  et_show_date_time.setText(date_time+" "+hourOfDay + ":" + minute);

                        notify4.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout3, sharedpreference.getStyle());
        super.onResume();
    }
}
