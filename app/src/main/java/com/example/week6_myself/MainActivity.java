package com.example.week6_myself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.week6_myself.provider.TaskScheme;
import com.example.week6_myself.provider.TasksDBHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TasksDBHelper hotelDB;
    View view;
    ListView lvItems;
    ItemCursorAdapter itemAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Reference to the DB
        hotelDB = new TasksDBHelper(this, TasksDBHelper.DB_NAME, null, TasksDBHelper.DB_VERSION);
        itemAdaptor = new ItemCursorAdapter(this, null, 2, hotelDB);

        //Reference to the listview
        lvItems = findViewById(R.id.list_task);
        //Add a header to the listview
        //lvItems.addHeaderView(View.inflate(this, R.layout.list_header, null));
        //link the adapter and the listview
        lvItems.setAdapter(itemAdaptor);
        // this function is used to fetch all the tasks from the database and provide them to the adapter.
        loadTasks();
    }

    private void loadTasks() {
        //Cursor is a temporary buffer area which stores results from a SQLiteDataBase query.
        Cursor cursor = hotelDB.getAllTasks();
        itemAdaptor.changeCursor(cursor);
    }

    public void addTaskBtnHandler(View view) {

        ContentValues contentValues = new ContentValues();

        EditText num_rooms = findViewById(R.id.Num_Room_add);
        EditText num_guests = findViewById(R.id.Num_Guest_add);
        // save all the values of the three columns in a contentvalues object
        //ContentValues is a name value pair, used to insert or update values into database tables.
        // ContentValues object will be passed to SQLiteDataBase objects insert() and update() functions.
        contentValues.put(TaskScheme.NUM_GUEST, num_guests.getText().toString());
        contentValues.put(TaskScheme.NUM_ROOMS, num_rooms.getText().toString());
        // The implementation of addTask function can be found in TasksDBHelper
        TextView totNumRoom = findViewById(R.id.textView2);
        TextView totNumGuest = findViewById(R.id.textView4);
        int numRoom=0;
        int numGuest=0;
        if(totNumGuest.getText()!="" && totNumRoom.getText()!="") {
             numRoom = Integer.parseInt(totNumRoom.getText().toString());
             numGuest = Integer.parseInt(totNumGuest.getText().toString());
        }
        if(numRoom+Integer.parseInt(num_rooms.getText().toString())>100 || numGuest+Integer.parseInt(num_guests.getText().toString())>150){
            Toast.makeText(getBaseContext(),"Limit Exceeded",Toast.LENGTH_LONG).show();
        }
        else{
            hotelDB.addTask(contentValues);
            updateText();
            loadTasks();
        }
    }

    public void deleteTasks(View view){
        Cursor cursor=hotelDB.getAllTasks();
        EditText editText = findViewById(R.id.deleteText);
        int deleteRow = Integer.parseInt(editText.getText().toString());
        int counter=0;
        for(cursor.moveToLast();counter<deleteRow;cursor.moveToPrevious()){
            hotelDB.deleteTask(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.ID))));
            //taskId.add(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.ID))));
            counter++;
        }
        loadTasks();
        updateText();
    }

    public void updateText(){
        Cursor cursor = hotelDB.getAllTasks();
        TextView numRoom = findViewById(R.id.textView2);
        TextView numGuest = findViewById(R.id.textView4);
        int numOfGuest=0;
        int numOfRooms=0;
        while(cursor.moveToNext()){
            numOfRooms+=Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.NUM_ROOMS)));
            numOfGuest+=Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(TaskScheme.NUM_GUEST)));
        }
        numRoom.setText(Integer.toString(numOfRooms));
        numGuest.setText(Integer.toString(numOfGuest));
    }
}