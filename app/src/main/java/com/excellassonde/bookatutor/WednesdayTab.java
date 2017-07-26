package com.excellassonde.bookatutor;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class WednesdayTab extends DaysTab {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the view from activity_wednesday_tab.xml
        View view = inflater.inflate(R.layout.activity_wednesday_tab, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.WednesdayTab);
        //get the list of tutors
        String day = "Wednesday";
        String display = "";
        Intent intent = getActivity().getIntent();
        String course = intent.getStringExtra(ChooseCourseActivity.courseSelected);

        TextView textView = new TextView(view.getContext());

        if (AreTutorsAvailableThatDay(day)) {
            List<Tutor> tutors = getTutors(day);
            for (int i = 0; i < tutors.size(); i++) {
                if(tutors.get(i).teachCourse(course)){
                    List<String> times =  tutors.get(i).getTimes(day);
                    for(int j = 0; j<times.size(); j++){
                        display += tutors.get(i).name + ": " + times.get(j) + "\n";
                    }

                }
                else{
                    display = "Sorry, there are no tutors available for " + course + " on " + day;
                }
            }
        } else {
            display = "Sorry, there are no tutors available on " + day;
        }

        textView.setText(display);
        layout.addView(textView);
        return view;
    }
}
