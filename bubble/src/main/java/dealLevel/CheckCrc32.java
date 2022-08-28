package dealLevel;

import com.json.JsonUtil;

import java.util.ArrayList;
import java.util.zip.CRC32;

/**
 * @author jz
 * @create 2022-09-04 14:47
 */
public class CheckCrc32 {
    private static CRC32 crc32 = new CRC32();

    public static String getCrc(String targetStr) {
        crc32.update(targetStr.getBytes());
        return String.valueOf(crc32.getValue());
    }
}

