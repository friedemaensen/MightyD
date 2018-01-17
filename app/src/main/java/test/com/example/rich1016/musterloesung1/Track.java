package test.com.example.rich1016.musterloesung1;

/**
 * Created by vofr1011 on 15.01.2018.
 */

public class Track {
    private String id;
    private String mode;
    private String name;
    private String date;
    private String length;
    private String firstLoc;
    private String lastLoc;
    private String duration;

    public String getId() { return id; }

    public String getMode() { return mode; }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLength() {
        return length;
    }

    public String getDuration() {
        return duration;
    }

    public void setId (String id) { this.id = id; }

    public void setMode (String mode) { this.mode = mode; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setFirstLoc(String firstLoc) {
        this.firstLoc = firstLoc;
    }

    public void setLastLoc(String lastLoc) {
        this.lastLoc = lastLoc;
    }

    public String getFirstLoc() {
        return firstLoc;
    }

    public String getLastLoc() {
        return lastLoc;
    }
}
