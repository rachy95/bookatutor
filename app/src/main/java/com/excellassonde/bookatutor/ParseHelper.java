package com.excellassonde.bookatutor;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ParseHelper {
    private static final String ns = null;

    public static class Tutor{
        public final String name;
        public final List<String> courses;
        public final HashMap<String, List<String>> availability;


        private Tutor(String name, List course, HashMap<String, List<String>> availabilities) {
            this.name = name;
            this.courses = new ArrayList<>(course);
            this.availability = new HashMap<>(availabilities);
        }

        public boolean teachesCourse(String course){
            return courses.contains(course);
        }

        public boolean availableThatDay(String day){
            return availability.containsKey(day);
        }

        public List<String> getTimes(String day){
            return availability.get(day);
        }
    }

    public List<Tutor> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<Tutor> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "Tutors");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the tutor tag
            if (name.equals("Tutor")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }



    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Tutor readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "Tutor");
        String name = null;
        List<String> course = null;
        HashMap<String, List<String>> avail = null;
        String day = "";

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals("Name")) {
                name = readName(parser);
            } else if (tag.equals("Course")) {
                course = readCourse(parser);
            } else if (name.equals("Availability")) {
                avail  = readAvailability(parser);
            } else if (name.equals("Day")) {
                day = readDay(parser);
                avail.put(day, null);
            } else{
                skip(parser);
            }
        }
        return new Tutor(name, course, avail);
    }

    // Processes name tags in the feed.
    private String readName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Name");
        String name = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Name");
        return name;
    }

    // Processes day tags in the feed.
    private String readDay(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Day");
        String day = parser.getAttributeValue(null, "day");
        parser.require(XmlPullParser.END_TAG, ns, "Day");
        return day;
    }

    // Processes course tags in the feed.
    private List<String> readCourse(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Course");
        String courses = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Course");

        //separate the list of courses by comma
        List<String> listCourses = new ArrayList<>(Arrays.asList(courses.split(", ")));
        return listCourses;
    }

    private List<String> readTimes(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Times");
        String times = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "Times");

        //separate the list of courses by comma
        List<String> listTimes = new ArrayList<>(Arrays.asList(times.split(", ")));
        return listTimes;
    }

    // Processes time tags in the feed.
    private HashMap<String, List<String>> readAvailability(XmlPullParser parser) throws IOException, XmlPullParserException {
        HashMap<String, List<String>> result = new HashMap<>();
        String day = "";
        List<String> times = null;

        parser.require(XmlPullParser.START_TAG, ns, "Availability");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String tag = parser.getName();
            if (tag.equals("Day")) {
                day = readDay(parser);
            }
            else if(tag.equals("Times")){
                times = readTimes(parser);
            }
        }
        result.put(day, times);

        return result;

    }
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }



}


