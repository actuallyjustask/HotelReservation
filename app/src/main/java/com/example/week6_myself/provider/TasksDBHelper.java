package com.example.week6_myself.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TasksDBHelper extends SQLiteOpenHelper {
    public final static String DB_NAME = "Lab6DB.db";
    public final static int DB_VERSION = 1;
    Context myContext;

    private final static String HOTEL_TABLE_CREATE =
            "CREATE TABLE " +
                    TaskScheme.TABLE_NAME + " (" +
                    TaskScheme.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    TaskScheme.NUM_GUEST + " TEXT, " +
                    TaskScheme.NUM_ROOMS+ " TEXT);";


    public TasksDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(HOTEL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TaskScheme.TABLE_NAME);
        onCreate(db);
    }

    public void addTask(ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TaskScheme.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor tasks = db.rawQuery("select * from " + TaskScheme.TABLE_NAME, null);
        return tasks;
    }

    public boolean deleteTask(int taskId) {
        boolean result;
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {Integer.toString(taskId)};
        result = db.delete(TaskScheme.TABLE_NAME, "_id=?", args) > 0;
        return result;
    }
}
