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

public class TimeManagement_adapter extends RecyclerView.Adapter<TimeManagement_adapter.PlanetViewHolder> {

    ArrayList<String> mData;
    ArrayList<String> mData1;
    ArrayList<String> mData2;
    ArrayList<String> mData3;
    ArrayList<String> mData4;
    Context context;
    private int mYear, mMonth, mDay, mHour, mMinute;


    // data is passed into the constructor
    public TimeManagement_adapter(ArrayList<String> data, ArrayList<String> data1, ArrayList<String> data2, ArrayList<String> data3, ArrayList<String> data4, Context context) {
        this.mData = data;
        this.mData1 = data1;
        this.mData2 = data2;
        this.mData3 = data3;
        this.mData4 = data4;
        this.context = context;

    }

    // inflates the row layout from xml when needed
    @Override
    public TimeManagement_adapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timemanagement_adapter, parent, false);
        TimeManagement_adapter.PlanetViewHolder PlanetViewHolder = new TimeManagement_adapter.PlanetViewHolder(v);
        return PlanetViewHolder;
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(PlanetViewHolder holder, int position) {

        holder.text.setText(mData.get(position).toString());
        holder.text1.setText(mData1.get(position).toString());
        holder.text2.setText(mData2.get(position).toString());
        holder.text3.setText(mData3.get(position).toString());
        holder.text4.setText(mData4.get(position).toString());
        holder.edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Intent intent = new Intent(context,Login.class);
               // dialogAccessCreateDate();
             //   Toast.makeText(context, "Working", Toast.LENGTH_LONG).show();
                dialogExamCreateDate();
                /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.studynotes_dialog, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });*/
             //   dialogBuilder.show();
               // dialogBuilder.setCancelable(false);



               /* StudyNotes_Dialog cdd = new StudyNotes_Dialog(context);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();*/
            }

        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class PlanetViewHolder extends RecyclerView.ViewHolder {

         TextView text,text1,text2,text3,text4,text5;
         ImageView edit3;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.day);
            text1= (TextView) itemView.findViewById(R.id.subject);
            text2= (TextView) itemView.findViewById(R.id.str);
            text3= (TextView) itemView.findViewById(R.id.end1);
            text4= (TextView) itemView.findViewById(R.id.bell);
            edit3= itemView.findViewById(R.id.edit3);
        }
    }

    EditText day, start,end,notify3,notify4;

    private void dialogExamCreateDate(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.timemanagement_dialog, null);

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

                new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay).show();


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

        /*bell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timePicker2();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(context, datePickerListener1, mYear, mMonth, mDay).show();

            }
        });
*/

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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

//-----------------------------------------------------------------------------------------------------------
    /*private void dialogAccessCreateDate() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.assessment_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);


        examdate = dialogView.findViewById(R.id.examdate);
        examtime = dialogView.findViewById(R.id.examtime);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);


        ImageButton calender1 = dialogView.findViewById(R.id.calendar);
        ImageButton clock1 = dialogView.findViewById(R.id.clock);
        ImageButton bell3 = dialogView.findViewById(R.id.bell2);


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

                timePicker();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay).show();

            }
        });


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

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
    }*/
//-------------------------------------------------------------------------------------------------------------
   /* EditText day, start, end, notify3, notify4;

    private void dialogTimeCreateDate() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.timemanagement_dialog,null);
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
                new DatePickerDialog(context, datePickerListener, mYear, mMonth, mDay).show();
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
   *//* private DatePickerDialog.OnDateSetListener datePickerListener3 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {

                    notify4.setText(selectedDay + " / " + (selectedMonth + 1) + " / " + selectedYear);

                }
            };*//*


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
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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
    }*/

}
