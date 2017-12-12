package com.excellassonde.bookatutor;

import android.text.TextUtils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.format.BoldStyle;

/**
 * This is the format for storing  a session that has been requested by the student.
 * If the student is confirmed by the tutor, we can know and then send a reply to the student
 */

public class SessionInformation {
    private int id, sessionScore;
    private String tutorName, course;
    private String sessionDate;
    private String startTime, endTime;
    private SessionType sessionType;
    private List<String> studentEmails;
    private String studentName;
    private Map.Entry<Boolean, String> isSessionConfirmed;

    public SessionInformation() {
        this.id = 0;
        this.sessionScore = 0;
        this.tutorName = "";
        this.course = "";
        this.sessionDate = "";
        this.startTime = "";
        this.endTime = "";
        this.sessionType = SessionType.ONE_ON_ONE;
        this.studentEmails = new ArrayList<>();
        this.isSessionConfirmed = new AbstractMap.SimpleEntry<Boolean, String>(false, "");
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionScore(){ return sessionScore; }

    public void setSessionScore(int score) {
        this.sessionScore = score;
    }

    public String getTutorName(){
        return tutorName;
    }

    public void setTutorName(String name) {
        this.tutorName = name;
    }

    public String getCourse(){
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSessionDate(){
        return sessionDate.toString();
    }

    public void setSessionDate(String date) {
        this.sessionDate = date;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String time) {
        this.startTime = time;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setEndTime(String time) {
        this.endTime = time;
    }

    public String getSessionType(){
        return this.sessionType.name();
    }

    public void setSessionType(SessionType type) {
        this.sessionType = type;
    }

    public void addToStudentEmails(String email) {
        this.studentEmails.add(email);
    }

    public void addToStudentEmails(List emails) {
        this.studentEmails.addAll(emails);
    }

    public String getStudentEmails() { return TextUtils.join(", ", studentEmails); }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String name) {
        this.studentName = name;
    }

    //the student just has to know if the session has been confirmed or not, they do not have to know the reason
    public boolean isSessionConfirmed() {
        return this.isSessionConfirmed.getKey();
    }

    //this is to keep the isConfirmed and the reason
    //if the session is rejected, then there will be a reason why
    public void setSessionConfirmed(Map.Entry<Boolean, String> confirmed) {
        this.isSessionConfirmed = confirmed;
    }

    public enum SessionType {ONE_ON_ONE, GROUP}

}


