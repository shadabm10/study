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

public class Assessment_adapter extends RecyclerView.Adapter<Assessment_adapter.PlanetViewHolder> {

    ArrayList<String> mData;
    ArrayList<String> mData1;
    ArrayList<String> mData2;
    Context context;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // data is passed into the constructor
    public Assessment_adapter(ArrayList<String> data, ArrayList<String> data1, ArrayList<String> data2, Context context) {
        this.mData = data;
        this.mData1 = data1;
        this.mData2 = data2;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public Assessment_adapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_adapter, parent, false);
        Assessment_adapter.PlanetViewHolder PlanetViewHolder = new Assessment_adapter.PlanetViewHolder(v);

        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {



        holder.text.setText(mData.get(position).toString());
        holder.text1.setText(mData1.get(position).toString());
        holder.text2.setText(mData2.get(position).toString());
        holder.edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Working", Toast.LENGTH_LONG).show();
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
        return mData.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder{

        protected TextView text,text1,text2;
        protected ImageView edit1;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.user);
            text1= (TextView) itemView.findViewById(R.id.date);
            text2= (TextView) itemView.findViewById(R.id.time);
            edit1 = itemView.findViewById(R.id.edit1);


        }
    }
    private void dialogAccessCreateDate() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay).show();


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

                new DatePickerDialog(context, datePickerListener1, mYear, mMonth, mDay).show();

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

        Button btn_no1 = dialogView.findViewById(R.id.btn_no);
        btn_no1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

    }

    EditText examdate, examtime, notify3, notify4;


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

                    notify3.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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