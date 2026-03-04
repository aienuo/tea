package com.aienuo.tea.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证号码解析工具类（支持15位或18位身份证）
 * <p>
 * 根据《中华人民共和国国家标准GB11643-1999》中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：6位数字地址码，8位数字出生日期码，3位数字顺序码和1位数字校验码。
 *
 * <p>
 * 一、地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。
 * <li>前1、2位数字表示：所在省份的代码；</li>
 * <li>第3、4位数字表示：所在城市的代码；</li>
 * <li>第5、6位数字表示：所在区县的代码；</li>
 *
 * <p>
 * 二、出生日期码，（第7位 - 14位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
 *
 * <p>
 * 三、顺序码（第15位至17位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
 * <li>第15、16位数字表示：所在地的派出所的代码；</li>
 * <li>第17位数字表示性别：奇数表示男性，偶数表示女性；</li>
 *
 * <p>
 * 四、校验码（第18位数）：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
 * <p>
 * 计算步骤：
 * <li>1.将前17位数分别乘以不同的系数。从第1位到第17位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2</li>
 * <li>2.将这17位数字和系数相乘的积相加</li>
 * <li>3.用加出来和除以11，看余数是多少</li>
 * <li>4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字，分别对应的最后一位身份证的号码为：1 0 X 9 8 7 6 5 4 3</li>
 *
 * <p>
 * 如果余数是3，则身份证的第18位数字就是9．如果余数是2，则身份证的第18位号码就是x．若某人的身份证号码的前17位依次是11010219600302011，则他身份证号码的第18位数字是3
 */
@Slf4j
public class IdCardUtils {

    final static Map<Integer, String> ZONE_NUM = new HashMap<>();
    final static int[] PARITY_BIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    static {
        ZONE_NUM.put(11, "北京");
        ZONE_NUM.put(12, "天津");
        ZONE_NUM.put(13, "河北");
        ZONE_NUM.put(14, "山西");
        ZONE_NUM.put(15, "内蒙古");
        ZONE_NUM.put(21, "辽宁");
        ZONE_NUM.put(22, "吉林");
        ZONE_NUM.put(23, "黑龙江");
        ZONE_NUM.put(31, "上海");
        ZONE_NUM.put(32, "江苏");
        ZONE_NUM.put(33, "浙江");
        ZONE_NUM.put(34, "安徽");
        ZONE_NUM.put(35, "福建");
        ZONE_NUM.put(36, "江西");
        ZONE_NUM.put(37, "山东");
        ZONE_NUM.put(41, "河南");
        ZONE_NUM.put(42, "湖北");
        ZONE_NUM.put(43, "湖南");
        ZONE_NUM.put(44, "广东");
        ZONE_NUM.put(45, "广西");
        ZONE_NUM.put(46, "海南");
        ZONE_NUM.put(50, "重庆");
        ZONE_NUM.put(51, "四川");
        ZONE_NUM.put(52, "贵州");
        ZONE_NUM.put(53, "云南");
        ZONE_NUM.put(54, "西藏");
        ZONE_NUM.put(61, "陕西");
        ZONE_NUM.put(62, "甘肃");
        ZONE_NUM.put(63, "青海");
        ZONE_NUM.put(64, "宁夏");
        ZONE_NUM.put(65, "新疆");
        ZONE_NUM.put(71, "台湾");
        ZONE_NUM.put(81, "香港");
        ZONE_NUM.put(82, "澳门");
        ZONE_NUM.put(91, "外国");
    }

    /**
     * 身份证号码长度
     */
    private final static int ID_CARD_15 = 15;
    private final static int ID_CARD_18 = 18;
    /**
     * 魔法值常量数
     */
    private final static int NUMBER_0 = 0;
    private final static int NUMBER_1 = 1;
    private final static int NUMBER_2 = 2;
    private final static int NUMBER_3 = 3;
    private final static int YEAR_START_NUMBER = 1900;
    private final static int MONTH_NUMBER = 12;
    private final static int DAY_OF_MONTH_NUMBER = 31;

    /**
     * 校验身份证信息是否正确
     *
     * @param idCardNumber - 身份证号码
     * @return 是否有效 null和 "" 都是false
     */
    public static Boolean isIdCard(final String idCardNumber) {
        if (idCardNumber == null || (idCardNumber.length() != ID_CARD_15 && idCardNumber.length() != ID_CARD_18)) {
            return false;
        }
        // 统一将 最后一位 X 转成 大写
        final char[] cs = idCardNumber.toUpperCase().toCharArray();
        final int length = cs.length;
        // 校验位数
        int power = NUMBER_0;
        for (int i = NUMBER_0; i < length; i++) {
            if (i == length - 1 && cs[i] == 'X') {
                // 最后一位可以 是 X 或 x
                break;
            }
            if (cs[i] < '0' || cs[i] > '9') {
                return false;
            }
            if (i < length - 1) {
                power += (cs[i] - '0') * POWER_LIST[i];
            }
        }
        // 校验区位码
        if (!ZONE_NUM.containsKey(Integer.valueOf(idCardNumber.substring(NUMBER_0, NUMBER_2)))) {
            return false;
        }
        // 校验年份
        final Integer intYear = getYearByIdCard(idCardNumber);
        if (intYear == null || intYear < YEAR_START_NUMBER || intYear > Calendar.getInstance().get(Calendar.YEAR)) {
            // 1900年的 PASS，超过今年的 PASS
            return false;
        }
        // 校验月份
        final Integer intMonth = getMonthByIdCard(idCardNumber);
        if (intMonth == null || intMonth < NUMBER_1 || intMonth > MONTH_NUMBER) {
            return false;
        }
        // 校验天数
        final Integer intDay = getDateByIdCard(idCardNumber);
        if (intDay == null || intDay < NUMBER_1 || intDay > DAY_OF_MONTH_NUMBER) {
            return false;
        }
        // 校验 "校验码"
        if (idCardNumber.length() == ID_CARD_15) {
            return true;
        }
        return cs[cs.length - NUMBER_1] == PARITY_BIT[power % 11];
    }

