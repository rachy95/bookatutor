package com.excellassonde.bookatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class SessionReview extends AppCompatActivity {
    Spinner spinner;
    public static String sessionTypeSelected;
    String sessionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_review);
    }

    public void goToConfirmation(View view) {
        spinner = (Spinner) findViewById(R.id.session_type_dropdown);
        Intent intent = new Intent(this, ChooseDayAndTutor.class);
        sessionType = String.valueOf(spinner.getSelectedItem());
        if(sessionType.equals(SessionInformation.SessionType.GROUP)){
         //if session is a group, then tell the student to add the remaining emails
        }
        intent.putExtra(sessionTypeSelected, sessionType);
        startActivity(intent);
    }
}
