package com.xmw.qiyun.util.check;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class IdCardUtil {

    private final static Map<Integer, String> zoneNum = new HashMap<>();

    static {
        zoneNum.put(11, "北京");
        zoneNum.put(12, "天津");
        zoneNum.put(13, "河北");
        zoneNum.put(14, "山西");
        zoneNum.put(15, "内蒙古");
        zoneNum.put(21, "辽宁");
        zoneNum.put(22, "吉林");
        zoneNum.put(23, "黑龙江");
        zoneNum.put(31, "上海");
        zoneNum.put(32, "江苏");
        zoneNum.put(33, "浙江");
        zoneNum.put(34, "安徽");
        zoneNum.put(35, "福建");
        zoneNum.put(36, "江西");
        zoneNum.put(37, "山东");
        zoneNum.put(41, "河南");
        zoneNum.put(42, "湖北");
        zoneNum.put(43, "湖南");
        zoneNum.put(44, "广东");
        zoneNum.put(45, "广西");
        zoneNum.put(46, "海南");
        zoneNum.put(50, "重庆");
        zoneNum.put(51, "四川");
        zoneNum.put(52, "贵州");
        zoneNum.put(53, "云南");
        zoneNum.put(54, "西藏");
        zoneNum.put(61, "陕西");
        zoneNum.put(62, "甘肃");
        zoneNum.put(63, "青海");
        zoneNum.put(64, "宁夏");
        zoneNum.put(65, "新疆");
        zoneNum.put(71, "台湾");
        zoneNum.put(81, "香港");
        zoneNum.put(82, "澳门");
        zoneNum.put(91, "国外");
    }

    private final static int[] INTS = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    private final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 身份证号是否基本有效
     *
     * @param s 号码内容
     * @return 是否有效，null和""都是false
     */
    public static boolean isIdCard(String s) {
        if (s == null || (s.length() != 15 && s.length() != 18))
            return false;
        final char[] cs = s.toUpperCase().toCharArray();
        // （1）校验位数
        int power = 0;
        for (int i = 0; i < cs.length; i++) {// 循环比正则表达式更快
            if (i == cs.length - 1 && cs[i] == 'X')
                break;// 最后一位可以是X或者x
            if (cs[i] < '0' || cs[i] > '9')
                return false;
            if (i < cs.length - 1)
                power += (cs[i] - '0') * POWER_LIST[i];
        }
        // （2）校验区位码
        if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
            return false;
        }
        // （3）校验年份
        String year = s.length() == 15 ? "19" + s.substring(6, 8) : s.substring(6, 10);
        final int iYear = Integer.parseInt(year);
        if (iYear < 1900 || iYear > Calendar.getInstance().get(Calendar.YEAR)) {
            return false;// 1900年的PASS，超过今年的PASS
        }
        // （4）校验月份
        String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10, 12);
        final int iMonth = Integer.parseInt(month);
        if (iMonth < 1 || iMonth > 12)
            return false;
        // （5）校验天数
        String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12, 14);
        final int iDay = Integer.parseInt(day);
        if (iDay < 1 || iDay > 31)
            return false;
        // （6）校验“校验码”
        return s.length() == 15 || cs[cs.length - 1] == INTS[power % 11];
    }
}
