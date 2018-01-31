package com.example.ianulanday.hourlogger;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    LinearLayout parentLinearLayout;
    TextView totalTimeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLinearLayout = (LinearLayout)findViewById(R.id.parent_linear_layout);
    }

    public void onAddField(View view) {

        /*
        big problem: I can't define totalTimeView here. Probs because i'd need to
            setContentView or something, but that crashes the app. I tried making
            another class and calling it here after inflating parent layout in an
            attempt to use the constructor to make addTime and call
            setOnKeyListener, but that just crashed the app upon clicking the FAB.
        */

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.logger_list_detail, null);
        parentLinearLayout.addView(rowView);

        totalTimeView = (TextView)rowView.findViewById(R.id.total_time);
        totalTimeView.setText("wow!");

        Integer totalTime = 0;

        //addTime is the "+ time" EditText view.
        final EditText addTime = (EditText)findViewById(R.id.add_stuff);
        addTime.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String addTimeStuff = addTime.getText().toString();
                    if (addTimeStuff.isEmpty() == true) {
                        Toast.makeText(getApplicationContext(), "Nothing happened!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else {
                        //TODO (1): Add value in entered by user into addTime to totalTime, and set totalTime text
                        Toast.makeText(getApplicationContext(), "Time recorded!", Toast.LENGTH_SHORT).show();
                        addTime.setText("");
                        return true;
                    }
                }

                return false;
            }
        });


    }

    public void onDelete(View view) {
        parentLinearLayout.removeView((View)view.getParent());

    }

}