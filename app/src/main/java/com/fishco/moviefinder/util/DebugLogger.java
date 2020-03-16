package com.fishco.moviefinder.util;

import android.util.Log;

public class DebugLogger {
    private static final String TAG = "TAG_X";
    private static final String ERROR_PREFIX = "Error: ";

    public static void logDebug(String debug) {
        Log.d(TAG, debug);
    }

    public static void logError(Exception e) {
        Log.d(TAG, ERROR_PREFIX + e.getLocalizedMessage());
    }
}
