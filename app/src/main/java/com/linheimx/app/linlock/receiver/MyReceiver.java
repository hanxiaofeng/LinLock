package com.linheimx.app.linlock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.linheimx.app.linlock.ui.ac.LockScreenActivity;

/**
 * Created by LJIAN on 2016/5/25.
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != context) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
                    || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

                // || intent.getAction().equals(Intent.ACTION_SCREEN_ON
                // start view: consider call
                TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                boolean isPhoneIdle = tManager.getCallState() == TelephonyManager.CALL_STATE_IDLE;
                if (isPhoneIdle) {
                    startLockscreenActivity(context);
                }

            }
        }
    }


    private void startLockscreenActivity(Context context) {
        Intent mIntent = new Intent(context, LockScreenActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }

}
