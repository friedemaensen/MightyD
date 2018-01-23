package test.com.example.rich1016.musterloesung1;

import android.content.Context;

/**
 * Created by vofr1011 on 23.01.2018.
 */

public class ChangeModeTo {

    private static ChangeModeTo instance;

    public ChangeModeTo(Context context) {

    }


    public static ChangeModeTo getInstance(Context context) {
        if (instance == null) {
            instance = new ChangeModeTo(context);
        }
        return instance;
    }

}
