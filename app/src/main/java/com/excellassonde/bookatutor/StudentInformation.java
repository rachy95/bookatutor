package com.excellassonde.bookatutor;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praiseayorinde on 2017-09-23.
 */

//will be used as the database to store the information of the student and the sessions that has been booked
public class StudentInformation {
    int id;
    List<Integer> sessionIDs = new ArrayList<>();
    String studentName, studentEmail;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setStudentName(String name){
        this.studentName = name;
    }

    public String getStudentName(){
        return this.studentName;
    }

    public void setStudentEmail(String email){
        this.studentEmail = email;
    }

    public String getStudentEmail(){
        return this.studentEmail;
    }

    public void addToSessions(int id){
        sessionIDs.add(id);
    }

    public int getNumberOfSessions(){
        return this.sessionIDs.size();
    }

    public String getSessionIDs() {
       //returns the session IDs that have been booked by the student
        return TextUtils.join(", ", sessionIDs);
    }
}
