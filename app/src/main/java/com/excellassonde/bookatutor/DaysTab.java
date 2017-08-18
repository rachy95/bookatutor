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

    //Define the tutor class
    public static class Tutor {
        public final String name;
        public final List courses;
        public final Map<String, List> availability;


        private Tutor(String aName, List listCourses, Map avails){
            this.name = aName;
            this.courses = listCourses;
            this.availability = avails;

        }

        public Set<String> getDaysAvailable(){
            return availability.keySet();
        }
        public List<String> getTimes(String day){
            return availability.get(day);
        }
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

    //parse the contents of a tutor.
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

    //process the names of tutors
    private  String readName(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "Name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Name");
        return name;
    }

    //process the courses the tutor can teach
    private  List readCourse(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "Course");
        String course = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Course");

        List<String> courses = Arrays.asList(course.split(", "));
        return courses;
    }

    //process the availability of the tutor
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
     * @param day - the day when a tutor might be free
     * @return if there are tutors available on particular day
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
     * @param day the day you want to get information
     * @return the list of tutors that are available that day
     */
    protected  List<Tutor> getTutors(String day) {
        List<Tutor> tutorsList = getTutorsList();
        List<Tutor> result = new ArrayList<>();
        for (int i = 0; i < tutorsList.size(); i++) {
            Set<String> daysAvailable = tutorsList.get(i).getDaysAvailable();
            if(daysAvailable.contains(day)){
                result.add(tutorsList.get(i));
            }
        }
        return result;
    }

    /***
     *
     * @param day the day you want to get information
     * @param course the course a tutor teaches
     * @return the list of tutors that are available that day and that teach that course
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

    protected void setDisplay(String day, View view, LinearLayout layout){
        //get the course chosen from the choose course activity
        Intent intent = getActivity().getIntent();
        courseSelected = intent.getStringExtra(ChooseCourseActivity.courseSelected);

        String display = "";

        //text to display a string if no tutor is available
        TextView textView1 = new TextView(view.getContext());

        //if there are tutors available, we want to be able to click on the tutors
        List<Button> displayTutors = new ArrayList<>();

        //if tutors are not even available on that day then dont check the list. Just display we do not have tutors on that day
        if (AreTutorsAvailableThatDay(day)) {
            final List<Tutor> tutors = getTutors(day, courseSelected);
            if(tutors.isEmpty()){
                display = "Sorry, there are no tutors available for " + courseSelected + " on " + day;
                textView1.setText(display);
                layout.addView(textView1);
            }
            else{
                for (int i = 0; i < tutors.size(); i++) {
                    final List<String> times =  tutors.get(i).getTimes(day);
                    for(int j = 0; j < times.size(); j++){
                        display = tutors.get(i).name + ": " + times.get(j);
                        Button button = new Button(view.getContext());
                        button.setText(display);
                        button.setBackgroundResource(R.color.colorPrimary);
                        button.layout(10,10, 10, 10);
                        final int finalI = i;
                        final int finalJ = j;
                        button.setOnClickListener(new View.OnClickListener() {
                                                      public void onClick(View v) {
                                                          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                          builder.setView(setAlertView(tutors.get(finalI).name, times.get(finalJ)))
                                                                  .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                                      public void onClick(DialogInterface dialog, int id) {
                                                                          dialog.cancel();
                                                                      }
                                                                  })
                                                                  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                      public void onClick(DialogInterface dialog, int id) {
                                                                          dialog.cancel();
                                                                      }
                                                                  });
                                                          AlertDialog alert = builder.create();
                                                          alert.show();
                                                      }
                                                  });
                        displayTutors.add(button);
                    }
                }
            }

        } else {
            display = "Sorry, there are no tutors available on " + day;
            textView1.setText(display);
            layout.addView(textView1);
        }

        for(int i = 0; i< displayTutors.size(); i++){
            layout.addView(displayTutors.get(i));
        }
    }

    private String getStartTime(String time){
        List<String> times = Arrays.asList(time.split("-"));
        return times.get(0);
    }

    private String getEndTime(String time){
        List<String> times = Arrays.asList(time.split("-"));
        return times.get(1);
    }

    private View setAlertView(String tutorName, String time){
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View alertView = factory.inflate(R.layout.alert_view, null);
        //set the tutor's name
        EditText tutor = (EditText) alertView.findViewById(R.id.tutor_name_edit);
        tutor.setText(tutorName);
        tutor.setEnabled(false);
        //set the course
        EditText course = (EditText) alertView.findViewById(R.id.course_name_edit);
        course.setText(courseSelected.toLowerCase());
        course.setEnabled(false);
        //set the start and end times
        //get the times first
        List times = setTime(time, alertView);
        String startTime = times.get(0).toString();
        String endTime = times.get(1).toString();
        EditText start = (EditText) alertView.findViewById(R.id.start_session_edit);
        start.setText(startTime, TextView.BufferType.EDITABLE);
        EditText end = (EditText) alertView.findViewById(R.id.end_session_edit);
        end.setText(endTime, TextView.BufferType.EDITABLE);

        return alertView;
    }

    private List<Integer> setTime(String time, View view) {
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
        if (start.equals("am") && end.equals("am")) {
            startSpinner.setSelection(1);
            startSpinner.setEnabled(false);
            endSpinner.setSelection(1);
            endSpinner.setEnabled(false);
        } else if (start.equals("am") && end.equals("pm")) {
            startSpinner.setSelection(1);
            endSpinner.setSelection(1);
        } else {
            startSpinner.setSelection(2);
            startSpinner.setEnabled(false);
            endSpinner.setSelection(2);
            endSpinner.setEnabled(false);
        }
        //return only the numbers from the time
        List<Integer> timeIntegers = new ArrayList();
        timeIntegers.add(Integer.parseInt(startTime.replaceAll("[\\D]", "")));
        timeIntegers.add(Integer.parseInt(endTime.replaceAll("[\\D]", "")));
        return timeIntegers;
    }
}
