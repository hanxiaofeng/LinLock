package com.linheimx.app.linlock.api.funny;

import com.linheimx.app.linlock.model.FunnyThing;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by LJIAN on 2016/5/15.
 */
public interface ApiFunny {

    @GET("http://api.laifudao.com/open/tupian.json")
    Observable<List<FunnyThing>> getFunnyData();
}
