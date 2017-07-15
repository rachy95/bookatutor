package com.excellassonde.bookatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class ChooseDayActivity extends AppCompatActivity {

    private  Spinner dayDropdown;
    public static String daySelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_day);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ChooseCourseActivity.courseSelected);
        // Capture the layout's TextView and set the string as its text 
        TextView text = (TextView) findViewById(R.id.displayCourse);
        text.setText(message);

    }

    public void chosenDay(View view){
        dayDropdown = (Spinner) findViewById(R.id.day_dropdown);
        Intent intent = new Intent(this, ChooseTutorActivity.class);
        String dayChoice = String.valueOf(dayDropdown.getSelectedItem());
        intent.putExtra(daySelected, dayChoice);
        startActivity(intent);
    }




}
