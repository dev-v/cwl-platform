package com.cwl.tool.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <li>修改记录: ...</li>
 * <li>内容摘要: ...</li>
 * <li>其他说明: ...</li>
 *
 * @author dev-v
 * @version 1.0
 * @since 2018/1/10T
 */
public class CipherUtil {
    /**
     * SHA算法
     */
    private static final String ALGORITHM_SHA = "SHA";


    /**
     * 使用SHA加密
     *
     * @param data 要加密的字符串
     * @return 返回加密后的信息
     */
    public static String SHAEncode(String data) {
        return encryptEncode(ALGORITHM_SHA, data);
    }


    /**
     * 信息摘要算法
     *
     * @param algorithm 算法类型
     * @param data      要加密的字符串
     * @return 返回加密后的摘要信息
     */
    private static String encryptEncode(String algorithm, String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return byteArrayToHexStr(md.digest(data.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 将字节数组转换成16进制字符串
     *
     * @param byteArray 要转码的字节数组
     * @return 返回转码后的16进制字符串
     */
    private static String byteArrayToHexStr(byte byteArray[]) {
        StringBuffer buffer = new StringBuffer(byteArray.length * 2);
        int i;
        for (i = 0; i < byteArray.length; i++) {
            if (((int) byteArray[i] & 0xff) < 0x10)//小于十前面补零
                buffer.append("0");
            buffer.append(Long.toString((int) byteArray[i] & 0xff, 16));
        }
        return buffer.toString();
    }
}
