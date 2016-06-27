package com.linheimx.app.linlock.util;

import android.content.Context;
import android.content.Intent;

import com.linheimx.app.linlock.service.LockscreenService;


public class SwitchLockUtil {
    private Context _context = null;
    public static final String ISSOFTKEY = "ISSOFTKEY";
    public static final String ISLOCK = "ISLOCK";
    private static SwitchLockUtil mSwitchInstance;

    public static SwitchLockUtil getInstance(Context context) {
        if (mSwitchInstance == null) {
            mSwitchInstance = new SwitchLockUtil(context);
        }
        return mSwitchInstance;
    }

    private SwitchLockUtil(Context context) {
        _context = context;
    }


    public void startLockscreenService() {
        Intent intent = new Intent(_context, LockscreenService.class);
//        startLockscreenIntent.putExtra(LockscreenService.LOCKSCREENSERVICE_FIRST_START, true);
        _context.startService(intent);
    }

    public void stopLockscreenService() {
//        Intent stopLockscreenViewIntent =  new Intent(_context, LockscreenViewService.class);
//        _context.stopService(stopLockscreenViewIntent);
        Intent intent = new Intent(_context, LockscreenService.class);
        _context.stopService(intent);
    }
}
