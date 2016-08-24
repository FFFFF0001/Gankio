package com.mifind.gankio.conf;

/**
 * Created by Xuanjiawei on 2016/8/4.
 */
public class Conf {
    /**
     搜索API1- http://gank.io/api/search/query/listview/category/App/count/10/page/1
     category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App • count 最大 50

     2- 获取某几日干货网站数据: http://gank.io/api/history/content/2/1
     注： 2 代表 2 个数据，1 代表：取第一页数据
     获取特定日期网站数据: http://gank.io/api/history/content/day/2016/05/11
     获取发过干货日期接口: http://gank.io/api/day/history 方式 GET
     */
    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * •数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * •请求个数： 数字，大于0
     * •第几页：数字，大于0
     * 例： •http://gank.io/api/data/Android/10/1
     * •http://gank.io/api/data/福利/10/1
     * •http://gank.io/api/data/iOS/20/2
     * •http://gank.io/api/data/all/20/2
     */

    //    每日数据： http://gank.io/api/day/年/月/日
    //    例：•http://gank.io/api/day/2015/08/06

    public static String RequestAll(int num, int index) {
        return "http://gank.io/api/data/all/" + num + "/" + index;
    }

    public static String RequestAboutApp() {
        return "http://fir.im/7qjd";
    }

    public static String RequestBlog() {
        return "https://mifind.github.io/";
    }
    
    public static String RequestFuli(int num ,int index){
        return "http://gank.io/api/data/福利/" + num + "/" + index;
    }
    
    public static String RequestAndroid(int num ,int index){
        return "http://gank.io/api/data/Android/" + num + "/" + index;
    }

    public static String RequestiOS(int num ,int index){
        return "http://gank.io/api/data/iOS/" + num + "/" + index;
    }
    public static String RequestVideo(int num ,int index){
        return "http://gank.io/api/data/休息视频/" + num + "/" + index;
    }
    public static String RequestFront(int num ,int index){
        return "http://gank.io/api/data/前端/" + num + "/" + index;
    }

    public static String RequestExpand(int num ,int index){
        return "http://gank.io/api/data/拓展资源/" + num + "/" + index;
    }

    public static String RequestRest(int num ,int index){
        return "http://gank.io/api/data/休息视频/" + num + "/" + index;
    }

    public static String RequestApp(int num ,int index){
        return "http://gank.io/api/data/App/" + num + "/" + index;
    }

    public static String RequestRecommond(int num ,int index){
        return "http://gank.io/api/data/瞎推荐/" + num + "/" + index;
    }

}
