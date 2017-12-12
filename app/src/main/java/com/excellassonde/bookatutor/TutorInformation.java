package com.excellassonde.bookatutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by praiseayorinde on 2017-11-19.
 */
//Define the tutor class
public class TutorInformation {
    private String name;
    private double availableHours;
    private List<String> courses;
    private String phoneNumber;
    private Map<String, Map<String, Boolean>> availability;
    private double hoursBooked;
    private String email;
    private String tutorID;
    private boolean isTranscriptSubmitted;

    public TutorInformation() {
        this.name = "";
        this.availableHours = 0.0;
        this.courses = new ArrayList<>();
        this.phoneNumber = "";
        this.availability = new HashMap<>();
        this.hoursBooked = 0.0;
        this.email = "";
        isTranscriptSubmitted = false;
    }

    /***
     * create a tutor with a name, a list of courses they teach and their availabilities
     * @param aName the full name of a tutor
     * @param anEmail the email addy of a tutor
     * @param aPhoneNumber the phone number of the tutor
     * @param listCourses A list of courses the tutor is willing to teach
     * @param availableHours the hours a tutor is willing to tutor for3
     * @param avails the times and days a tutor is available.
     *               the day is the key, and a list is the times the tutor is free.
     *                       Each list is an entry with the time they are free with if it has been booked
     */
    public TutorInformation(String aName, String anEmail, String aPhoneNumber, List listCourses, double availableHours, Map<String, Map<String, Boolean>> avails) {
        this.name = aName;
        this.phoneNumber = aPhoneNumber;
        this.courses = listCourses;
        this.availability = avails;
        this.availableHours = availableHours;
        this.email = anEmail;
        this.hoursBooked = 0;
        this.isTranscriptSubmitted = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvailableHours() {
        return this.availableHours;
    }

    public void setAvailableHours(double availableHours) {
        this.availableHours = availableHours;
    }

    public List<String> getCourses() {
        return this.courses;
    }

    public void setCourses(List<String> courses) {
        this.courses.addAll(courses);
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Map<String, Boolean>> getAvailability() {
        return this.availability;
    }

    public void setAvailability(Map<String, Map<String, Boolean>> availability) {
        this.availability = availability;
    }

    public double getHoursBooked() {
        return this.hoursBooked;
    }

    public void setHoursBooked(double hoursBooked) {
        this.hoursBooked = hoursBooked;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTutorID() {
        return this.tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public boolean isTranscriptSubmitted() {
        return isTranscriptSubmitted;
    }

    public void setTranscriptSubmitted(boolean isTranscriptSubmitted) {
        this.isTranscriptSubmitted = isTranscriptSubmitted;
    }

    /***
     *
     * @return a set of days this tutor is available
     */
    public Set<String> getDaysAvailable() {
        return availability.keySet();
    }

    /***
     *
     * @param day the day this tutor might be available
     * @return the times, given a particular day a tutor teaches, if the tutor is not available on the given day,
     * an empty list is returned
     */
    public Map<String, Boolean> getTimes(String day) {

        if (getDaysAvailable().contains(day)) {
            return availability.get(day);
        } else {
            return new HashMap<>();
        }
    }

    /***
     *
     * @param course the course a tutor might teach
     * @return if this tutor teaches a particular course called 'course'
     */
    public boolean teachCourse(String course) {
        return courses.contains(course);
    }

    /***
     *
     * @param hours the amount of hours the tutor has been booked for
     */
    public void addToHoursBooked(double hours) {
        hoursBooked += hours;
    }

    //given a new availability, reset it to be this value
    public void resetAvailability(String day, Map<String, Boolean> times) {
        availability.put(day, times);
    }

    /***
     *
     * @return if the tutor has reached the available hours he is willing to tutor for
     */
    public boolean reachedMaximumHours() {
        return this.hoursBooked >= this.availableHours;
    }
}
