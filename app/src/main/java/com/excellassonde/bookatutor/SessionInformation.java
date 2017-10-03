package com.excellassonde.bookatutor;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by praiseayorinde on 2017-09-23.
 */

public class SessionInformation {
    int id, sessionScore;
    String tutorName, course;
    Date sessionDate;
    String startTime, endTime;
    SessionType sessionType;
    List<String> studentEmails = new ArrayList<>();

    public enum SessionType{ ONE_ON_ONE, GROUP; }

    public void setId(int id){ this.id = id; }

    public int getId(){
        return id;
    }

    public void setSessionScore(int score){
        this.sessionScore = score;
    }

    public int getSessionScore(){ return sessionScore; }

    public void setTutorName(String name){
        this.tutorName = name;
    }

    public String getTutorName(){
        return tutorName;
    }

    public void setCourse (String course){
        this.course = course;
    }

    public String getCourse(){
        return course;
    }

    public void setSessionDate(Date date){
        this.sessionDate = date;
    }

    public String getSessionDate(){
        return sessionDate.toString();
    }

    public void setStartTime(String time){
        this.startTime = time;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setEndTime(String time){
        this.endTime = time;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setSessionType(SessionType type){
        this.sessionType = type;
    }

    public String getSessionType(){
        return this.sessionType.name();
    }

    public void addToStudentEmails(String email){ this.studentEmails.add(email); }

    public void addToStudentEmails(List emails){ this.studentEmails.addAll(emails); }

    public String getStudentEmails() { return TextUtils.join(", ", studentEmails); }
}


