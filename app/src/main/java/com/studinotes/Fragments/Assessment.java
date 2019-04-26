package com.studinotes.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.studinotes.Activities.Setting;
import com.studinotes.Utils.Utils;
import com.studinotes.AdapterClass.Assessment_adapter;
import com.studinotes.AdapterClass.Assessment_adapter1;
import com.studinotes.AdapterClass.R;
import com.studinotes.AdapterClass.Sharedpreference;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Assessment extends Fragment {

    Assessment_adapter adapter;
    Assessment_adapter1 adapter1;
    RecyclerView.LayoutManager mLayoutManager,mLayoutManager1 ;
    ImageView edit1;
    RelativeLayout main_layout2;
    Sharedpreference sharedpreference;
    private int mYear, mMonth, mDay, mHour, mMinute;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.assessment, container, false);

        sharedpreference = new Sharedpreference(getActivity());
        main_layout2 = rootView.findViewById(R.id.main_layout2);

        //Returning the layout file after inflating
        //Change R.layout.studynotes in you classes

        ArrayList<String> mData = new ArrayList<>();
        mData.add("Examination Name");
        mData.add("Examination Name");
        mData.add("Examination Name");

        ArrayList<String> mData1 = new ArrayList<>();
        mData1.add("25/03/2019");
        mData1.add("25/03/2019");
        mData1.add("25/03/2019");

        ArrayList<String> mData2 = new ArrayList<>();
        mData2.add("12:00 p.m");
        mData2.add("12:00 p.m");
        mData2.add("12:00 p.m");



        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new Assessment_adapter(mData,mData1,mData2,getActivity());
        recyclerView.setAdapter(adapter);


        ArrayList<String> mData3 = new ArrayList<>();
        mData3.add("Assessment Name");
        mData3.add("Assessment Name");
        mData3.add("Assessment Name");

        ArrayList<String> mData4 = new ArrayList<>();
        mData4.add("25/03/2019");
        mData4.add("25/03/2019");
        mData4.add("25/03/2019");

        ArrayList<String> mData5 = new ArrayList<>();
        mData5.add("12:00 p.m");
        mData5.add("12:00 p.m");
        mData5.add("12:00 p.m");

        RecyclerView recyclerView1 = (RecyclerView) rootView.findViewById(R.id.recycle1);
        mLayoutManager1 = new LinearLayoutManager(this.getActivity());
        recyclerView1.setLayoutManager(mLayoutManager1);
        adapter1 = new Assessment_adapter1(mData3,mData4,mData5,getActivity());
        recyclerView1.setAdapter(adapter1);

        final ImageButton add = (ImageButton) rootView.findViewById(R.id.add);
        final ImageButton search = (ImageButton) rootView.findViewById(R.id.search);
        final LinearLayout searchfield = (LinearLayout) rootView.findViewById(R.id.searchfield);
        final ImageButton cross = (ImageButton) rootView.findViewById(R.id.cross);
        final RecyclerView recycle = (RecyclerView) rootView.findViewById(R.id.recycle);
        final RecyclerView recycle1 = (RecyclerView) rootView.findViewById(R.id.recycle1);
        final ImageButton add1 = (ImageButton) rootView.findViewById(R.id.add1);
        final Button exam = (Button) rootView.findViewById(R.id.exam);
        final Button assess = (Button) rootView.findViewById(R.id.assess);
        final ImageView edit1 = (ImageView) rootView.findViewById(R.id.edit1);
       // final ImageView calender = (ImageView) rootView.findViewById(R.id.calendar);
       // final EditText examdate = (EditText) rootView.findViewById(R.id.examdate);

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
                public void onClick(View arg0) {

                    dialogExamCreateDate();

                }

        });
        /*edit1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialogExamCreateDate();

            }

        });*/
       /* add1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                dialogExamCreateDate();

            }

        });*/
        assess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recycle1.setVisibility(View.VISIBLE);
                recycle.setVisibility(View.GONE);
                add.setVisibility(View.GONE);
                add1.setVisibility(View.VISIBLE);
                exam.setBackgroundResource(R.drawable.cancel_button);
                exam.setTextColor(Color.parseColor("#000000"));
                assess.setBackgroundResource(R.drawable.save_button);
                assess.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        exam.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                recycle1.setVisibility(View.GONE);
                recycle.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                add1.setVisibility(View.GONE);
                assess.setBackgroundResource(R.drawable.cancel_button);
                assess.setTextColor(Color.parseColor("#000000"));
                exam.setBackgroundResource(R.drawable.save_button);
                exam.setTextColor(Color.parseColor("#FFFFFF"));




            }
        });
        add1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dialogAccessCreateDate();


                }
            });
               /* Assessment_Dialog1 cdd = new Assessment_Dialog1(getActivity());
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();*/

        final ImageButton setting = (ImageButton) rootView.findViewById(R.id.settings);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
            }
        });


        return rootView;
    }


    EditText examdate, examtime,notify3,notify4;

    private void dialogExamCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.assessment_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);

        examdate = dialogView.findViewById(R.id.examdate);
        examtime = dialogView.findViewById(R.id.examtime);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);


        ImageButton calender = dialogView.findViewById(R.id.calendar);
        ImageButton clock = dialogView.findViewById(R.id.clock);
        ImageButton bell2 = dialogView.findViewById(R.id.bell2);


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener, mYear, mMonth, mDay).show();


            }


        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker();

            }
        });

        bell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker1();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener2, mYear, mMonth, mDay).show();


            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes = dialogView.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        Button btn_no = dialogView.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

    }

    EditText accessdate,accesstime,notify5,notify6;
    private void dialogAccessCreateDate(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.assessment_dialog1, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);


        accessdate = dialogView.findViewById(R.id.assessdate);
        accesstime = dialogView.findViewById(R.id.assesstime);
        notify5 = dialogView.findViewById(R.id.notify5);
        notify6 = dialogView.findViewById(R.id.notify6);


        ImageButton calender1 = dialogView.findViewById(R.id.calendar1);
        ImageButton clock1 = dialogView.findViewById(R.id.clock1);
        ImageButton bell3 = dialogView.findViewById(R.id.bell3);


        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener1, mYear, mMonth, mDay).show();


            }


        });

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker2();

            }
        });

        bell3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker3();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(getActivity(), datePickerListener3, mYear, mMonth, mDay).show();

            }
        });

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes1 = dialogView.findViewById(R.id.btn_yes1);
        btn_yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        Button btn_no1 = dialogView.findViewById(R.id.btn_no1);
        btn_no1.setOnClickListener(new View.OnClickListener() {
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

            examdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListener1 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    accessdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener2 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify3.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener3 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify5.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

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

                        examtime.setText(hourOfDay + ":" + minute);


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

                        notify4.setText(hourOfDay + ":" + minute);

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

                        accesstime.setText(hourOfDay + ":" + minute);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void timePicker3(){
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

                        notify6.setText(hourOfDay + ":" + minute);

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }



    @Override
    public void onResume() {

        Utils.changeBackgroundColor(getActivity(), main_layout2, sharedpreference.getStyle());
        super.onResume();
    }
}
