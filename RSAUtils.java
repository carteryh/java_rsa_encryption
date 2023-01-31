package com.util.rsa.demo;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * RSA 加解密算法工具
 * <pre>
 *     依赖第三方jar包：bcprov-jdk16-1.46.jar 或者 bcprov-jdk15on-1.46.jar
 *     记录：
 *     1、Java Sun 的 security provider 默认：RSA/None/PKCS1Padding
 *     2、Android 的 security provider 是 Bouncycastle Security provider，默认：RSA/None/NoPadding
 *     3、Cipher.getInstance("RSA/ECB/NoPadding")，有一个缺点就是解密后的明文比加密之前多了很多空格
 *     推荐：
 *     Cipher.getInstance("RSA"，"BC")
 * </pre>
 * <pre>
 *     一般用法：
 *     1、私钥加密，公钥解密（服务端加密，客户端解密）
 *     2、公钥加密，私钥解密（客户端加密，服务端解密）
 *     注意：私钥不要泄漏出去，客户端一般使用公钥
 * </pre>
 *
 */
public class RSAUtils {

    public static final String modulus="9305e60da673d3756b422e73b41605b9b2a76c61e84c1d0a9b63011301c8ba9fb0c3fe30c6350732756d6ed11788a7f067e44fdee2c35d59ec40340275c3bf98699b874ff434218a842f0c9d8ddaf2eb41f10baec58e5dbf2cd6e028dd244a1b91a7b09e73900b7d34c1a2ba0795f1ac40931437bc64755a227501d648bd9685";
    public static final String public_exponent = "10001";

    public static void main(String[] args) throws Exception {
//        String username = encryptRsa("xxx".getBytes());
//        System.out.println(username);

        //加密内容abc+123，编码处理后为abc%2B123（+编码后为%2B）
        String password = encryptRsa("abc%2B123".getBytes());
        System.out.println(password);
    }


    private static String encryptRsa(byte[] source) {
        BigInteger modulus = new BigInteger("9305e60da673d3756b422e73b41605b9b2a76c61e84c1d0a9b63011301c8ba9fb0c3fe30c6350732756d6ed11788a7f067e44fdee2c35d59ec40340275c3bf98699b874ff434218a842f0c9d8ddaf2eb41f10baec58e5dbf2cd6e028dd244a1b91a7b09e73900b7d34c1a2ba0795f1ac40931437bc64755a227501d648bd9685", 16);
        BigInteger exponent = new BigInteger("10001", 16);
        //使用的是百度统一登录页的modulus和exponent,可以根据实际项目不同来修改
        int chunkSize = modulus.bitLength() / 8 - 1;
//        int chunkSize = 8;

        //算出分批加密每批的最大长度
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < source.length; i += chunkSize) {
            BigInteger c = new BigInteger(arrayReverse(Arrays.copyOfRange(source, i, i + chunkSize)));
            result.append(c.modPow(exponent, modulus).toString(16));
        }
        return result.toString();
    }

    /**
     *
     * @param m 模
     * @param e 指数
     * @param source
     * @return
     */
    public static String encryptRsa(String m, String e, byte[] source) {
        BigInteger modulus = new BigInteger(m, 16);
        BigInteger exponent = new BigInteger(e, 16);
        //使用的是百度统一登录页的modulus和exponent,可以根据实际项目不同来修改
        int chunkSize = modulus.bitLength() / 8 - 1;
//        int chunkSize = 8;

        //算出分批加密每批的最大长度
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < source.length; i += chunkSize) {
            BigInteger c = new BigInteger(arrayReverse(Arrays.copyOfRange(source, i, i + chunkSize)));
            result.append(c.modPow(exponent, modulus).toString(16));
        }
        return result.toString();
    }

    private static byte[] arrayReverse(byte[] src) {
        if (src == null) {
            return null;
        } else {
            int i = 0, j = src.length;
            byte[] result = new byte[j];
            while (true) {
                j--;
                if (j < 0) {
                    return result;
                }
                result[j] = src[i++];
            }
        }
    }

}