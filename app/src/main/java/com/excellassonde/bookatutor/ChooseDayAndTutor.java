package com.excellassonde.bookatutor;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ChooseDayAndTutor extends AppCompatActivity {
    private Spinner courseDropDown;
    private TextView displayCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_day_and_tutor);
        displayCourse = (TextView) findViewById(R.id.toolbarText);
        Intent intent = getIntent();
        String course = intent.getStringExtra(ChooseCourseActivity.courseSelected);
        String text = "Course chosen : "+ course;
        displayCourse.setText(text);

        TabLayout daysTabs = (TabLayout) findViewById(R.id.dayTabs);

        final PagerAdapter adapter = new TabPagerAdapter (getSupportFragmentManager(), daysTabs.getTabCount());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pageView);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(daysTabs));
        daysTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

}
