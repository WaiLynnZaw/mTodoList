package com.wlz.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {
    EditText todo_text;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        assert mToolBar != null;
        mToolBar.setTitle(R.string.app_name);
        setSupportActionBar(mToolBar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Get ListView object from xml
        final ListView listView = (ListView) findViewById(R.id.listView);
        // Create a new Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // Use Firebase to populate the list.
        Firebase.setAndroidContext(this);

        new Firebase("https://mtodolist.firebaseio.com/todoItems")
                .addChildEventListener(new ChildEventListener() {
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        adapter.add((String) dataSnapshot.child("text").getValue());
                        progressBar.setVisibility(View.GONE);
                    }

                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        adapter.remove((String) dataSnapshot.child("text").getValue());
                    }

                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTask();
            }
        });

    }
    private void addTask(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Enter task:")
                .setView(R.layout.input_dialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Firebase("https://mtodolist.firebaseio.com/todoItems")
                                .push()
                                .child("text")
                                .setValue(todo_text.getText().toString());
                    }
                }).show();
        todo_text = (EditText) alertDialog.findViewById(R.id.edit_mail);
        todo_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (todo_text.getText().toString().length() != 0) {

                } else {
                    todo_text.setError("Task should not be blank");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
