package com.snz.rskj.android.bean;

import java.util.List;

/**
 *
 */

public class NewsInfo extends BaseResult {
    public NewsListInfo data;

    public class NewsListInfo {
        public String maxCursor;
        public String cursor;
        public int tops;
        public List<NewsDetailInfo> newList;
    }

    public class NewsDetailInfo {
        public int isTop;
        public String layoutType;
        public String newsId;
        public String origin;
        public String pageviews;
        public String publishTime;
        public String title;
        public List<String> imageListThumb;
    }

}
