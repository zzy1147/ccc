package com.snz.rskj.android.bean;
import java.io.Serializable;
import java.util.List;

/**
 *
 */

public class ChannelInfo extends BaseResult implements Serializable {
    public ChannelCenter data;
    public class ChannelCenter{
        public List<ChannelInner> newsChannelList;
    }
    public class ChannelInner{
        public String channelId;
        public String channelName;
    }
}
