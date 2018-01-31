package com.example.ianulanday.hourlogger;

/**
 * Created by ianulanday on 1/31/2018.
 */

public class ActivityTime {
    private Double netTime;

    public ActivityTime() {
        this.netTime = 0.0;
    }

    public String getNetTimeStr() {
        return this.netTime.toString();
    }

    public void addTime(final String timeStr) {
        this.netTime += Double.valueOf(timeStr);
    }
}
