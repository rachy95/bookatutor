package com.excellassonde.bookatutor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v4.app.Fragment;
import android.support.v4.widget.Space;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DaysTab extends Fragment {
    //namespaces
    private  final String ns = null;

    //course chosen by the user
    private String courseSelected = "";

    //keeps the name of the tutor the user picks
    public static String tutorName;

    //Define the tutor class
    public static class Tutor {
        public final String name;
        public final List courses;
        public final Map<String, List> availability;

        /***
         * create a tutor with a name, a list of courses they teach and their availabilities
         * @param aName the full name of a tutor
         * @param listCourses A list of courses the tutor is willing to teach
         * @param avails the times and days a tutor is available
         */
        private Tutor(String aName, List listCourses, Map avails){
            this.name = aName;
            this.courses = listCourses;
            this.availability = avails;

        }

        /***
         *
         * @return a set of days this tutor is available
         */
        public Set<String> getDaysAvailable(){
            return availability.keySet();
        }

        /***
         *
         * @param day the day this tutor might be available
         * @return the times, given a particular day a tutor teaches, if the tutor is not available on the given day,
         * an empty list is returned
         */
        public List<String> getTimes(String day){
            if(getDaysAvailable().contains(day)) {
                return availability.get(day);
            }
            else{
                return new ArrayList<String>();
            }
        }

        /***
         *
         * @param course the course a tutor might teach
         * @return if this tutor teaches a particular course called 'course'
         */
        public boolean teachCourse(String course){
            return courses.contains(course);
        }
    }

    /***
     * Tutors list that returns a list of tutors
     * @return list of tutors in the xml file
     */
    private  List<Tutor> getTutorsList(){
        //get the list of tutors
        List<Tutor> tutorsList = null;
        try {
            tutorsList = readTutors();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tutorsList;
    }

    /***
     * parse the xml file, and call the respective methods to read different entries
     * @return list of tutors
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List readTutors() throws XmlPullParserException, IOException{
        //list of tutors to return
        List tutors = new ArrayList();
        XmlResourceParser parser = getResources().getXml(R.xml.tutors);
        parser.next();
        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, ns, "Tutors");
        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            // Start by looking for the tutor tag
            if(name.equals("Tutor")){
                tutors.add(readTutor(parser));
            }
        }

        return tutors;
    }

    //private method to parse the contents in a tutor tag, then calls the respective methods to parse the nested tags
    private Tutor readTutor(XmlPullParser parser) throws XmlPullParserException, IOException{
        parser.require(XmlPullParser.START_TAG, ns, "Tutor");
        String name = null;
        List courses = null;
        Map availability = null;

        while (parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String tag = parser.getName();
            if (tag.equals("Name")){
                name = readName(parser);
            }
            else if (tag.equals("Course")){
                courses = readCourse(parser);
            }
            else if (tag.equals("Availability")){
                availability = readAvailability(parser);
            }
        }
        return new Tutor(name, courses, availability);
    }

    //private method to parse the names of tutors
    private  String readName(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "Name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Name");
        return name;
    }

    //private method that returns a list a tutor can teach
    private  List readCourse(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "Course");
        String course = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Course");

        List<String> courses = Arrays.asList(course.split(", "));
        return courses;
    }

    //process the availability of the tutor, it returns the day as a key
    //and the times are the values mapped to that day
    private  Map<String, List> readAvailability(XmlPullParser parser) throws IOException, XmlPullParserException{
        Map<String, List> avails = new HashMap<>();
        String day = null;
        List times = null;

        parser.require(XmlPullParser.START_TAG, ns, "Availability");

        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String tag = parser.getName();
            if(tag.equals("Day")){
                day = readDay(parser);
                while(parser.next() != XmlPullParser.END_TAG){
                    if(parser.getEventType() != XmlPullParser.START_TAG){
                        continue;
                    }
                    tag = parser.getName();
                    if(tag.equals("Time")){
                        times = readTimes(parser);
                    }
                }
            }
            parser.require(XmlPullParser.END_TAG, ns, "Day");
            avails.put(day, times);
        }

        parser.require(XmlPullParser.END_TAG, ns, "Availability");
        return avails;
    }

    //process the day the tutor is free
    private  String readDay(XmlPullParser parser) throws IOException, XmlPullParserException{
        String day = "";
        parser.require(XmlPullParser.START_TAG, ns, "Day");
        day = parser.getAttributeValue(0);
        return day;
    }

    //process the times the tutor is free
    private  List<String> readTimes(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "Time");
        String time = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Time");

        List<String> times = Arrays.asList(time.split(", "));
        return times;
    }

    // For the tags name, and courses, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /***
     *
     * @param day - the day a tutor is wanted
     * @return if there are tutors available on a particular day
     */
    protected boolean AreTutorsAvailableThatDay(String day){
        List<Tutor> tutorsList = getTutorsList();
        Set<String> daysAvailable = new HashSet<>();
        boolean res = false;
        for(int i = 0; i < tutorsList.size(); i++) {
            daysAvailable.addAll (tutorsList.get(i).getDaysAvailable());
            for(String d: daysAvailable){
                if(d.equals(day)){
                    res =  true;
                }
            }
        }
        return res;
    }

    /***
     *
     * @param day a day tutoring is wanted
     * @param course a course being offered
     * @return the list of tutors that are available for day `day' and that teach course `course'
     */
    protected  List<Tutor> getTutors(String day, String course) {
        List<Tutor> tutorsList = getTutorsList();
        List<Tutor> result = new ArrayList<>();
        for (int i = 0; i < tutorsList.size(); i++) {
            Set<String> daysAvailable = tutorsList.get(i).getDaysAvailable();
            if(daysAvailable.contains(day) && tutorsList.get(i).teachCourse(course)){
                result.add(tutorsList.get(i));
            }
        }

        return result;
    }

    //set the page view for the different tabs. This method is called by MonTab to FriTab
    protected void setDisplay(String day, View view, LinearLayout layout){
        //get the course chosen from the choose course activity
        Intent intent = getActivity().getIntent();
        courseSelected = intent.getStringExtra(ChooseCourseActivity.courseSelected);

        String display = "";

        //text to display a string if no tutor is available
        TextView displayTextView = new TextView(view.getContext());

        //if there are tutors available, we want to be able to click on the tutors
        List<Button> displayTutors = new ArrayList<>();

        //if tutors are not even available on that day then don't check the list.
        //Just display we do not have tutors on that day
        if (AreTutorsAvailableThatDay(day)) {
            final List<Tutor> tutors = getTutors(day, courseSelected);
            if(tutors.isEmpty()){
                //there are tutors available on that day, but none that teach the course selected
                display = "Sorry, there are no tutors available for " + courseSelected + " on " + day;
                //set the text view, then add to layout
                displayTextView.setTextSize(20);
                displayTextView.setText(display);
                layout.addView(displayTextView);
            }
            else{
                //this means this tutor is available and teach this course
                for (int i = 0; i < tutors.size(); i++) {
                    //get the times for that tutor
                    final List<String> times =  tutors.get(i).getTimes(day);
                    for(int j = 0; j < times.size(); j++){
                        final String name = tutors.get(i).name;
                        display = name + ": " + times.get(j);
                        //add the tutor and times to a button
                        Button button = new Button(view.getContext());
                        button.setText(display);
                        //set the color and the way it looks
                        button.setTextColor(getResources().getColor(R.color.colorPrimary));
                        button.setBackgroundResource(R.drawable.rectangle);
                        //set space between the buttons
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(60, 20, 60, 0);
                        button.setLayoutParams(params);
                        button.setPadding(20,20,20,20);
                        //integers needed for getting objects in a loop
                        final int finalI = i;
                        final int finalJ = j;
                        button.setOnClickListener(new View.OnClickListener() {
                                                      public void onClick(View v) {
                                                          Intent intent1=new Intent(getActivity(), SessionReview.class);
                                                          startActivity(intent1);
                                                      }
                                                  });
                        displayTutors.add(button);
                    }
                }
            }

        } else {
            display = "Sorry, there are no tutors available on " + day;
            displayTextView.setText(display);
            displayTextView.setTextSize(20);
            displayTextView.setPadding(30, 0, 0, 0);
            layout.addView(displayTextView);
        }
        //get the buttons and add them to the layout
        for(int i = 0; i< displayTutors.size(); i++){
            layout.addView(displayTutors.get(i));
        }
    }

    //private method to set the view of the alert that pops when a button is clicked
    private View setAlertView(String tutorName, String time){
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View alertView = factory.inflate(R.layout.alert_view, null);
        //set the tutor's name
        EditText tutor = (EditText) alertView.findViewById(R.id.tutor_name_edit);
        tutor.setText(tutorName);
        tutor.setEnabled(false);
        //set the course
        EditText course = (EditText) alertView.findViewById(R.id.course_name_edit);
        course.setText(courseSelected);
        course.setEnabled(false);
        //set the start and end times
        //get the times first
        List times = getTime(time, alertView);
        String startTime = times.get(0).toString();
        String endTime = times.get(1).toString();
        EditText start = (EditText) alertView.findViewById(R.id.start_session_edit);
        start.setText(startTime, TextView.BufferType.EDITABLE);
        EditText end = (EditText) alertView.findViewById(R.id.end_session_edit);
        end.setText(endTime, TextView.BufferType.EDITABLE);

        return alertView;
    }

    //get the time a tutor is free broken down, and return the start time and end time in a list.
    //A list can only contain two items
    private List<Integer> getTime(String time, View view) {
        //set the spinners value
        Spinner startSpinner = (Spinner) view.findViewById(R.id.start_session_dropdown_time);
        Spinner endSpinner = (Spinner) view.findViewById(R.id.end_session_dropdown_time);
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
