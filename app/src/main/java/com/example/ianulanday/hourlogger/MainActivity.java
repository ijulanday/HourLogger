package com.example.ianulanday.hourlogger;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
====================
    Hour Logger
====================

Made this to learn about how memory is handled in AS as well as UI design practice.
Because I want everything to be displayed on one screen, I'm doing everything in just
one activity, but that causes some memory problems.

1)  Some things I haven't gotten to that I want to eventually: The text "whatcha doin'?"
    EditText view needs to somehow retain its value after you close the app;
    parentLinearLayout is restarted when the screen is rotated, so I need to use a
    ViewModel there somehow
2)  Glaring issue: if you add another field, entering time does nothing.

 */

public class MainActivity extends AppCompatActivity {

    ItemAdapter itemAdapter;
    ListView activityListView;

    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> times = new ArrayList<String>();

    PopupWindow pw;
    View pwView;
    EditText pwEntry;

    PopupWindow enterHrsWindow;
    View enterHrsView;
    EditText enterHrsEntry;

    LayoutInflater pwInflater;

    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;

    CoordinatorLayout mainLayout;

    int itemClickedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.activity_main_layout);

        Resources res = getResources();
        activityListView = findViewById(R.id.activity_list);

        //read files from memory

        itemAdapter = new ItemAdapter(this, items, times);
        activityListView.setAdapter(itemAdapter);
        activityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "something happened!",
//                        Toast.LENGTH_SHORT).show();
                onEditField(i);
                itemClickedIndex = i;
            }
        });

        pwInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pwView = pwInflater.inflate(R.layout.popup_window, null);
        pw = new PopupWindow(pwView, width, height, true);
        pwEntry = (EditText) pwView.findViewById(R.id.activity_name_input);

        enterHrsView = pwInflater.inflate(R.layout.popup_window_2, null);
        enterHrsWindow = new PopupWindow(enterHrsView, width, height, true);
        enterHrsEntry = (EditText) enterHrsView.findViewById(R.id.activity_time_input);
    }

    public void onAddField(View view) {
        pwEntry.setHint("name your new activity!");
        pw.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
        pwEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        && (event.getAction() == KeyEvent.ACTION_DOWN))) {

                    String entry = pwEntry.getText().toString();

                    if (!entry.isEmpty()) {
                        items.add(entry);
                        times.add("0.0");
                        Toast.makeText(MainActivity.this, "new activity added!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "nothing happened!",
                                Toast.LENGTH_SHORT).show();
                    }

                    pwEntry.setText("");
                    pw.dismiss();
                    return true;
                }

                return false;
            }
        });

        itemAdapter.notifyDataSetChanged();
    }

    public void onEditField(final int i) {
        enterHrsEntry.setHint("enter some time");
        enterHrsWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
        enterHrsEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        && (event.getAction() == KeyEvent.ACTION_DOWN))) {

                    String entry = enterHrsEntry.getText().toString();

                    if (!entry.isEmpty()) {

                        Double timeDouble = Double.parseDouble(times.get(i));
                        timeDouble += Double.parseDouble(entry);
                        times.set(i, timeDouble.toString());

                        Toast.makeText(MainActivity.this, "activity " + items.get(i) + " now has "
                                + times.get(i) + " hrs logged.", Toast.LENGTH_LONG).show();
                        itemAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "nothing happened!",
                                Toast.LENGTH_SHORT).show();
                    }

                    enterHrsEntry.setText("");
                    enterHrsWindow.dismiss();
                    return true;
                }

                return false;
            }
        });

        itemAdapter.notifyDataSetChanged();
    }


    public void onDelete(View view) {
        enterHrsWindow.dismiss();
        times.remove(itemClickedIndex);
        items.remove(itemClickedIndex);

        Toast.makeText(MainActivity.this, "itemClickedIndex: " + itemClickedIndex,
                Toast.LENGTH_SHORT).show();

        itemAdapter.notifyDataSetChanged();
    }

}