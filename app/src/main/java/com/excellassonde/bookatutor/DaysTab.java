package com.excellassonde.bookatutor;

import android.content.res.XmlResourceParser;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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

    public  List<Tutor> getTutorsList(){
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

    //parse the xml file
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

    public  boolean AreTutorsAvailableThatDay(String day){
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

    public  List<Tutor> getTutors(String day) {
        List<Tutor> tutorsList = getTutorsList();
        List<Tutor> result = new ArrayList<>();
        for (int i = 0; i < tutorsList.size(); i++) {
            Set<String> daysAvailable = tutorsList.get(i).getDaysAvailable();
            if(daysAvailable.contains(day)){
                result.add(i, tutorsList.get(i));
            }
        }
        return result;
    }
}
