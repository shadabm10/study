package com.studinotes.AdapterClass;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ikovac.timepickerwithseconds.MyTimePickerDialog;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.studinotes.Constant.AppConfig;
import com.studinotes.Fragments.TimeManagement;
import com.studinotes.Utils.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TimeManagement_adapter extends RecyclerView.Adapter<TimeManagement_adapter.PlanetViewHolder> {
    String TAG="Time";

    Context context;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;
    ArrayList<HashMap<String,String>> tipsntricks;
    String notify_before="";
    Calendar myCalendar = Calendar.getInstance();
    String notify_me="";
    ProgressDialog pd;
    TimeManagement timeManagement;
    String schedule_id;
    String message,end_time,random_new,exam_time,exam_date,social_new,value_notify,notify_me_new;
    String str_assess_name,id,str_asses_subject_name,str_asses_date,str_asses_time,str_asses_details,str_asses_notify;
    String  randonYes="N";
    String  Social="N";
    // data is passed into the constructor
    public TimeManagement_adapter(Context context, ArrayList<HashMap<String, String>> tipsntricks, ProgressDialog pd,TimeManagement timeManagement) {

        this.context = context;
        this.tipsntricks=tipsntricks;
        this.pd=pd;
        this.timeManagement=timeManagement;

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
        id=tipsntricks.get(position).get("id");
        String social=tipsntricks.get(position).get("social");
        String random=tipsntricks.get(position).get("random");

        if(social.equals("Y")){
            holder.end_time.setText("Yes");

        }else {
            holder.end_time.setText("No");

        }
        if (random.equals("Y")){
            holder.social.setText("Random");
        }
        else {
            holder.social.setText("Important");
        }
        holder.day.setText(tipsntricks.get(position).get("fullname"));

        holder.date_day.setText(tipsntricks.get(position).get("day"));
        holder.subject.setText(tipsntricks.get(position).get("subject_name"));
        holder.start_time.setText(tipsntricks.get(position).get("start_time")+" "+"to");
        holder.end_time_new.setText(tipsntricks.get(position).get("end_time"));


        holder.edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_assess_name=tipsntricks.get(position).get("day");
                str_asses_subject_name=tipsntricks.get(position).get("subject_name");
                str_asses_date=tipsntricks.get(position).get("start_time");
                str_asses_time=tipsntricks.get(position).get("end_time");
                notify_me_new=tipsntricks.get(position).get("notify_me");
                str_asses_notify=tipsntricks.get(position).get("notify_before");
                random_new=tipsntricks.get(position).get("random");
                social_new=tipsntricks.get(position).get("social");
                schedule_id=tipsntricks.get(position).get("id");
                Log.d(TAG, "onClick: "+schedule_id);
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
        return tipsntricks.size();
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder {

         TextView day,subject,start_time,end_time,social,date_day,end_time_new;
         ImageView edit3;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            day= itemView.findViewById(R.id.days);
            subject= itemView.findViewById(R.id.tv_subject);
            start_time= itemView.findViewById(R.id.tv_time);
            end_time= itemView.findViewById(R.id.tv_social);
            social= itemView.findViewById(R.id.tv_random);
            edit3= itemView.findViewById(R.id.tv_edit);
            date_day= itemView.findViewById(R.id.date);
            end_time_new= itemView.findViewById(R.id.tv_time_to);
            itemView.setOnLongClickListener(v -> {
                //  Toast.makeText(v.getContext(), "Position is " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);

                // set title

                // set dialog message
                alertDialogBuilder
                        .setMessage("Want to delete the Examination")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                    String abc=tipsntricks.get(getAdapterPosition()).get("id");
                                Log.d(TAG, "onClick: "+abc);
                                DeleteFolder(tipsntricks.get(getAdapterPosition()).get("id"));
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                return false;
            });

        }
    }

    EditText day, start,end,notify3,notify4,subjectname;

    private void dialogExamCreateDate(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.timemanagement_dialog, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        day = dialogView.findViewById(R.id.day);
        subjectname = dialogView.findViewById(R.id.subjectname);
        start = dialogView.findViewById(R.id.start);
        end = dialogView.findViewById(R.id.end);
        notify3 = dialogView.findViewById(R.id.notify3);
        notify4 = dialogView.findViewById(R.id.notify4);
        RadioGroup rg = dialogView.findViewById(R.id.radio1);
        day.setText(str_assess_name);
        subjectname.setText(str_asses_subject_name);
        start.setText(str_asses_date);
        end.setText(str_asses_time);

        RadioGroup radio_group_notify = dialogView.findViewById(R.id.radio_group_notify);


        ImageButton calender2 = dialogView.findViewById(R.id.calendar2);
        ImageButton clock2 = dialogView.findViewById(R.id.clock2);
        ImageButton clock3 = dialogView.findViewById(R.id.clock3);
        Switch onOffSwitch = dialogView. findViewById(R.id.switch2);
        Switch switch_notify = dialogView. findViewById(R.id.switch3);
        if(social_new.equals("Y")){
            onOffSwitch.setChecked(true);
        }
        else {
            onOffSwitch.setChecked(false);
        }
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked==true){
                    Social="Y";
                    Log.d(TAG, "onCheckedChanged: "+Social);
                }
                else {
                    Social="N";
                    Log.d(TAG, "onCheckedChangedN: "+Social);

                }
            }

        });
        switch_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked==true){
                    notify_me="Y";
                    radio_group_notify.setVisibility(View.VISIBLE);
                    radio_group_notify.setOnCheckedChangeListener((group, checkedId) -> {
                        RadioButton radioButton = dialogView. findViewById(checkedId);
                        // Toast.makeText(getActivity(),radioButton.getText(), Toast.LENGTH_SHORT).show();
                        notify_before= (String) radioButton.getText();
                        Log.d(TAG, "notify_before: "+notify_before);
                        if(notify_before.equals("5 mins")){
                            value_notify="5";
                        }
                        else if(notify_before.equals("10 mins")){
                            value_notify="10";
                        }
                        else if(notify_before.equals("20 mins")){
                            value_notify="20";
                        }


                    });

                }
                else {
                    notify_me="N";
                    radio_group_notify.setVisibility(View.GONE);

                }
            }

        });

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = dialogView. findViewById(checkedId);
            String buttonValue= (String) radioButton.getText();
            if (buttonValue.equals("Random")){
                randonYes="Y";
                Log.d(TAG, "dialogExamCreateDate: "+randonYes);

            }
            else {
                randonYes="N";
                Log.d(TAG, "dialogExamCreateDate: "+randonYes);
            }

        });

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



        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Button btn_yes = dialogView.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String day_name=day.getText().toString().trim();
                String str_subject= subjectname.getText().toString().trim();
                String exam_date= start.getText().toString().trim();
                String end_date= end.getText().toString().trim();


                if(!day.getText().toString().isEmpty()){
                    if(!subjectname.getText().toString().isEmpty()){
                        if(!start.getText().toString().isEmpty()){
                            if(!end.getText().toString().isEmpty()){
                                EditSchedule(day_name,str_subject,exam_date,end_date);
                                alertDialog.dismiss();

                            }

                            else {
                                Toast.makeText(context,"Enter Exam Time", Toast.LENGTH_LONG).show();

                            }
                        }
                        else {
                            Toast.makeText(context,"Enter Exam Date", Toast.LENGTH_LONG).show();

                        }

                    }
                    else {
                        Toast.makeText(context,"Enter Subject", Toast.LENGTH_LONG).show();

                    }

                }
                else {
                    Toast.makeText(context,"Enter Name", Toast.LENGTH_LONG).show();



                }




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


    private void EditSchedule(String day_name, String str_subject, String exam_date, String end_date)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"editSchedule", response -> {
            Log.d(TAG, "JOB RESPONSE: " + response.toString());

            pd.dismiss();
            //  productlist.clear();

            Gson gson = new Gson();

            try {


                JsonObject jobj = gson.fromJson(response, JsonObject.class);
                String result = jobj.get("status").toString().replaceAll("\"", "");
                String message = jobj.get("message").toString().replaceAll("\"", "");


                if (result.equals("1")) {

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    timeManagement.ScheduleList();


                } else
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "issue", Toast.LENGTH_LONG).show();

            }


        }, error -> {
            Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
            Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show();
            pd.dismiss();
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("scheduleId",schedule_id);
                params.put("day",day_name);
                params.put("start_time",exam_date);
                params.put("start_time",exam_date);
                params.put("end_time",end_date);
                params.put("subject_name",str_subject);
                params.put("notify_me",notify_me);
                params.put("random",randonYes);
                params.put("social",Social);
                params.put("notify_before",notify_before);


                Log.d(TAG, "getParams: "+params);
                return params;
            }





        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }


    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    myCalendar.set(Calendar.YEAR, selectedYear);
                    myCalendar.set(Calendar.MONTH, selectedMonth);
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                    String myFormat = "MMM dd, yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    exam_date = sdf1.format(myCalendar.getTime());
                    String date_to_show = sdf.format(myCalendar.getTime());
                    day.setText(exam_date);

                }
            };

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        /*MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            notify6.setText(time_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                start.setText( ""+selectedHour + ":" + selectedMinute);
            }
        }, mHour, mMinute,true);

        mTimePicker.show();
    }


   /* private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond=c.get(Calendar.SECOND);


        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            exam_time= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            start.setText(exam_time);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }*/
   private void timePicker1(){
       // Get Current Time
       final Calendar c = Calendar.getInstance();
       mHour = c.get(Calendar.HOUR_OF_DAY);
       mMinute = c.get(Calendar.MINUTE);

       // Launch Time Picker Dialog
        /*MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            time_notify= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            notify6.setText(time_notify);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();*/
       TimePickerDialog mTimePicker;
       mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

           @Override
           public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               end.setText( ""+selectedHour + ":" + selectedMinute);
           }
       }, mHour, mMinute,true);

       mTimePicker.show();
   }

