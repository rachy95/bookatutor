package com.excellassonde.bookatutor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    AutoCompleteTextView studentNameTextBox, studentEmailTextBox;
    String studentName = "";
    String studentEmail = "";

    DatabaseReference ref;

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    public void bookSession(View view){
        studentNameTextBox = (AutoCompleteTextView) findViewById(R.id.firstName);
        studentEmailTextBox = (AutoCompleteTextView) findViewById(R.id.email);
        Intent intent = new Intent(this, ChooseCourseActivity.class);
        studentName = studentNameTextBox.getEditableText().toString();
        studentEmail = studentEmailTextBox.getEditableText().toString();
        setAllCourses();
        setAllTutors();
        //we need to instantiate the tutor parser
        //  TutorXmlParser parser = new TutorXmlParser(this);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
//        if(!isValidEmail(studentEmail)){
//            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_LONG).show();
//        }
//        else {
//            DaysTab.setStudentName(studentName);
//            DaysTab.setStudentEmail(studentEmail);
        SessionReview.setStudentName(studentName);
            startActivity(intent);
//        }
    }

    public void setAllCourses() {
        final Set<String> courses = new HashSet<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tutorsRef = ref.child("Tutors");
        tutorsRef.keepSynced(true);

        tutorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    TutorInformation tutor = child.getValue(TutorInformation.class);
                    courses.addAll(tutor.getCourses());
                }
                ChooseCourseActivity.setCourses(courses);
                Intent intent = new Intent(getApplicationContext(), ChooseCourseActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", databaseError.getDetails());
            }
        });

    }

    public void setAllTutors() {
        final List<TutorInformation> tutors = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tutorsRef = ref.child("Tutors");
        tutorsRef.keepSynced(true);

        tutorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    TutorInformation tutor = child.getValue(TutorInformation.class);
                    tutors.add(tutor);
                }
                DaysTab.setTutors(tutors);
                Intent intent = new Intent(getApplicationContext(), DaysTab.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", databaseError.getDetails());
            }
        });

    }
}
