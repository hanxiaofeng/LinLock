package com.linheimx.app.linlock.api.xhdq.random_pic;

import com.linheimx.app.linlock.model.Pic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJIAN on 2016/6/24.
 */
public class PicResponse {


    /**
     * reason : success
     * result : [{"content":"时机把握好，跑都不用跑","hashId":"15E67E2EC5E37FE7E091B84AD9E1D1E9","unixtime":"1423212783","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/15E67E2EC5E37FE7E091B84AD9E1D1E9.gif"},{"content":"一不小心就被砸底下","hashId":"96235306BBBA44977C1110565CAC2B11","unixtime":"1423212817","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/96235306BBBA44977C1110565CAC2B11.jpg"},{"content":"妹子们特殊的拍照姿势","hashId":"166A35C46A94931FC5E5D3175A4DA924","unixtime":"1423213363","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/166A35C46A94931FC5E5D3175A4DA924.jpg"},{"content":"这手机真是太流了","hashId":"B37CB906890DB3D4CA56ED56AD3A5391","unixtime":"1423214102","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/B37CB906890DB3D4CA56ED56AD3A5391.jpg"},{"content":"善变的老师","hashId":"E319AD5D0A60C62810670F0014C1A3F1","unixtime":"1423214115","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/E319AD5D0A60C62810670F0014C1A3F1.jpg"},{"content":"骚年，说说你看到什么了","hashId":"7FBE6430F227C05040F221A27CF32833","unixtime":"1423214115","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/7FBE6430F227C05040F221A27CF32833.jpg"},{"content":"这是打算去遛牛嘛","hashId":"5DD771F491CB8EB1D3FFF5203FB0DC14","unixtime":"1423214675","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/5DD771F491CB8EB1D3FFF5203FB0DC14.jpg"},{"content":"你居然说了实话！！！！","hashId":"CE2D40E51E67BA25411AD391694B0151","unixtime":"1423214675","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/CE2D40E51E67BA25411AD391694B0151.jpg"},{"content":"刚才在百。度首页上看见了一个笑话，觉的不错，想和冷友分享一下，然后就发到冷兔上了。然后我继续看。就发现！！！！！","hashId":"86F1BE5D97AB42172B3FAE15E9AE1580","unixtime":"1423215775","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/86F1BE5D97AB42172B3FAE15E9AE1580.jpg"},{"content":"挨揍大赛","hashId":"A3426B534AC4F426FFA57EBA2B2E5199","unixtime":"1423215787","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/A3426B534AC4F426FFA57EBA2B2E5199.gif"}]
     * error_code : 0
     */

    public String reason;
    public int error_code;
    /**
     * content : 时机把握好，跑都不用跑
     * hashId : 15E67E2EC5E37FE7E091B84AD9E1D1E9
     * unixtime : 1423212783
     * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201502/06/15E67E2EC5E37FE7E091B84AD9E1D1E9.gif
     */

    public List<ResultEntity> result;

    public static class ResultEntity {
        public String content;
        public String hashId;
        public String unixtime;
        public String url;
    }


    public List<Pic> Result2Pic() {

        List<Pic> list = new ArrayList<>();
        for (ResultEntity entity : result) {
            Pic pic = new Pic();
            pic.content = entity.content;
            pic.url = entity.url;
            pic.unixtime = entity.unixtime;
            pic.hashId = entity.hashId;
            list.add(pic);
        }

        return list;
    }
}