    /**
     * 解析身份证获取地址区域编码
     *
     * @param idCardNumber - 身份证号码
     * @return Integer - 区域码
     */
    public static Integer getAreaCode(final String idCardNumber) {
        if (idCardNumber != null && !idCardNumber.isEmpty()) {
            String areaString = idCardNumber.substring(NUMBER_0, 6);
            return Integer.parseInt(areaString);
        }
        return null;
    }

    /**
     * 判断来源地址是否属于目标地址
     *
     * @param sourceCode - 来源地址
     * @param targetCode - 目标地址
     * @param level      - 级别（1：省 2：市  3：县）
     * @return Boolean
     */
    public static Boolean isBelong(final String sourceCode, final String targetCode, final Integer level) {
        if (sourceCode != null && !sourceCode.isEmpty() && targetCode != null && !targetCode.isEmpty()) {
            if (level.equals(NUMBER_1)) {
                return sourceCode.substring(NUMBER_0, NUMBER_2).equals(targetCode.substring(NUMBER_0, NUMBER_2));
            }
            if (level.equals(NUMBER_2)) {
                return sourceCode.substring(NUMBER_0, 4).equals(targetCode.substring(NUMBER_0, 4));
            }
            if (level.equals(NUMBER_3)) {
                return sourceCode.substring(NUMBER_0, 6).equals(targetCode.substring(NUMBER_0, 6));
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 解析身份证号码获取性别
     *
     * @param idCardNumber - 身份证号码
     * @return Integer - 0-女；1-男；2-未知
     */
    public static Integer getSex(final String idCardNumber) {
        if (idCardNumber != null && !idCardNumber.isEmpty()) {
            String sex = idCardNumber.substring(12, 15);
            if (idCardNumber.length() == ID_CARD_18) {
                sex = idCardNumber.substring(16, 17);
            }
            if (Integer.parseInt(sex) % NUMBER_2 != NUMBER_0) {
                return NUMBER_1;
            } else {
                return NUMBER_0;
            }
        }
        // 0-女；1-男；2-未知
        return 2;
    }

    /**
     * 解析身份证号码获取年龄
     *
     * @param idCardNumber - 身份证号码
     * @return Integer - 年龄
     */
    public static Integer getAge(final String idCardNumber) {
        if (idCardNumber != null && !idCardNumber.isEmpty()) {
            return getBirthByIdCard(idCardNumber).until(LocalDate.now()).getYears();
        }
        return 0;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCardNumber - 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthStringByIdCard(final String idCardNumber) {
        if (idCardNumber != null && !idCardNumber.isEmpty()) {
            String birthday = idCardNumber.substring(6, 14);
            if (idCardNumber.length() == ID_CARD_15) {
                birthday = "19" + idCardNumber.substring(6, 12);
            }
            return birthday;
        }
        return null;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCardNumber - 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static LocalDate getBirthByIdCard(final String idCardNumber) {
        if (idCardNumber != null && !idCardNumber.isEmpty()) {
            String birthday = getBirthStringByIdCard(idCardNumber);
            if (birthday != null && birthday.length() == 8) {
                try {
                    // 防止恶意日期
                    return LocalDate.of(Integer.parseInt(birthday.substring(0, 4)), Integer.parseInt(birthday.substring(4, 6)), Integer.parseInt(birthday.substring(6, 8)));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCardNumber - 身份编号
     * @return 生日(yyyy)
     */
    public static Integer getYearByIdCard(final String idCardNumber) {
        LocalDate localDate = getBirthByIdCard(idCardNumber);
        if (localDate != null) {
            return localDate.getYear();
        }
        return null;
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCardNumber - 身份编号
     * @return 生日(MM)
     */
    public static Integer getMonthByIdCard(final String idCardNumber) {
        LocalDate localDate = getBirthByIdCard(idCardNumber);
        if (localDate != null) {
            return localDate.getMonthValue();
        }
        return null;
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCardNumber - 身份编号
     * @return 生日(dd)
     */
    public static Integer getDateByIdCard(final String idCardNumber) {
        LocalDate localDate = getBirthByIdCard(idCardNumber);
        if (localDate != null) {
            return localDate.getDayOfMonth();
        }
        return null;
    }

}
