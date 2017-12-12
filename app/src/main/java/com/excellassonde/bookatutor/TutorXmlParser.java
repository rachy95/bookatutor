//package com.excellassonde.bookatutor;
//
//import android.content.Context;
//import android.content.res.XmlResourceParser;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.util.AbstractMap;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by praiseayorinde on 2017-11-19.
// */
//
//public class TutorXmlParser {
//
//    //namespaces
//    private static  final String ns = null;
//
//    private static  Context context;
//    public TutorXmlParser(Context c){
//        context = c;
//    }
//
//
//    /***
//     * Returns a list of tutor objects
//     * Each tutor object has definitions as defined in the tutor class
//     * @return list of tutors in the xml file
//     */
//    public static  List<TutorInformation> getTutorsList(){
//        //get the list of tutors
//        List<TutorInformation> tutorsList = null;
//        try {
//            tutorsList = readTutors();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
////        //insert in the database
////        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Tutors");
////
////        for (int i= 0; i<tutorsList.size(); i++) {
////            // Creating new user node, which returns the unique key value
////            // new user node would be /users/$userid/
////            String tutorId = mDatabase.push().getKey();
////
////            // creating user object
////            TutorInformation tutor = tutorsList.get(i);
////
////            // pushing user to 'users' node using the userId
////            mDatabase.child(tutorId).setValue(tutor);
////        }
//
//        return tutorsList;
//    }
//
//    /***
//     * parse the xml file, and call the respective methods to read different entries
//     * @return list of tutors
//     * @throws XmlPullParserException
//     * @throws IOException
//     */
//    private static List readTutors() throws XmlPullParserException, IOException{
//        //list of tutors to return
//        List tutors = new ArrayList();
//        XmlResourceParser parser = context.getResources().getXml(R.xml.tutors);
//        parser.next();
//        parser.nextTag();
//        parser.require(XmlPullParser.START_TAG, ns, "Tutors");
//        while (parser.next() != XmlPullParser.END_TAG){
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                continue;
//            }
//            String name = parser.getName();
//            // Start by looking for the tutor tag
//            if(name.equals("TutorInformation")){
//                tutors.add(readTutor(parser));
//            }
//        }
//
//        return tutors;
//    }
//
//    //private method to parse the contents in a tutor tag, then calls the respective methods to parse the nested tags
//    private static TutorInformation readTutor(XmlPullParser parser) throws XmlPullParserException, IOException{
//        parser.require(XmlPullParser.START_TAG, ns, "TutorInformation");
//        String name = null;
//        String phoneNumber = "";
//        List courses = null;
//        double availableHours = 0;
//        Map<String, Map<String, Boolean>> availability = null;
//
//        while (parser.next() != XmlPullParser.END_TAG){
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                continue;
//            }
//            String tag = parser.getName();
//            if (tag.equals("Name")){
//                name = readName(parser);
//            }
//            else if (tag.equals("Course")){
//                courses = readCourse(parser);
//            }
//            else if(tag.equals("PhoneNumber")){
//                phoneNumber = readPhoneNumber(parser);
//            }
//            else if(tag.equals("AvailableHours")){
//                availableHours = readAvailableHours(parser);
//            }
//            else if (tag.equals("Availability")){
//                availability = readAvailability(parser);
//            }
//        }
//        return new TutorInformation(name, phoneNumber, courses, availableHours, availability);
//    }
//
//    //private method to parse the names of tutors
//    private static String readName(XmlPullParser parser) throws IOException, XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG, ns, "Name");
//        String name = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "Name");
//        return name;
//    }
//
//    //private method that returns a list a tutor can teach
//    private static  List<String> readCourse(XmlPullParser parser) throws IOException, XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG, ns, "Course");
//        String course = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "Course");
//
//        List<String> courses = Arrays.asList(course.split(", "));
//        return courses;
//    }
//
//    //private method that returns the tutor's phone number
//    private static String readPhoneNumber(XmlPullParser parser) throws IOException, XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG, ns, "PhoneNumber");
//        String phoneNumber = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "PhoneNumber");
//        return phoneNumber;
//    }
//
//    //process the maxHours that the tutor is willing  to teach for
//    private static double readAvailableHours(XmlPullParser parser) throws IOException, XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG, ns, "AvailableHours");
//        String availability = readText(parser);
//        parser.require(XmlPullParser.END_TAG, ns, "AvailableHours");
//        return Double.parseDouble(availability);
//    }
//
//    //process the availability of the tutor, it returns the day as a key
//    //and the times are the values mapped to that day
//    private static Map<String, Map<String, Boolean>> readAvailability(XmlPullParser parser) throws IOException, XmlPullParserException{
//        Map<String, Map<String, Boolean>> avails = new HashMap<>();
//
//        parser.require(XmlPullParser.START_TAG, ns, "Availability");
//
//        while(parser.next() != XmlPullParser.END_TAG){
//            if(parser.getEventType() != XmlPullParser.START_TAG){
//                continue;
//            }
//            String tag = parser.getName();
//            if(tag.equals("Day")){
//                String day = readDay(parser);
//                Map<String, Boolean> times = new HashMap<>();
//                while(parser.next() != XmlPullParser.END_TAG){
//                    if(parser.getEventType() != XmlPullParser.START_TAG){
//                        continue;
//                    }
//                    tag = parser.getName();
//                    if(tag.equals("Time")){
//                        Map<String,Boolean> time = readTime(parser);
//                        times.add(time);
//                    }
//                }
//                avails.put(day, times);
//                parser.require(XmlPullParser.END_TAG, ns, "Day");
//            }
//        }
//        parser.require(XmlPullParser.END_TAG, ns, "Availability");
//        return avails;
//    }
//
//    //process the day the tutor is free
//    private static String readDay(XmlPullParser parser) throws IOException, XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG, ns, "Day");
//        String day = parser.getAttributeValue(0);
//        return day;
//    }
//
//    //process the times the tutor is free
//    private static Map.Entry<String, Boolean> readTime(XmlPullParser parser) throws IOException, XmlPullParserException{
//        parser.require(XmlPullParser.START_TAG, ns, "Time");
//        String booked = parser.getAttributeValue(0);
//        String time = readText(parser);
//        boolean isBooked = false;
//        if(booked.equals("false")){
//            isBooked = false;
//        }
//        else if (booked.equals("true")){
//            isBooked = true;
//        }
//
//        parser.require(XmlPullParser.END_TAG, ns, "Time");
//        return new AbstractMap.SimpleEntry<>(time, isBooked);
//    }
//
//
//    // For the tags name, and courses, extracts their text values.
//    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
//        String result = "";
//        if (parser.next() == XmlPullParser.TEXT) {
//            result = parser.getText();
//            parser.nextTag();
//        }
//        return result;
//    }
//
//}
//
//
