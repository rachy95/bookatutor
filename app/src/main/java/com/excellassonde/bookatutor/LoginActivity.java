package com.excellassonde.bookatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {
    AutoCompleteTextView studentNameTextBox, studentEmailTextBox;
    String studentName = "";
    String studentEmail = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void bookSession(View view){
        studentNameTextBox = (AutoCompleteTextView) findViewById(R.id.firstName);
        studentEmailTextBox = (AutoCompleteTextView) findViewById(R.id.email);
        Intent intent = new Intent(this, ChooseCourseActivity.class);
        studentName = studentNameTextBox.getEditableText().toString();
        studentEmail = studentEmailTextBox.getEditableText().toString();
//        if(!isValidEmail(studentEmail)){
//            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
//        }
//        else {
//            DaysTab.setStudentName(studentName);
//            DaysTab.setStudentEmail(studentEmail);
            startActivity(intent);
//        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
