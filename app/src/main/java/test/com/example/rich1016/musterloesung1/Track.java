package test.com.example.rich1016.musterloesung1;

import java.io.Serializable;

/**
 * Created by vofr1011 on 20.01.2018.
 */

public class Track implements Serializable {
    private int id;
    private String name;
    private String date;
    private String duration;
    private double length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
