package com.excellassonde.bookatutor;

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by praiseayorinde on 2017-09-30.
 */

public class TutorInformation {
    int id;
    float maxHours, hoursBooked;
    String tutorName, tutorEmail;
    Map<String, List<String>> availabilities;
    List<String> courses;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setTutorName(String name){
        this.tutorName = name;
    }

    public String getTutorName(){
        return this.tutorName;
    }

    public void setTutorEmail(String email){
        this.tutorEmail = email;
    }

    public String getTutorEmail(){
        return this.getTutorEmail();
    }

    public void setCourses(String courses){
        String[] array = courses.split(",");
        this.courses = Arrays.asList(array);
    }

    public void addToCourses(String course){
        this.courses.add(course);
    }

    public String getCourses(){
        return TextUtils.join(", ", this.courses);
    }

    public void setMaxHours(float hours){
        this.maxHours = hours;
    }

    public float getMaxHours(){
        return this.maxHours;
    }

    public void addToHoursBooked(float hours){
        this.hoursBooked += hours;
    }

    public float getHoursBooked(){
        return this.hoursBooked;
    }

    public void addToAvailabilities(String day, List<String> times){
        this.availabilities = new HashMap<>();
        this.availabilities.put(day, times);
    }

    public String getAvailabilties(){
        //return the availabilites in this format
        //Monday(8am-9am, 10am-5pm), Tuesday(4pm-5pm), e.t.c.
        String result = "";
        Set<Map.Entry<String, List<String>>> mapSet = this.availabilities.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = mapSet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<String>> entry = iterator.next();
            String day = entry.getKey();
            String times = TextUtils.join(", ", entry.getValue());
            result += day + "(" + times + ")";
        }
        return result;
    }
}
