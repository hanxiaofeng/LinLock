package com.linheimx.app.linlock.api.joke;

import java.util.List;

/**
 * Created by LJIAN on 2016/6/24.
 */
public class JokeResponse {


    /**
     * reason : success
     * result : [{"content":"妹子为了拍照够拼","hashId":"D0D7A81D266A81C930E7D616CB758F8C","unixtime":"1426385555","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/D0D7A81D266A81C930E7D616CB758F8C.jpg"},{"content":"妹子别着急，我来救你了","hashId":"D3AE5F351B0B1FCC4F90665BB1B6A344","unixtime":"1426385555","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/D3AE5F351B0B1FCC4F90665BB1B6A344.jpg"},{"content":"这又是哪个熊孩子","hashId":"9CC447D499B2F87D0419F478517AD56C","unixtime":"1426385564","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/9CC447D499B2F87D0419F478517AD56C.jpg"},{"content":"这么任性，有电视信号么","hashId":"AAF2B6AF8C1B9D1BFA5D1F9B122DAE5F","unixtime":"1426385564","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/AAF2B6AF8C1B9D1BFA5D1F9B122DAE5F.jpg"},{"content":"给你们科普一下男左女右的由来","hashId":"819680452203866D372181517D9A1AA9","unixtime":"1426386150","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/819680452203866D372181517D9A1AA9.jpg"},{"content":"妹子你的形象呢","hashId":"5C31DFA3A73F99194EF9CE3AE68404A5","unixtime":"1426386164","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/5C31DFA3A73F99194EF9CE3AE68404A5.jpg"},{"content":"此路是我开，此树是我栽","hashId":"C17317BF2B279AFE81EA229023CD94E0","unixtime":"1426386845","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/C17317BF2B279AFE81EA229023CD94E0.jpg"},{"content":"现在的商店啥的都拜他了","hashId":"9212583B220B7846F7FC1B1FA4CF0EB3","unixtime":"1426387356","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/9212583B220B7846F7FC1B1FA4CF0EB3.jpg"},{"content":"紫薇，你还认识我吗？","hashId":"F171AC27F5EC7896A31246EC7C737CC4","unixtime":"1426387360","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/F171AC27F5EC7896A31246EC7C737CC4.jpg"},{"content":"萌妹子专驾","hashId":"16051BA37F4F3B82C7B843513C1EE1BC","unixtime":"1426387947","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/16051BA37F4F3B82C7B843513C1EE1BC.jpg"}]
     * error_code : 0
     */

    public String reason;
    public int error_code;
    /**
     * content : 妹子为了拍照够拼
     * hashId : D0D7A81D266A81C930E7D616CB758F8C
     * unixtime : 1426385555
     * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/15/D0D7A81D266A81C930E7D616CB758F8C.jpg
     */

    public List<ResultEntity> result;

    public static class ResultEntity {
        public String content;
        public String hashId;
        public String unixtime;
        public String url;
    }
}
