package com.example.ianulanday.hourlogger;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Ian on 2/8/2018.
 */

public class BaseData implements Serializable {
    ArrayList<String> items;
    ArrayList<String> times;

    public BaseData() {
        times = new ArrayList<String>();
        items = new ArrayList<String>();
    }
}
