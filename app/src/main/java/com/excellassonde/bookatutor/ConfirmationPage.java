package com.excellassonde.bookatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ConfirmationPage extends AppCompatActivity {
    String confirmMessage = "Thanks. Your request has been received. A confirmation email will be sent once %s confirms this session";
    public static String tutorName;
    String totalConfirmMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        //format the confirmation message with the tutor's name
        totalConfirmMessage = String.format(confirmMessage, tutorName);
        //set the message
        TextView confirm = (TextView)findViewById(R.id.confirm_message);
        confirm.setText(totalConfirmMessage);

    }

    public static void setTutorName(String name){
        tutorName = name;
    }
}
