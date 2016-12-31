package com.marco.tabs.tabs;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

class Utils {

    private Utils() {
    }


    // PX To DP
    public static int dpToPx(Context context, int dp) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float density = displayMetrics.density;

        return (int) (dp * density);
    }

}
