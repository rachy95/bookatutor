package com.excellassonde.bookatutor;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ConfirmationPage extends AppCompatActivity {
    String confirmMessage = "Your request has been received! A confirmation message will be sent once %s confirms this session";
    String totalConfirmMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmation_page);
        //format the confirmation message with the tutor's name
        totalConfirmMessage = String.format(confirmMessage, SessionReview.tutorName);
        //set the message
        TextView confirm = (TextView)findViewById(R.id.confirm_message);
        confirm.setText(totalConfirmMessage);
        //store the sessions class
    }

//    private void storeSessionInDatabase(){
//        //Our access to the database
//        final StudentInfoDatabaseHelper helper = new StudentInfoDatabaseHelper(this);
//        final  SessionInfoDatabaseHelper shelper = new SessionInfoDatabaseHelper(this);
//
//        //store this session
//        SessionInformation session = new SessionInformation();
//        session.setTutorName(SessionReview.tutorName);
//        session.setCourse(SessionReview.course);
//        session.setStartTime(SessionReview.startTime);
//        session.setEndTime(SessionReview.endTime);
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        session.setSessionDate(date);
//        if(SessionReview.sessionType.equals("Group")){
//            session.setSessionType(SessionInformation.SessionType.GROUP);
//            session.addToStudentEmails(SessionReview.studentEmail);
//            session.addToStudentEmails(SessionReview.otherEmails);
//        }
//        else{
//            session.setSessionType(SessionInformation.SessionType.ONE_ON_ONE);
//            session.addToStudentEmails(SessionReview.studentEmail);
//        }
//        shelper.insertSession(session);
//        //store the information in our database
//        StudentInformation studentInformation = new StudentInformation();
//        studentInformation.setStudentName(SessionReview.studentName);
//        studentInformation.setStudentEmail(SessionReview.studentEmail);
//        studentInformation.addToSessions(session.getId());
//        //the session has to be set too
//        helper.insertStudent(studentInformation);
//    }
}
