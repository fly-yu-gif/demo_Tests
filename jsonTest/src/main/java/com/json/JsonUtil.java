package com.json;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author jz
 * @create 2022-08-27 18:08
 */
public class JsonUtil {
    public static String readJsonFile(File jsonFile) {

        String jsonStr = "";

        try {


            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(Files.newInputStream(jsonFile.toPath()), StandardCharsets.UTF_8);

            int ch = 0;

            StringBuffer sb = new StringBuffer();

            while ((ch = reader.read()) != -1) {

                sb.append((char) ch);

            }

            fileReader.close();

            reader.close();

            jsonStr = sb.toString();


            return jsonStr;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }

    /**
     * @param strValue 要写入的内容
     * @param outPath  写入目录
     * @param fileName 文件名
     */
   public static void writeStrToFile(String strValue, String outPath, String fileName) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outPath + fileName,true);
            fos.write(strValue.getBytes());
            fos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
