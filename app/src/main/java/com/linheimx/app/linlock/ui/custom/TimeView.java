
package com.linheimx.app.linlock.ui.custom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.Time;
import android.util.AttributeSet;
import android.widget.TextView;

import com.linheimx.app.linlock.util.DateUtils;


/**
 * Created by Artem on 29.01.14.
 */
public class TimeView extends TextView {

    private static final String TAG = "TimeView";

    private int mLastTime = -1;

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_TIME_TICK:
                case Intent.ACTION_TIME_CHANGED:
                case Intent.ACTION_TIMEZONE_CHANGED:
                    updateClock();
                    break;
            }
        }
    };

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        getContext().registerReceiver(mIntentReceiver, filter, null, null);

        updateClock();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(mIntentReceiver);
    }

    protected void updateClock() {
        Time time = new Time();
        time.setToNow();

        int now = time.hour * 60 + time.minute;
        if (now != mLastTime) {
            setText(DateUtils.formatTime(getContext(), time.hour, time.minute));
            mLastTime = now;
        }
    }
}
