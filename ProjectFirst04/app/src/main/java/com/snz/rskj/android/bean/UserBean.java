package com.snz.rskj.android.bean;

public class UserBean {

    /**
     * code : 200
     * msg : 登陆成功
     * data : {"user_id":31,"mobile":"13661358999","password":"4297f44b13955235245b2497399d7a93","username":"555","nickname":"呆五呆","realname":null,"idnumber":"","head_img":"","gender":0,"birth":null,"region":null,"describe":null,"salary":null,"wxtoken":"","qqtoken":"","wbtoken":"","yxtoken":"207a8f638be3ae27cfbc8cc70cc5ba48","add_time":1545969302,"last_login_time":1545969302,"status":1,"make_friends":1,"occupationon":1,"project":1,"skill":1,"microphone":1,"golds":0,"stamps":40,"total_golds":300,"total_stamps":100,"device_token":"","first_charge":0}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 31
         * mobile : 13661358999
         * password : 4297f44b13955235245b2497399d7a93
         * username : 555
         * nickname : 呆五呆
         * realname : null
         * idnumber :
         * head_img :
         * gender : 0
         * birth : null
         * region : null
         * describe : null
         * salary : null
         * wxtoken :
         * qqtoken :
         * wbtoken :
         * yxtoken : 207a8f638be3ae27cfbc8cc70cc5ba48
         * add_time : 1545969302
         * last_login_time : 1545969302
         * status : 1
         * make_friends : 1
         * occupationon : 1
         * project : 1
         * skill : 1
         * microphone : 1
         * golds : 0
         * stamps : 40
         * total_golds : 300
         * total_stamps : 100
         * device_token :
         * first_charge : 0
         */

        private int user_id;
        private String mobile;
        private String password;
        private String username;
        private String nickname;
        private Object realname;
        private String idnumber;
        private String head_img;
        private int gender;
        private Object birth;
        private Object region;
        private Object describe;
        private Object salary;
        private String wxtoken;
        private String qqtoken;
        private String wbtoken;
        private String yxtoken;
        private int add_time;
        private int last_login_time;
        private int status;
        private int make_friends;
        private int occupationon;
        private int project;
        private int skill;
        private int microphone;
        private int golds;
        private int stamps;
        private int total_golds;
        private int total_stamps;
        private String device_token;
        private int first_charge;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getRealname() {
            return realname;
        }

        public void setRealname(Object realname) {
            this.realname = realname;
        }

        public String getIdnumber() {
            return idnumber;
        }

        public void setIdnumber(String idnumber) {
            this.idnumber = idnumber;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public Object getBirth() {
            return birth;
        }

        public void setBirth(Object birth) {
            this.birth = birth;
        }

        public Object getRegion() {
            return region;
        }

        public void setRegion(Object region) {
            this.region = region;
        }

        public Object getDescribe() {
            return describe;
        }

        public void setDescribe(Object describe) {
            this.describe = describe;
        }

        public Object getSalary() {
            return salary;
        }

        public void setSalary(Object salary) {
            this.salary = salary;
        }

        public String getWxtoken() {
            return wxtoken;
        }

        public void setWxtoken(String wxtoken) {
            this.wxtoken = wxtoken;
        }

        public String getQqtoken() {
            return qqtoken;
        }

        public void setQqtoken(String qqtoken) {
            this.qqtoken = qqtoken;
        }

        public String getWbtoken() {
            return wbtoken;
        }

        public void setWbtoken(String wbtoken) {
            this.wbtoken = wbtoken;
        }

        public String getYxtoken() {
            return yxtoken;
        }

        public void setYxtoken(String yxtoken) {
            this.yxtoken = yxtoken;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(int last_login_time) {
            this.last_login_time = last_login_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getMake_friends() {
            return make_friends;
        }

        public void setMake_friends(int make_friends) {
            this.make_friends = make_friends;
        }

        public int getOccupationon() {
            return occupationon;
        }

        public void setOccupationon(int occupationon) {
            this.occupationon = occupationon;
        }

        public int getProject() {
            return project;
        }

        public void setProject(int project) {
            this.project = project;
        }

        public int getSkill() {
            return skill;
        }

        public void setSkill(int skill) {
            this.skill = skill;
        }

        public int getMicrophone() {
            return microphone;
        }

        public void setMicrophone(int microphone) {
            this.microphone = microphone;
        }

        public int getGolds() {
            return golds;
        }

        public void setGolds(int golds) {
            this.golds = golds;
        }

        public int getStamps() {
            return stamps;
        }

        public void setStamps(int stamps) {
            this.stamps = stamps;
        }

        public int getTotal_golds() {
            return total_golds;
        }

        public void setTotal_golds(int total_golds) {
            this.total_golds = total_golds;
        }

        public int getTotal_stamps() {
            return total_stamps;
        }

        public void setTotal_stamps(int total_stamps) {
            this.total_stamps = total_stamps;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public int getFirst_charge() {
            return first_charge;
        }

        public void setFirst_charge(int first_charge) {
            this.first_charge = first_charge;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id=" + user_id +
                    ", mobile='" + mobile + '\'' +
                    ", password='" + password + '\'' +
                    ", username='" + username + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", realname=" + realname +
                    ", idnumber='" + idnumber + '\'' +
                    ", head_img='" + head_img + '\'' +
                    ", gender=" + gender +
                    ", birth=" + birth +
                    ", region=" + region +
                    ", describe=" + describe +
                    ", salary=" + salary +
                    ", wxtoken='" + wxtoken + '\'' +
                    ", qqtoken='" + qqtoken + '\'' +
                    ", wbtoken='" + wbtoken + '\'' +
                    ", yxtoken='" + yxtoken + '\'' +
                    ", add_time=" + add_time +
                    ", last_login_time=" + last_login_time +
                    ", status=" + status +
                    ", make_friends=" + make_friends +
                    ", occupationon=" + occupationon +
                    ", project=" + project +
                    ", skill=" + skill +
                    ", microphone=" + microphone +
                    ", golds=" + golds +
                    ", stamps=" + stamps +
                    ", total_golds=" + total_golds +
                    ", total_stamps=" + total_stamps +
                    ", device_token='" + device_token + '\'' +
                    ", first_charge=" + first_charge +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
