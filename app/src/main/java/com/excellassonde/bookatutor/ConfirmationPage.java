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
    String confirmMessage = "Your request has been received! A confirmation email will be sent once %s confirms this session";
    public static String tutorName;
    public static boolean tutorReachedAvailableHours;
    String totalConfirmMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmation_page);
        showNotification();
        //format the confirmation message with the tutor's name
        totalConfirmMessage = String.format(confirmMessage, tutorName);
        //set the message
        TextView confirm = (TextView)findViewById(R.id.confirm_message);
        confirm.setText(totalConfirmMessage);
        storeSessionInDatabase();

    }

    private void storeSessionInDatabase(){
        //Our access to the database
        final StudentInfoDatabaseHelper helper = new StudentInfoDatabaseHelper(this);
        final  SessionInfoDatabaseHelper shelper = new SessionInfoDatabaseHelper(this);

        //store this session
        SessionInformation session = new SessionInformation();
        session.setTutorName(tutorName);
        session.setCourse(SessionReview.course);
        session.setStartTime(SessionReview.startTime);
        session.setEndTime(SessionReview.endTime);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        session.setSessionDate(date);
        if(SessionReview.sessionType.equals("Group")){
            session.setSessionType(SessionInformation.SessionType.GROUP);
            session.addToStudentEmails(SessionReview.studentEmail);
            session.addToStudentEmails(SessionReview.otherEmails);
        }
        else{
            session.setSessionType(SessionInformation.SessionType.ONE_ON_ONE);
            session.addToStudentEmails(SessionReview.studentEmail);
        }
        shelper.insertSession(session);
        //store the information in our database
        StudentInformation studentInformation = new StudentInformation();
        studentInformation.setStudentName(SessionReview.studentName);
        studentInformation.setStudentEmail(SessionReview.studentEmail);
        studentInformation.addToSessions(session.getId());
        //the session has to be set too
        helper.insertStudent(studentInformation);
    }

    private void showNotification(){
        if(true){
            //if the tutor has reached the maximum hours they are willing to tutor, then
            //display a warning message
            LayoutInflater factory = LayoutInflater.from(this);
            final View notificationView = factory.inflate(R.layout.available_hours_notification, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(notificationView)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public static void setReachedAvailableHours(boolean reached) {tutorReachedAvailableHours = reached;}


}
