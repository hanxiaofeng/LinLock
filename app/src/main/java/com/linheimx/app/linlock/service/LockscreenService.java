package com.linheimx.app.linlock.service;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.linheimx.app.linlock.R;
import com.linheimx.app.linlock.receiver.MyReceiver;
import com.linheimx.app.linlock.ui.ac.LockScreenActivity;
import com.linheimx.app.linlock.util.LockscreenUtil;

/**
 * Created by mugku on 15. 5. 20..
 */
public class LockscreenService extends Service {
    private final String TAG = "LockscreenService";

    private int mServiceStartId = 0;
    private Context _context = null;
    private KeyguardManager _keyManager = null;
    private KeyguardManager.KeyguardLock mKeyLock = null;

    private BroadcastReceiver mLockscreenReceiver;


    @Override
    public void onCreate() {
        super.onCreate();

        _context = this;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServiceStartId = startId;

        processRecever(true);

        Intent bundleIntet = intent;
        if (null != bundleIntet) {
            startLockscreenActivity();
        } else {
            Log.d(TAG, TAG + " onStartCommand intent NOT existed");
        }

        setLockGuard();

        startForeground();

        return LockscreenService.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onDestroy() {

        processRecever(false);
        setStandardKeyguardState(true);
    }


    private void processRecever(boolean isRegister) {

        mLockscreenReceiver = new MyReceiver();

        if (isRegister) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mLockscreenReceiver, filter);
        } else {
            if (null != mLockscreenReceiver) {
                unregisterReceiver(mLockscreenReceiver);
            }
        }
    }

    private void setLockGuard() {

        initKeyguardService();

        if (!LockscreenUtil.getInstance(_context).isStandardKeyguardState()) {
            setStandardKeyguardState(false);
        } else {
            setStandardKeyguardState(true);
        }
    }


    private void initKeyguardService() {

        if (null != _keyManager) {
            _keyManager = null;
        }

        _keyManager = (KeyguardManager) getSystemService(_context.KEYGUARD_SERVICE);

        if (null != _keyManager) {
            if (null != mKeyLock) {
                mKeyLock = null;
            }
            mKeyLock = _keyManager.newKeyguardLock(_context.KEYGUARD_SERVICE);
        }
    }

    private void setStandardKeyguardState(boolean isStart) {
        if (isStart) {
            if (null != mKeyLock) {
                mKeyLock.reenableKeyguard();
            }
        } else {
            if (null != _keyManager) {
                mKeyLock.disableKeyguard();
            }
        }
    }


    // Run service in foreground so it is less likely to be killed by system
    private void startForeground() {
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(null)
                .setOngoing(true)
                .build();
        startForeground(9999, notification);
    }

    private void startLockscreenActivity() {

        //  go to new task
        Intent intent = new Intent(_context, LockScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
