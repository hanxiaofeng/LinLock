package com.linheimx.app.linlock.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by LJIAN on 2016/6/24.
 */
@Table(database = LinLockDB.class)
public class Pic extends BaseModel {


    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String content;
    @Column
    public String hashId;
    @Column
    public String unixtime;
    @Column
    public String url;

}
