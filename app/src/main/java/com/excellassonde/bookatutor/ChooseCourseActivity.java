package com.excellassonde.bookatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ChooseCourseActivity extends AppCompatActivity {

      private Spinner spinner;
      public static String courseSelected;
      String courseChoice;
      Button chooseTutorButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);

    }

    public void goToChooseDayAndTutor(View view){
        spinner = (Spinner) findViewById(R.id.courses_dropdown);
        Intent intent = new Intent(this, ChooseDayAndTutor.class);
        courseChoice = String.valueOf(spinner.getSelectedItem());
        intent.putExtra(courseSelected, courseChoice);
        startActivity(intent);

    }
}
