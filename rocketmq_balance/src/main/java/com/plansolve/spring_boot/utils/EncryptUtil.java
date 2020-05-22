package com.plansolve.spring_boot.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Andrew
 * @Date: 2018/6/6
 * @Description:
 **/
@Service
public class EncryptUtil {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * 对字符串进行加密
     *
     * @param alter 所加密字符串
     * @return
     */
    public static String encrypt(String alter) {
        return MD5(alter);
    }

    /**
     * 判断明文密文所储信息是否一致
     *
     * @param alter  明文
     * @param cipher 密文
     * @return
     */
    public static Boolean comparator(String alter, String cipher) {
        String code = encrypt(alter);
        if (cipher.equalsIgnoreCase(code)){
            return true;
        }
        return false;
    }

    /**
     * 安卓端页面为用户密码所做初次加密
     *
     * @param alter
     * @return
     */
    public static String encryptForPage(String alter) {
        StringBuffer stringBuffer = new StringBuffer();
        // 得到一个信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(alter.getBytes());
            // 把每一个byte做一个与运算 0xff
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    private static String MD5(String key, Integer num) {
        if (num != null && num > 0) {
            for (Integer i = 0; i < num; i++) {
                key = MD5(key);
            }
            return key;
        } else {
            return MD5(key);
        }
    }

    private static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /***************************************************高强度加密算法,不可逆（暂定MD5）***************************************************/

    public static byte[] computeSHA1(byte[] content) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            return sha1.digest(content);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(String input) {
        try {
            return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base64解码.
     */
    public static String decodeBase64String(String input) {
        try {
            return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

}
