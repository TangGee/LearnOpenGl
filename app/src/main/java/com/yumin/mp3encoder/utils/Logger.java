package com.yumin.mp3encoder.utils;

import android.util.Log;

public class Logger {

    private String mTag;
    private boolean mOpen;

    public Logger(String tag,boolean open) {
        mTag = tag;
        mOpen = open;
    }

    public void e(String content) {
        if (mOpen) {
            Log.e(mTag,content);
        }
    }

    public void i(String content) {
        if (mOpen) {
            Log.i(mTag,content);
        }
    }

    public void w(String content) {
        if (mOpen) {
            Log.w(mTag,content);
        }
    }
}
