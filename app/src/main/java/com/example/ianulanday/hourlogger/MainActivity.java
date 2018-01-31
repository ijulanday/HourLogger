package com.example.ianulanday.hourlogger;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LinearLayout parentLinearLayout;
    TextView totalTimeView;
    Integer totalTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLinearLayout = (LinearLayout)findViewById(R.id.parent_linear_layout);
    }

    public void onAddField(View view) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.logger_list_detail, null);
        parentLinearLayout.addView(rowView);

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
                        keepTime(addTime.getText().toString());
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
        totalTime = 0;
    }

    public void keepTime(String userTime) {
        totalTimeView = (TextView)findViewById(R.id.total_time);

        totalTime += Integer.parseInt(userTime);
        userTime = totalTime.toString();

        totalTimeView.setText(userTime);
    }
}
