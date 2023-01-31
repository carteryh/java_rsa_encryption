# java_rsa_encryption
后端模拟前端RSA.js加密登录


项目开发过程中，经常会遇到数据爬取需求，但是对于某些网站，由于前端加密，导致数据爬取不容易。比如某网站，前端使用RSA.js加密，并且后端返回对应的公钥的指数和模数，通过后端返回的指数和模数对传输内容进行加密，如登录接口需要对账户及密码进行加密传输到后端，后端在解密后验证账户信息。
 *     一般用法：
 *     1、私钥加密，公钥解密（服务端加密，客户端解密）
 *     2、公钥加密，私钥解密（客户端加密，服务端解密）
 *     注意：私钥不要泄漏出去，客户端一般使用公钥


1.前端RSA加密
前端RSA加密代码及需要引用js文件(RSA.js、BigInt.js、Barrett.js)，如下图所示
![在这里插入图片描述](https://img-blog.csdnimg.cn/9ab233ba2e994502b367978d4e3cc648.png)
前端加密核心代码
```
    var key = null;
    var countdown = 90;
    var bool = true;
    // 公钥指数，16进制
    var empoent = "10001";
    // 公钥模数，16进制
    var module = "90e3486cbcb4858df6837d42a89564fa75f6186c7fde7e9ffc12ec7e53a02c2a98a4890dacc49621fd94b658913c3282233372746b26f346595e489f0855f7b3bfabd26828ea3a119827a85fca321b50c33a93c6864da106c7ee75f7c40cf3582e82405105745b74b9151d1eabe0db756999d198571a087cb9cb18f7a3ea2809";

    // 初始化key
    function initKey() {
	    setMaxDigits(130);
        key = new RSAKeyPair(empoent, "", module);
        
        console.log("key",key)
    }
    this.initKey()
   // encodeURIComponent编码，主要是针对特殊字符，比如@  + 等，+字符编码后是%2B，16进制
    console.log(username, encryptedString(key, encodeURIComponent('xxx')))
    console.log(password, encryptedString(key, encodeURIComponent('xxx')))
```
2.后端加密代码
关键核心代码，对于加密内容，如有特殊字符，比如@、#、$、+等，若前端编码处理，后端也需要转义，比如某个字符串abc+123,其中+就是特殊字符，需要对+编码处理，+编码%2B(16进制)。因此编码处理后的内容为abc%2B123，故加密内容为编码处理后的字符串abc%2B123。
```
    /**
     *
     * @param m 模数
     * @param e 指数
     * @param source  需要加密内容
     * @return
     */
    public static String encryptRsa(String m, String e, byte[] source) {
    	//需要注意前端传模数，如16进制，需要指定为16进制
        BigInteger modulus = new BigInteger(m, 16);
        BigInteger exponent = new BigInteger(e, 16);
        //使用的是百度统一登录页的modulus和exponent,可以根据实际项目不同来修改
        int chunkSize = modulus.bitLength() / 8 - 1;

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
```
实例代码如下图所示
![在这里插入图片描述](https://img-blog.csdnimg.cn/395256f1419a45b395f280803896e6da.png)
