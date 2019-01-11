package com.snz.rskj.android.view.home.bean;

import java.util.List;

public class HomeBean {
    /**
     * code : 200
     * msg : ok
     * data : [{"id":300,"vid":"2235072882","name":"6638399149907643661","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/6815e43f-0f51-4dae-96b3-37a96594d417.mp4","user_id":1,"state":1,"content":"结婚后她们就是我的朋友，跟着她们就能听到整个村子最新消息，最多的八卦，不敢走，不敢走，害怕我走她们就讨论我?","position":null,"music_title":"@C位宝宝创作的原声","add_time":1546684607,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":299,"vid":"2235071920","name":"6638395719742917902","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/aab2ada2-ad1b-4c85-8637-3dca817b88fc.mp4","user_id":1,"state":1,"content":"平安夜快乐","position":null,"music_title":"男人要有担当","add_time":1546684586,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":298,"vid":"2235071758","name":"6638382051932245252","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/3a38e7f3-c8f7-43e6-9a8f-c550e581825b.mp4","user_id":1,"state":1,"content":"#岳云鹏 #陈赫 对，你们是最胖的","position":null,"music_title":"@娱乐八卦奈小妹创作的原声","add_time":1546684565,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":297,"vid":"2235073222","name":"6638376964790947085","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/e9d0d0b9-4b4f-4823-8d4e-53c33a0b8c7a.mp4","user_id":1,"state":1,"content":"短发也可以很潮很有个性好不好#发型 #短发姑娘 ","position":null,"music_title":"Horizon-Janji","add_time":1546684562,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":296,"vid":"2235074383","name":"6638372192339889415","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/0f8e3210-bd5b-40ee-915c-ffba09e1b24a.mp4","user_id":1,"state":1,"content":"一直很开心","position":null,"music_title":"龙梅子、冷漠 - 唱一首情歌 (DJ何鹏版)","add_time":1546684560,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":295,"vid":"2235071695","name":"6638361669460299016","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/47182250-9719-4446-ab04-6a4d3cef1a1b.mp4","user_id":1,"state":1,"content":"哎呀，生个娃出来，不玩多没意思，哈哈哈?","position":null,"music_title":"@木子麻麻创作的原声","add_time":1546684557,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":294,"vid":"2235071684","name":"6638335612736769283","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/e4790544-fa3e-4cd6-ac62-78983a9fa208.mp4","user_id":1,"state":1,"content":"见到你的时候我的❤️在跳?，想要把你忘掉，怎么也做不到?","position":null,"music_title":"虎妞欧尼原创手势舞","add_time":1546684555,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":293,"vid":"2235073152","name":"6638334518556101892","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/98f91ca5-5106-4238-b7fc-55bd59a9bf51.mp4","user_id":1,"state":1,"content":"请务必盯着字幕看，老祖大型日常自恋现场！（另外艾特我拍这个的出来挨打！）#cosplay #同好召唤令","position":null,"music_title":"@银川Silver丶创作的原声","add_time":1546684553,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":292,"vid":"2235074328","name":"6638300373079559431","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/3c70d6f9-f2a9-4fe8-9945-9b213ad25d69.mp4","user_id":1,"state":1,"content":"很好\u2026","position":null,"music_title":"下坠Falling","add_time":1546684550,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0},{"id":291,"vid":"2235074314","name":"6638228036619603207","origaddr":"http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/3cc55751-43ff-43f5-9ddd-60f5ef4aca10.mp4","user_id":1,"state":1,"content":"喜欢杰哥五年以上的举爪?#声入人心  #张杰","position":null,"music_title":"@爱听歌的小哥哥创作的原声","add_time":1546684548,"share_num":0,"head_img":"111","nickname":"呆小呆","comment_num":0,"praise_num":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 300
         * vid : 2235072882
         * name : 6638399149907643661
         * origaddr : http://jdvodaepe3opu.vod.126.net/jdvodaepe3opu/6815e43f-0f51-4dae-96b3-37a96594d417.mp4
         * user_id : 1
         * state : 1
         * content : 结婚后她们就是我的朋友，跟着她们就能听到整个村子最新消息，最多的八卦，不敢走，不敢走，害怕我走她们就讨论我?
         * position : null
         * music_title : @C位宝宝创作的原声
         * add_time : 1546684607
         * share_num : 0
         * head_img : 111
         * nickname : 呆小呆
         * comment_num : 0
         * praise_num : 0
         */

        private int id;
        private String vid;
        private String name;
        private String origaddr;
        private int user_id;
        private int state;
        private String content;
        private String position;
        private String music_title;
        private int add_time;
        private int share_num;
        private String head_img;
        private String nickname;
        private int comment_num;
        private int praise_num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrigaddr() {
            return origaddr;
        }

        public void setOrigaddr(String origaddr) {
            this.origaddr = origaddr;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getMusic_title() {
            return music_title;
        }

        public void setMusic_title(String music_title) {
            this.music_title = music_title;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getShare_num() {
            return share_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(int praise_num) {
            this.praise_num = praise_num;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", vid='" + vid + '\'' +
                    ", name='" + name + '\'' +
                    ", origaddr='" + origaddr + '\'' +
                    ", user_id=" + user_id +
                    ", state=" + state +
                    ", content='" + content + '\'' +
                    ", position='" + position + '\'' +
                    ", music_title='" + music_title + '\'' +
                    ", add_time=" + add_time +
                    ", share_num=" + share_num +
                    ", head_img='" + head_img + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", comment_num=" + comment_num +
                    ", praise_num=" + praise_num +
                    '}';
        }
    }

    /**
     * code : 200
     * msg : ok
     * data : [{"
     */

}
