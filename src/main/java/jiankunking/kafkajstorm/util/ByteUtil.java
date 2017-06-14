package jiankunking.kafkajstorm.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by jiankunking on 2017/4/24 13:09.
 */
public class ByteUtil {

    public static String getStringFromByteArray(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }
}
