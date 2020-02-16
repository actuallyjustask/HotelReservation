package com.example.week6_myself;

import android.app.IntentService;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.week6_myself.provider.TaskScheme;
import com.example.week6_myself.provider.TasksDBHelper;

public class ItemCursorAdapter extends CursorAdapter {
    TasksDBHelper hotelDB;
    MainActivity m1;
    public ItemCursorAdapter(MainActivity context, Cursor c, int flags, TasksDBHelper _db) {
        super(context, c, flags);
        hotelDB =_db;
        m1 = context;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_header, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView num_rooms=view.findViewById(R.id.num_rooms);
        TextView num_guests=view.findViewById(R.id.num_guests);

        num_rooms.setText(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.NUM_ROOMS)));
        num_guests.setText(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.NUM_GUEST)));
        final String id = cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.ID));


        Button removeBtn = view.findViewById(R.id.remove_data);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteShape(id);
                m1.updateText();
            }
        });

    }

    public void deleteShape(String id) {
        hotelDB.deleteTask(Integer.parseInt(id));
        changeCursor(hotelDB.getAllTasks());
    }

}