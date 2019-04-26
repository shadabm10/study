package com.studinotes.AdapterClass;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class Assessment_adapter1 extends RecyclerView.Adapter<Assessment_adapter1.PlanetViewHolder> {

    private ArrayList<String> mData3;
    private ArrayList<String> mData4;
    private ArrayList<String> mData5;
    private Context context;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // data is passed into the constructor
    public Assessment_adapter1(ArrayList<String> data3, ArrayList<String> data4, ArrayList<String> data5, Context context) {
        this.mData3 = data3;
        this.mData4 = data4;
        this.mData5 = data5;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public Assessment_adapter1.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_adapter1, parent, false);
        Assessment_adapter1.PlanetViewHolder PlanetViewHolder = new Assessment_adapter1.PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        holder.text3.setText(mData3.get(position).toString());
        holder.text4.setText(mData4.get(position).toString());
        holder.text5.setText(mData5.get(position).toString());

        holder.edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Working", Toast.LENGTH_LONG).show();
                dialogAccessCreateDate();

            }

        });



        /*String animal = mData.get(position);
        holder.myTextView.setText(animal);

        String animal1 = mData1.get(position);
        holder.myTextView1.setText(animal1);

        String animal2 = mData2.get(position);
        holder.myTextView2.setText(animal2);*/
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData3.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

        protected TextView text3, text4, text5;
        ImageView edit1;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text3 = (TextView) itemView.findViewById(R.id.user1);
            text4 = (TextView) itemView.findViewById(R.id.date1);
            text5 = (TextView) itemView.findViewById(R.id.time1);
            edit1 = itemView.findViewById(R.id.edit2);

        }
    }


    private void dialogAccessCreateDate() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay).show();


            }


        });

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker();

            }
        });

        bell3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker1();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(context, datePickerListener1, mYear, mMonth, mDay).show();

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

    EditText accessdate, accesstime, notify5, notify6;


    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    accessdate.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };
    private DatePickerDialog.OnDateSetListener datePickerListener1 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify5.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };


    private void timePicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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

    private void timePicker1(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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










    {


        // stores and recycles views as they are scrolled off screen
   /* public class PlanetViewHolder extends RecyclerView.PlanetViewHolder implements View.OnClickListener {

        TextView myTextView, myTextView1, myTextView2;

        PlanetViewHolder(View itemView) {
            super(itemView);

            myTextView = itemView.findViewById(R.id.user);
            itemView.setOnClickListener(this);
            myTextView1 = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
            myTextView2 = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(this);
        }
    }*/
    }
}
