package com.swms.plugin.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class SignatureUtil {
    private static final char[] HEX_CHAR_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String byteArrToHex(byte[] btArr) {
        char[] strArr = new char[btArr.length * 2];
        int i = 0;
        for (byte bt : btArr) {
            strArr[i++] = HEX_CHAR_ARR[bt >>> 4 & 0xf];
            strArr[i++] = HEX_CHAR_ARR[bt & 0xf];
        }
        return new String(strArr);

    }

    /**
     * 字符串签名算法
     *
     * @param content  被签名的字符串
     * @param charset  字符集编码
     * @param keys     秘钥
     * @param signType MD2/MD5/SHA-1/SHA-256/SHA-384/SHA-512
     *
     * @return
     */
    public static String doSign(String content, String charset, String keys, String signType) {
        String sign = "";
        content = content + keys;
        try {
            MessageDigest md = MessageDigest.getInstance(signType);
            md.update(content.getBytes(charset));
            sign = byteArrToHex(md.digest());
        } catch (Exception e) {
            log.error("SignatureUtil#doMD5Sign error", e);
        }
        return sign;
    }

}
