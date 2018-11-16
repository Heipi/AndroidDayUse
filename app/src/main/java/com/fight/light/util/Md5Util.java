package com.fight.light.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 */
public class Md5Util {
    /**
     * md5 加密
     * @param url
     * @return
     */
    public static  String hashKeyFormUrl(String url){
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
             mDigest.update(url.getBytes());
             cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
           cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;

    }

    private static  String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
         //   Log.d("byte_i",""+bytes[i]);
            String hex = Integer.toHexString(0xFF & bytes[i]);//此方法返回的字符串表示的无符号整数参数所表示的值以十六进制
         //   Log.d("hex",hex);
          if (hex.length() == 1){
              sb.append('0');
          }
          sb.append(hex);
        }
        Log.d("HexString",sb.toString());
         return sb.toString();

    }

}