/*
    private void timePicker1(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSecond=c.get(Calendar.SECOND);


        // Launch Time Picker Dialog
        MyTimePickerDialog mTimePicker = new MyTimePickerDialog(context, (MyTimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute, seconds) -> {
            // TODO Auto-generated method stub
            end_time= String.format("%02d", hourOfDay)+
                    ":" + String.format("%02d", minute) +
                    ":" + String.format("%02d", seconds);
            end.setText(end_time);
        }, mHour, mMinute, mSecond, true);
        mTimePicker.show();
    }
*/

    private void DeleteFolder(String id) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pd.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DEV+"deleteSchedule", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                pd.dismiss();

                Gson gson = new Gson();

                try
                {


                    JsonObject jobj = gson.fromJson(response, JsonObject.class);
                    String status = jobj.get("status").getAsString().replaceAll("\"", "");
                    String message = jobj.get("message").getAsString().replaceAll("\"", "");

                    Log.d(TAG, "Message: "+message);

                    if (status.equals("1")) {




                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        tipsntricks.clear();
                        timeManagement.ScheduleList();


                        pd.dismiss();
                    }
                    else {

                        FancyToast.makeText(context, message, FancyToast.LENGTH_LONG, FancyToast.WARNING, false).show();
                    }


                    Log.d(TAG,"Token \n" +message);



                }catch (Exception e) {

                    Toast.makeText(context,"Incorrect Client ID/Password", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DATA NOT FOUND: " + error.getMessage());
                Toast.makeText(context,
                        "Connection Error", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();

                params.put("scheduleId", id);
                //  params.put("type", type);

                Log.d(TAG, "getParams: "+params);



                return params;
            }

        };

        // Adding request to request queue
        GlobalClass.getInstance().addToRequestQueue(strReq, tag_string_req);
        strReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 10, 1.0f));

    }



}
