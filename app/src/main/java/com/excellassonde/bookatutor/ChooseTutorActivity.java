package com.excellassonde.bookatutor;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.InputStream;
import java.util.List;

public class ChooseTutorActivity extends AppCompatActivity {

    private Spinner tutorDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tutor);

        //get the course selected
        Intent intent1 = getIntent();
        String course = intent1.getStringExtra(ChooseCourseActivity.courseSelected);


        //get the day chosen
        Intent intent2 = getIntent();
        String day = intent2.getStringExtra(ChooseDayActivity.daySelected);

        AssetManager assets = getAssets();

        InputStream input = null;
        List tutors = null;
        try {
            input = assets.open("tutors.xml");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            ParseHelper parser = new ParseHelper();
            tutors = parser.parse(input);

        } catch (Exception e) {
            e.printStackTrace();
        }

        tutorDropdown = (Spinner) findViewById(R.id.tutor_dropdown);
        ArrayAdapter<String> dataAdapter = null;
        if(day.equals("Any day")){
            dataAdapter  = new ArrayAdapter<String>(this,R.layout.activity_choose_tutor , getTutors(tutors, course));

        }
        else {
            dataAdapter = new ArrayAdapter<String>(this, R.layout.activity_choose_tutor, getTutors(tutors, day, course));
        }

        dataAdapter.setDropDownViewResource(R.layout.activity_choose_tutor);
        tutorDropdown.setAdapter(dataAdapter);

    }

    public List<ParseHelper.Tutor> getTutors(List<ParseHelper.Tutor> tutors, String day, String course){
        List<ParseHelper.Tutor> result = null;

        for(int i = 0; i< tutors.size(); i++){
           ParseHelper.Tutor tutor = tutors.get(i);
            if(tutor.availableThatDay(day) && tutor.teachesCourse(course)){
              result.add(tutor);
            }
        }
        return result;
    }

    public List<ParseHelper.Tutor> getTutors(List<ParseHelper.Tutor> tutors, String course){
        List<ParseHelper.Tutor> result = null;

        for(int i = 0; i< tutors.size(); i++){
            ParseHelper.Tutor tutor = tutors.get(i);
            if(tutor.teachesCourse(course)){
                result.add(tutor);
            }
        }
        return result;
    }
}



