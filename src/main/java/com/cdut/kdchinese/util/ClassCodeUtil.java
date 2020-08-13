package com.cdut.kdchinese.util;

import java.util.UUID;

/**
 * 随机生成8位邀请码(只含大写字母)
 */
public class ClassCodeUtil {
    public static String chars[] = new String[]{"A","B","C","D","E","F","G","H","I","J"
            ,"K","L","M","N","O","P","Q","R","S","T"
            ,"U","V","W","X","Y","Z"};

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 26]);
        }
        return shortBuffer.toString();
    }

}
