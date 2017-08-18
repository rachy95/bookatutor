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

public class FridayTab extends DaysTab {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get the view from activity_friday_tab.xml
        View view = inflater.inflate(R.layout.activity_friday_tab, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.FridayTab);
        //get the list of tutors
        String day = "Friday";
        setDisplay(day, view, layout);
        return view;
    }
}
