package com.plumliu.shorturl.utils;

import cn.hutool.core.lang.hash.MurmurHash;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;


public class HashUtil {

    private static final char[] SIZE_TABLE = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };


    /**
     *
      * @param originalUrl 原始的长链接
     * @return 短链接
     */
    public static String hashToBase62(String originalUrl) {

        int i = Hashing.murmur3_32_fixed().hashString(originalUrl, StandardCharsets.UTF_8).asInt();

        long num = i & 0xFFFFFFFFL;

        StringBuilder sb = new StringBuilder();
        if (num == 0) {
            return String.valueOf(SIZE_TABLE[0]);
        }

        while (num > 0) {
            int remainder = (int) (num % 62);
            sb.append(SIZE_TABLE[remainder]);
            num /= 62;
        }

        return sb.reverse().toString();
    }

}
