package com.linheimx.app.linlock.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * Created by LJIAN on 2016/5/28.
 */
@Table(database = LinLockDB.class)
public class Joke extends BaseModel {


    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String title;

    @Column
    public String content;

    @Column
    public boolean isFavorite = false;



}
