package com.snz.rskj.android.bean;

import java.io.Serializable;

public class LoginBean   implements Serializable{


    /**
     * code : 200
     * msg : 登陆成功
     * data : {"user_id":48,"accid":"abd18033646108","mobile":"18033646108","password":"e10adc3949ba59abbe56e057f20f883e","username":"5324","nickname":"453","realname":null,"idnumber":null,"head_img":"uploads/20190110/b5da15e10202a0fea8618b997cbdee6c.jpg","gender":0,"birth":631126860,"province":null,"provincename":null,"city":null,"cityname":null,"region":null,"regionname":null,"describe":null,"salary":null,"wxtoken":"","qqtoken":"","yxtoken":"563c2553800485fb0a09b0349cb7fe34","wbtoken":"","add_time":1546662864,"last_login_time":1547008562,"status":1,"make_friends":1,"occupationon":1,"project":1,"skill":1,"microphone":1,"golds":200,"stamps":0,"total_golds":200,"total_stamps":0,"device_token":"","first_charge":0,"wanshan":0,"platform_id":null,"update_type":0}
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
         * user_id : 48
         * accid : abd18033646108
         * mobile : 18033646108
         * password : e10adc3949ba59abbe56e057f20f883e
         * username : 5324
         * nickname : 453
         * realname : null
         * idnumber : null
         * head_img : uploads/20190110/b5da15e10202a0fea8618b997cbdee6c.jpg
         * gender : 0
         * birth : 631126860
         * province : null
         * provincename : null
         * city : null
         * cityname : null
         * region : null
         * regionname : null
         * describe : null
         * salary : null
         * wxtoken :
         * qqtoken :
         * yxtoken : 563c2553800485fb0a09b0349cb7fe34
         * wbtoken :
         * add_time : 1546662864
         * last_login_time : 1547008562
         * status : 1
         * make_friends : 1
         * occupationon : 1
         * project : 1
         * skill : 1
         * microphone : 1
         * golds : 200
         * stamps : 0
         * total_golds : 200
         * total_stamps : 0
         * device_token :
         * first_charge : 0
         * wanshan : 0
         * platform_id : null
         * update_type : 0
         */

        private int user_id;
        private String accid;
        private String mobile;
        private String password;
        private String username;
        private String nickname;
        private Object realname;
        private Object idnumber;
        private String head_img;
        private int gender;
        private int birth;
        private Object province;
        private Object provincename;
        private Object city;
        private Object cityname;
        private Object region;
        private Object regionname;
        private Object describe;
        private Object salary;
        private String wxtoken;
        private String qqtoken;
        private String yxtoken;
        private String wbtoken;
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
        private int wanshan;
        private Object platform_id;
        private int update_type;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
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

        public Object getIdnumber() {
            return idnumber;
        }

        public void setIdnumber(Object idnumber) {
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

        public int getBirth() {
            return birth;
        }

        public void setBirth(int birth) {
            this.birth = birth;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getProvincename() {
            return provincename;
        }

        public void setProvincename(Object provincename) {
            this.provincename = provincename;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getCityname() {
            return cityname;
        }

        public void setCityname(Object cityname) {
            this.cityname = cityname;
        }

        public Object getRegion() {
            return region;
        }

        public void setRegion(Object region) {
            this.region = region;
        }

        public Object getRegionname() {
            return regionname;
        }

        public void setRegionname(Object regionname) {
            this.regionname = regionname;
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

        public String getYxtoken() {
            return yxtoken;
        }

        public void setYxtoken(String yxtoken) {
            this.yxtoken = yxtoken;
        }

        public String getWbtoken() {
            return wbtoken;
        }

        public void setWbtoken(String wbtoken) {
            this.wbtoken = wbtoken;
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

        public int getWanshan() {
            return wanshan;
        }

        public void setWanshan(int wanshan) {
            this.wanshan = wanshan;
        }

        public Object getPlatform_id() {
            return platform_id;
        }

        public void setPlatform_id(Object platform_id) {
            this.platform_id = platform_id;
        }

        public int getUpdate_type() {
            return update_type;
        }

        public void setUpdate_type(int update_type) {
            this.update_type = update_type;
        }
    }
}
