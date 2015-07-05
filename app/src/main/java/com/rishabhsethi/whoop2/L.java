package com.rishabhsethi.whoop2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rishabh on 17/6/15.
 */
public class L {
    public static void m(String message) {
        Log.d("VIVZ", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void T(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }
}
