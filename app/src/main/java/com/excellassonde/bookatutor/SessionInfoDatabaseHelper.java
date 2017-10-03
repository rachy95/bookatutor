package com.excellassonde.bookatutor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by praiseayorinde on 2017-09-23.
 */

public class SessionInfoDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sessionInformation.db";
    private static final String TABLE_NAME = "sessionInformation";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TUTOR_NAME = "tutor";
    private static final String COLUMN_COURSE = "course";
    private static final String COLUMN_START_TIME = "start";
    private static final String COLUMN_END_TIME = "end";
    private static final String COLUMN_STUDENTS_EMAIL = "studentEmails";
    private static final String COLUMN_SESSION_TYPE = "sessionType";
    private static final String COLUMN_SESSION_DATE = "date";
    private static final String COLUMN_SESSION_SCORE = "score";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table sessionInformation (id integer primary key not null, " +
            "tutor text not null, course text not null, start text not null, end text not null, studentEmails text not null, " +
            "sessionType text not null, date text not null, score integer)";

    public SessionInfoDatabaseHelper(Context context){
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

    public void insertSession(SessionInformation session){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //so that each id is unique, we get the count
        String query = "select * from sessionInformation";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count++);
        session.setId(count);
        values.put(COLUMN_TUTOR_NAME, session.getTutorName());
        values.put(COLUMN_COURSE, session.getCourse());
        values.put(COLUMN_START_TIME, session.getStartTime());
        values.put(COLUMN_END_TIME, session.getEndTime());
        values.put(COLUMN_SESSION_TYPE, session.getSessionType());
        values.put(COLUMN_SESSION_DATE, session.getSessionDate());
        values.put(COLUMN_SESSION_SCORE, session.getSessionScore());
        values.put(COLUMN_STUDENTS_EMAIL, session.getStudentEmails());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

}
