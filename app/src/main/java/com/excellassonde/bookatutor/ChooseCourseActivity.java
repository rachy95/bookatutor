package com.excellassonde.bookatutor;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChooseCourseActivity extends AppCompatActivity {
    public static String courseSelected;
    static Set<String> allCourses;
    List<Button> displayCourses;

    public static void setCourses(Set<String> courses) {
        allCourses = courses;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);
        LinearLayout layout = (LinearLayout) findViewById(R.id.chooseCourse);

        displayCourses = new ArrayList<>();

        //add the display text first

        TextView helperText = new TextView(this);
        helperText.setText("Which Course would you like help in?");
        helperText.setTextSize(20);
        helperText.setTypeface(Typeface.DEFAULT_BOLD);
        helperText.setTypeface(Typeface.SANS_SERIF);
        helperText.setGravity(Gravity.CENTER);
        helperText.setTextColor(getResources().getColor(R.color.colorPrimary));
        //set space between the text and buttons
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(80, 20, 80, 0);
        helperText.setLayoutParams(params);
        helperText.setPadding(20, 20, 20, 60);

        layout.addView(helperText);

        TextView waitText = new TextView(this);
        waitText.setText("Loading...");
        helperText.setTextSize(20);
        helperText.setTypeface(Typeface.DEFAULT_BOLD);
        helperText.setTypeface(Typeface.SANS_SERIF);
        helperText.setGravity(Gravity.CENTER);
        helperText.setTextColor(getResources().getColor(R.color.colorPrimary));
        //set space between the text and buttons
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(80, 20, 80, 0);
        helperText.setLayoutParams(params);
        helperText.setPadding(20, 20, 20, 60);


        layout.addView(waitText);

        if (allCourses != null) {
            layout.removeView(waitText);
            for (final String course : allCourses) {
                //add the tutor and times to a button
                Button button = new Button(this);
                button.setText(course);
                //set the color and the way it looks
                button.setTextColor(getResources().getColor(R.color.colorPrimary));
                button.setBackgroundResource(R.drawable.textbox_background);
                //set space between the buttons
                params.setMargins(60, 20, 60, 0);
                button.setLayoutParams(params);
                button.setPadding(20, 20, 20, 20);
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(ChooseCourseActivity.this, ChooseDayAndTutor.class);
                        DaysTab.setCourseSelected(course);
                        startActivity(intent);

                    }
                });
                displayCourses.add(button);
            }

            //get the buttons and add them to the layout
            for (int i = 0; i < displayCourses.size(); i++) {
                layout.addView(displayCourses.get(i));
            }
        }

    }


}
