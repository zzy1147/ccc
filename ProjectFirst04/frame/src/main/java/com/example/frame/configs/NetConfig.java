package com.example.frame.configs;

public class NetConfig {
    public static int apiType = 5;
    public static String BASE_URL;

    static {
        if (apiType == 1) {
            BASE_URL = "http://www.demo.com/thinkphp_5.0/public/index.php/index/index/";

        } else if (apiType == 2) {
            BASE_URL = "http://8080:192.168.1.113/thinkphp_5.0/public/index.php/index/index/";
        } else if (apiType == 3) {
            BASE_URL = "https://www.firstgainfo.com/firstga/app/";
        } else if (apiType == 4) {
            BASE_URL = "http://c.m.163.com/";

        }else if (apiType==5){
            //测试专用
            BASE_URL="http://zgztest.com/";
        }
    }
}
