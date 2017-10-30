package com.excellassonde.bookatutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessionReview extends AppCompatActivity {
    Spinner spinner;
    public static String sessionType, studentEmail, studentName;
    public static String tutorName, sessionTime, startTime, endTime, course, otherEmails;

    public static void setTime(String time) {
        sessionTime = time;
    }

    public static void setTutorName(String name) {
        tutorName = name;
    }

    public static void setCourse(String aCourse) {
        course = aCourse;
    }

    public static void setOtherEmails(String emails){ otherEmails = emails;}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_session_review);

        EditText tutor = (EditText) findViewById(R.id.tutor_name_edit);
        tutor.setText(tutorName);
        tutor.setEnabled(false);

        //set the course
        EditText courseBox = (EditText) findViewById(R.id.course_name_edit);
        courseBox.setText(course);
        courseBox.setEnabled(false);

        //set the start and end times
        //get the times first
        List times = getTime(sessionTime);
        startTime = times.get(0).toString();
        endTime = times.get(1).toString();
        EditText start = (EditText) findViewById(R.id.start_session_edit);
        start.setText(startTime, TextView.BufferType.EDITABLE);
        EditText end = (EditText) findViewById(R.id.end_session_edit);
        end.setText(endTime, TextView.BufferType.EDITABLE);
    }

    public void goToConfirmation(View view) {
        spinner = (Spinner) findViewById(R.id.session_type_dropdown);
        final Intent intent = new Intent(this, ConfirmationPage.class);
        sessionType = String.valueOf(spinner.getSelectedItem());

        if(sessionType.equals("Group")){
            //if session is a group, then tell the student to add the remaining emails
            //inflate the view for the group email
            LayoutInflater factory = LayoutInflater.from(this);
            final View groupSessionEmailView = factory.inflate(R.layout.group_session_email, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(groupSessionEmailView)
                    .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText emailBox = (EditText) groupSessionEmailView.findViewById(R.id.other_emails);
                            String emails = emailBox.getText().toString();
                            setOtherEmails(emails);
                            startActivity(intent);                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            startActivity(intent);
        }
    }

    //get the time a tutor is free broken down, and return the start time and end time in a list.
    //A list can only contain two items
    private List<Integer> getTime(String time) {
        //set the spinners value
        Spinner startSpinner = (Spinner) findViewById(R.id.start_session_dropdown_time);
        Spinner endSpinner = (Spinner) findViewById(R.id.end_session_dropdown_time);
        //split the time e.g time is 5pm-7pm.
        List<String> times = Arrays.asList(time.split("-"));
        String startTime = times.get(0);
        String endTime = times.get(1);
        //get the end of the time, if it is AM or PM
        String start = startTime.substring(startTime.length() - 2, startTime.length());
        String end = endTime.substring(endTime.length() - 2, endTime.length());
        //0 is AM and 1 is PM
        if (start.equals("am") && end.equals("am")) {
            startSpinner.setSelection(0);
            startSpinner.setEnabled(false);
            endSpinner.setSelection(0);
            endSpinner.setEnabled(false);
        }
        else if (start.equals("am") && end.equals("pm")) {
            startSpinner.setSelection(0);
            endSpinner.setSelection(1);
        }
        else {
            startSpinner.setSelection(1);
            startSpinner.setEnabled(false);
            endSpinner.setSelection(1);
            endSpinner.setEnabled(false);
        }
        //return only the numbers from the time
        List<Integer> timeIntegers = new ArrayList();
        //replace all letters with spaces so you can get only integers
        timeIntegers.add(Integer.parseInt(startTime.replaceAll("[\\D]", "")));
        timeIntegers.add(Integer.parseInt(endTime.replaceAll("[\\D]", "")));
        return timeIntegers;
    }


}
