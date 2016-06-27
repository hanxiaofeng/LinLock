package com.linheimx.app.linlock.model;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by LJIAN on 2016/5/15.
 */
@Database(name = LinLockDB.NAME, version = LinLockDB.VERSION, foreignKeysSupported = true)
public class LinLockDB {

    public static final String NAME = "linlock";

    public static final int VERSION = 1;
}
