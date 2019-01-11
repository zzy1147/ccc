package com.snz.rskj.android.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 输入验证
 *
 * @author Administrator
 */
public class Verify {

    /**
     * 判断邮箱格式
     */
    public static boolean verifyEmailLast(String str) {//@＠

        //String check = "^[a-zA-Z0-9]+([\\.\\_\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
        String check = "^[a-zA-Z0-9]+([\\.\\_\\-]*[a-z0-9])*@([a-zA-Z0-9]+[\\.\\_\\-]*[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z]{2,3}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }

    /**
     * 判断邮箱格式
     */
    public static boolean verifyEmail(String str) {//@＠

        //String check = "^[a-zA-Z0-9]+([\\.\\_\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
        String check = "^[a-zA-Z0-9]+([\\.\\_\\-]*[a-z0-9])*@([a-zA-Z0-9]+[\\.\\_\\-]*[-a-zA-Z0-9]*[a-zA-Z0-9]+.){1,63}[a-zA-Z]{2,3}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        if (matcher.matches()) {
            String[] lastPatchs = str.split("\\.");
            String lastPatch = lastPatchs[lastPatchs.length - 1];
            if (lastPatch.length() < 2 || lastPatch.length() > 3) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    /**
     * 校验座机号
     */
    public static boolean verifyTelephonenumber(String telephonenumber) {
        Pattern p = Pattern.compile("[0]{1}[0-9]{2,3}-[0-9]{7,8}");
        Matcher m = p.matcher(telephonenumber);
        return m.matches();
    }

    /**
     * 检测是否含有 数字
     */
    public static boolean verifyIsHasStandard(String standard) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(standard);
        if (m.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检测是不能为纯数字并且字符串长度必须大于等于6且小于 20(是否符合标准)
     */
    public static boolean verifyIsStandard(String standard) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(standard);
        return !m.matches() && 6 <= standard.length() && standard.length() <= 16;
    }

    public static boolean verifyIsStandard1(String standard) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(standard);
        return m.matches();
    }

    /**
     * 检测是不能为纯字母并且字符串长度必须大于等于6且小于 20(是否符合标准)
     */
    public static boolean verifyIsChar(String standard) {
        Pattern p = Pattern.compile("[a-zA-Z]*");
        Matcher m = p.matcher(standard);
        return !m.matches() && 6 <= standard.length() && standard.length() <= 16;
    }

    /*检测是否含有字母*/
    public static boolean verifyIsHasChar(String standard) {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(standard);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 检测是否含有汉字
     */
    public static boolean verifyIsHan(String standard) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(standard);
        if (m.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否含有特殊字符
     */
    public static boolean verifyIsSpecial(String standard) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？《》·]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(standard);
        if (m.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否含有中文特殊字符
     */
    public static boolean verifyIsChineseSpecial(String standard) {
        String regEx = "[·！……（）——【】：；“”‘’、，。《》？、]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(standard);
        if (m.find()) {
            return false;
        }
        return true;
    }

    /**
     * 检测是否含有空格
     */
    public static boolean hasKongge(String standard) {
        int i = standard.indexOf(" ");
        if (i == -1) return true;
        return false;
    }

    //检测密码包含数字和字母以外字符
    public Boolean checkPassWord(String str) {
        Pattern p_str = Pattern.compile("[^[\\u4e00-\\u9fa5]]+");
        Matcher m = p_str.matcher(str);
        return m.find() && m.group(0).equals(str);
    }

    /**
     * 检测必须是纯数字且长度大于0
     */
    public static boolean verifyIsNumber(String number) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(number);
        return m.matches() && number.length() > 0;
    }

    /**
     * 验证邮政编码
     *
     * @param post
     * @return
     */
    public static boolean checkPost(String post) {
        return post.matches("[1-9]\\d{5}(?!\\d)");
    }

    /**
     * 校验手机号
     */
    public static boolean verifyPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            // Pattern p =
            // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
            Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNumber);
            return m.matches();
        }
    }

    /**
     * 校验手机号
     */
    public static boolean verifyPhoneNumber2(String phoneNumber) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 校验手机号
     */
    public static boolean verifyPhoneNumber3(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return true;
        } else {
            // Pattern p =
            // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
            Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNumber);
            return m.matches();
        }
    }

    /**
     * 校验传真号
     */
    public static boolean istel(String phoneNumber) {
        // Pattern p =
        // Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0,5-9]))\\d{8}$");
        Pattern p = Pattern.compile("/^[+]{0,1}(d){1,3}[ ]?([-]?((d)|[ ]){1,12})+$/");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 校验公司简称
     */
    public static boolean verifyCompanyShortName(String str) {//@＠

        String check = "^[A-Za-z0-9\u4E00-\u9FA5()（）]*$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }

    /**
     * 校验公司全称
     */
    public static boolean verifyCompanyFullName(String str) {//@＠

        String check = "^[A-Za-z0-9\u4E00-\u9FA5()（）]*$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }

    /**
     * 校验公司全称
     */
    public static boolean verifyCompanyFullName1(String str) {//@＠

        String check = "^[\u4E00-\u9FA5()（）]*$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }

    //java中获得字节长度
    public int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;
    }

    //用正则表达式判断字节长度
    public int getWordCount1(String s) {
        s = s.replaceAll(" [^\\x00-\\xff] ", " ** ");
        int length = s.length();
        return length;
    }

    public static int getChineseNum1(String s) {
        int count = 0;
        Pattern p = Pattern.compile("[\u4E00-\u9FA5]");
        Matcher m = p.matcher(s);
        while (m.find()) {
            count++;
        }
        return count;
    }

    //匹配双字节字符(包括汉字在内)：[^\x00-\xff]
    public static int getChineseNum(String s) {
        int count = 0;
        Pattern p = Pattern.compile("[^\\x00-\\xff]");
        Matcher m = p.matcher(s);
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static int getStringLength(String s) {
        int length = 0;
        length = s.length() + getChineseNum(s);
        return length;
    }

    //判断字符串是否全为空格
    public static boolean isAllKong(String s) {
        s = s.replaceAll(" ", "1");
        int count = 0;
        Pattern p = Pattern.compile("[1]");
        Matcher m = p.matcher(s);
        while (m.find()) {
            count++;
        }
        if (s.length() == count) {
            return false;
        }
        return true;
    }

    //军官证
    public static boolean verifyJunGuan(String str) {//@＠

        String check = "[\\d\\u4E00-\\u9FA5]{1,20}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }

    //护照
    public static boolean verifyHuZhao(String str) {//@＠
        String check ="^[a-zA-Z0-9]{1,10}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }

    //身份证
    public static boolean verifyShenFenZheng(String str) {//@＠
        String check = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);

        return matcher.matches();
    }
}
