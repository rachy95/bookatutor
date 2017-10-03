package com.excellassonde.bookatutor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by praiseayorinde on 2017-09-23.
 */

public class TutorInfoDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tutorInformation.db";
    private static final String TABLE_NAME = "tutorInformation";
    private static final String COLUMN_TUTOR_NAME = "name";
    private static final String COLUMN_TUTOR_EMAIL = "email";
    private static final String COLUMN_COURSES = "courses";
    private static final String COLUMN_MAX_HOURS = "Available hours";
    private static final String COLUMN_AVAILABILITY_ID = "";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table studentInformation (id integer primary key not null, " +
            "name text not null, email text not null, sessions text)";

    public TutorInfoDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
//
//    public void insertTutor(TutorInformation tutor){
//        db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        //so that each id is unique, we get the count
//        String query = "select * from tutorInformation";
//        values.put(COLUMN_STUDENT_NAME, student.getStudentName());
//        values.put(COLUMN_STUDENT_EMAIL, student.getStudentEmail());
//        values.put(COLUMN_SESSION_IDs, student.getSessionIDs());
//
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//    }
//
//    public class AvailabilityDatabaseHelper extends SQLiteOpenHelper {
//        private static final int DATABASE_VERSION = 1;
//        private static final String DATABASE_NAME = "availabilty.db";
//        private static final String TABLE_NAME = "availability";
//        private static final String COLUMN_ID = "id";
//        private static final String COLUMN_DAY = "courses";
//        private static final String COLUMN_IS_BOOKED = "isBooked";
//        private static final String COLUMN_  = "";
//
//        SQLiteDatabase db;
//
//        private static final String TABLE_CREATE = "create table studentInformation (id integer primary key not null, " +
//                "name text not null, email text not null, sessions text)";
//
//        public TutorInfoDatabaseHelper(Context context){
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//            db = this.getWritableDatabase();
//        }
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(TABLE_CREATE);
//            this.db = db;
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            String query = "DROP TABLE IF EXISTS "+ TABLE_NAME;
//            db.execSQL(query);
//            this.onCreate(db);
//        }
//
//        public void insertTutor(TutorInformation tutor){
//            db = this.getWritableDatabase();
//            ContentValues values = new ContentValues();
//
//            //so that each id is unique, we get the count
//            String query = "select * from tutorInformation";
//            values.put(COLUMN_STUDENT_NAME, student.getStudentName());
//            values.put(COLUMN_STUDENT_EMAIL, student.getStudentEmail());
//            values.put(COLUMN_SESSION_IDs, student.getSessionIDs());
//
//            db.insert(TABLE_NAME, null, values);
//            db.close();
//        }
//    }


}
