package com.excellassonde.bookatutor;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {
    public static Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        res = getResources();
    }

    public void bookSession(View view){
        Intent intent = new Intent(this, ChooseCourseActivity.class);
        startActivity(intent);
    }
}
