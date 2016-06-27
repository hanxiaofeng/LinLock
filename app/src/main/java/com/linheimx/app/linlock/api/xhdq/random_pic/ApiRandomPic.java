package com.linheimx.app.linlock.api.xhdq.random_pic;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by LJIAN on 2016/6/24.
 */
public interface ApiRandomPic {
    @GET("http://v.juhe.cn/joke/randJoke.php?type=pic&key=8df2093715b0b28f56e74c16bbc840a2")
    Observable<PicResponse> get_OB();
}
