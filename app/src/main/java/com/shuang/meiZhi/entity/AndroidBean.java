package com.shuang.meiZhi.entity;

import java.util.List;

/**
 * @author feng
 * @Description:
 * @date 2017/3/23
 */
public class AndroidBean {

    /**
     * error : false
     * results : [{"_id":"58d1eae9421aa90f131786b0","createdAt":"2017-03-22T11:09:29.904Z","desc":"Android Picture in Picture 效果 Demo。","images":["http://img.gank.io/41541b3b-4d98-44e3-a595-f47567544a82"],"publishedAt":"2017-03-22T11:47:09.555Z","source":"chrome","type":"Android","url":"https://github.com/googlesamples/android-PictureInPicture","used":true,"who":"带马甲"},{"_id":"58d1eb7b421aa95810795caf","createdAt":"2017-03-22T11:11:55.134Z","desc":"Android SpringAnimation 安卓模拟弹簧动画。","images":["http://img.gank.io/a74c3e42-1801-48c8-acb3-c7a68b30c96a"],"publishedAt":"2017-03-22T11:47:09.555Z","source":"chrome","type":"Android","url":"http://www.jianshu.com/p/870334f2e2d4","used":true,"who":"allen"},{"_id":"58d1ee83421aa90f131786b2","createdAt":"2017-03-22T11:24:51.435Z","desc":"基于 MD 实现的一些 List Item 的模板布局，可以帮助大家在开发的时候省时间。","images":["http://img.gank.io/1701f633-68db-422c-b94c-c8e20d23fe64"],"publishedAt":"2017-03-22T11:47:09.555Z","source":"chrome","type":"Android","url":"https://github.com/lurbas/ListItemView","used":true,"who":"士兵"},{"_id":"58cbf72b421aa90f13178688","createdAt":"2017-03-17T22:48:11.282Z","desc":"Android存储路径你了解多少？","images":["http://img.gank.io/f58881ff-000f-4e7e-8b72-25112be5b830"],"publishedAt":"2017-03-21T12:19:46.895Z","source":"web","type":"Android","url":"http://itfeifei.win/2017/03/17/Android%E5%AD%98%E5%82%A8%E8%B7%AF%E5%BE%84%E4%BD%A0%E4%BA%86%E8%A7%A3%E5%A4%9A%E5%B0%91/","used":true,"who":null},{"_id":"58cca37b421aa95810795c86","createdAt":"2017-03-18T11:03:23.757Z","desc":"Android TensorFlow MachineLearning Example (Building TensorFlow for Android)","images":["http://img.gank.io/75f15c11-3fc0-4426-ac3b-d91352d3afd0","http://img.gank.io/76ef2be4-e495-4f72-9809-334d288ebfda"],"publishedAt":"2017-03-21T12:19:46.895Z","source":"web","type":"Android","url":"https://github.com/MindorksOpenSource/AndroidTensorFlowMachineLearningExample","used":true,"who":"AMIT SHEKHAR"},{"_id":"58ccbcdb421aa90f03345199","createdAt":"2017-03-18T12:51:39.452Z","desc":"加快apk的构建速度，如何把编译时间从130秒降到17秒","images":["http://img.gank.io/4330cd9f-1a6e-464a-8f15-3705f77b73b1"],"publishedAt":"2017-03-21T12:19:46.895Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/53923d8f241c","used":true,"who":"tong"},{"_id":"58cf5757421aa90f1317869b","createdAt":"2017-03-20T12:15:19.335Z","desc":"一文让你明白Java字节码","publishedAt":"2017-03-21T12:19:46.895Z","source":"web","type":"Android","url":"http://url.cn/46JozfM","used":true,"who":"陈宇明"},{"_id":"58cf86ff421aa90efc4fb6fd","createdAt":"2017-03-20T15:38:39.903Z","desc":"可能是最优雅的切换布局的方法[android控件]","images":["http://img.gank.io/167f1978-db87-4d7d-9161-353d1ea8a48c"],"publishedAt":"2017-03-21T12:19:46.895Z","source":"web","type":"Android","url":"https://github.com/fingdo/stateLayout","used":true,"who":null},{"_id":"58d07f14421aa95810795ca0","createdAt":"2017-03-21T09:17:08.978Z","desc":"美团的新一代热更新系统Robust，对Android版本无差别兼容。无需发版就可以做到随时修改线上bug，快速对重大线上问题作出反应，补丁修补成功率高达99.9%。","publishedAt":"2017-03-21T12:19:46.895Z","source":"web","type":"Android","url":"https://github.com/Meituan-Dianping/Robust","used":true,"who":"JackHang"},{"_id":"58d08f0e421aa90f033451ab","createdAt":"2017-03-21T10:25:18.343Z","desc":"React Native MD 风格的 Snackbar","publishedAt":"2017-03-21T12:19:46.895Z","source":"chrome","type":"Android","url":"https://github.com/uphold-forks/react-native-snackbar","used":true,"who":"带马甲"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 58d1eae9421aa90f131786b0
         * createdAt : 2017-03-22T11:09:29.904Z
         * desc : Android Picture in Picture 效果 Demo。
         * images : ["http://img.gank.io/41541b3b-4d98-44e3-a595-f47567544a82"]
         * publishedAt : 2017-03-22T11:47:09.555Z
         * source : chrome
         * type : Android
         * url : https://github.com/googlesamples/android-PictureInPicture
         * used : true
         * who : 带马甲
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
