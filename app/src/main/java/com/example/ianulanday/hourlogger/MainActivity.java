package com.example.ianulanday.hourlogger;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/*
====================
    Hour Logger
====================

Made this to learn about how memory is handled in AS as well as UI design practice.

Still doesn't store anything in memory! coming soon...

 */

public class MainActivity extends AppCompatActivity {

    ItemAdapter itemAdapter;
    ListView activityListView;

    BaseData data;

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
        data = (BaseData)readData();
        if (data == null) {
            data = new BaseData();
        }

        itemAdapter = new ItemAdapter(this, data.items, data.times);
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
                        data.items.add(entry);
                        data.times.add("0.0");
                        Toast.makeText(MainActivity.this, "new activity added!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "nothing happened!",
                                Toast.LENGTH_SHORT).show();
                    }

                    pwEntry.setText("");
                    pw.dismiss();
                    writeData(data);
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

                        Double timeDouble = Double.parseDouble(data.times.get(i));
                        timeDouble += Double.parseDouble(entry);
                        data.times.set(i, timeDouble.toString());

                        Toast.makeText(MainActivity.this, "Added "
                                + entry + " hours to \"" + data.items.get(i) + ".\"", Toast.LENGTH_LONG).show();
                        itemAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Never mind!",
                                Toast.LENGTH_SHORT).show();
                    }

                    enterHrsEntry.setText("");
                    enterHrsWindow.dismiss();
                    writeData(data);
                    return true;
                }

                return false;
            }
        });

        itemAdapter.notifyDataSetChanged();
    }


    public void onDelete(View view) {
        enterHrsWindow.dismiss();

        Toast.makeText(MainActivity.this, "Activity \"" + data.items.get(itemClickedIndex)
                        + "\" has been removed!",
                Toast.LENGTH_SHORT).show();

        data.times.remove(itemClickedIndex);
        data.items.remove(itemClickedIndex);

        itemAdapter.notifyDataSetChanged();
        writeData(data);
    }

    private Object readData() {
        Object result;
        FileInputStream fis;
        ObjectInputStream ois;
        Log.e("debugLoading", "READING CACHE...");
        try {
            File file = new File(getCacheDir(), "cache");
            if (!file.exists())
                if (!file.createNewFile())
                    return null;
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            result = ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("cache" + " not found");
            return null;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            System.err.println("cache" + " input stream corrupted");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("I/O error in reading " + "cache");
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * Writes loaded app data to cache
     */
    private void writeData(BaseData data) {
        Log.e("debugLoading", "WRITING CACHE...");
        FileOutputStream fos;
        ObjectOutputStream oos;
        File file = new File(getCacheDir(), "cache");
        try {
            fos = new FileOutputStream(file, false);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("cache" + " not found");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            System.err.println("cache" + " output stream corrupted");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("I/O error in writing " + "cache");
        }
    }

}