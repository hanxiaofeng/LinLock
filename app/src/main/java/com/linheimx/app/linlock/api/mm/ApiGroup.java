package com.linheimx.app.linlock.api.mm;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Mr_Wrong on 15/10/30.
 */
public interface ApiGroup {
    @Headers("User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36")
    @GET("http://www.mzitu.com//{type}/page/{page}")
    Observable<String> getGroup(@Path("type") String type, @Path("page") int page);
}
