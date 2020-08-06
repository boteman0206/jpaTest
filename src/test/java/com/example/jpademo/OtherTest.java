package com.example.jpademo;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class OtherTest {


    @Test
    public void test1(){
        for (int i = 0; i < 10; i++) {
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            System.out.println(i + " : " + salt);
        }


    }


    /**
     * shiro的加密包
     */
    @Test
    public void hashTest(){
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes("hello")) // 加密字符串
                .setSalt(ByteSource.Util.bytes("123")) // 加盐
                .setIterations(1).build(); // 加密的次数
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);


        HashRequest request2 = new HashRequest.Builder()
                .setAlgorithmName("MD5")
                .setSource(ByteSource.Util.bytes("hello"))
                .setIterations(1).build(); // 5d41402abc4b2a76b9719d911017c592
        String hex1 = hashService.computeHash(request2).toHex();
        System.out.println(hex1);
    }



    @Test
    public void testHash() throws NoSuchAlgorithmException {
        MessageDigest md5Digest = DigestUtils.getMd5Digest();

        MessageDigest sha1Digest = DigestUtils.getSha1Digest();
//        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5Digest.reset();
        md5Digest.update("hello".getBytes());
        byte[] digesta=md5Digest.digest();
        for (byte b : digesta) {
            System.out.println(b);
        }

        sha1Digest.reset();
        sha1Digest.update("hello".getBytes());
        byte[] digest = sha1Digest.digest();
        // 需要自己实现算法

        String hello = DigestUtils.md5Hex("hello");
        System.out.println(hello); // 5d41402abc4b2a76b9719d911017c592


    }

}
