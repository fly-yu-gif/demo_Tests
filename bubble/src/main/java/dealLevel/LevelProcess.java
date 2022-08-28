package dealLevel;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.json.JsonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class LevelProcess {

    public static void main(String[] arg) throws IOException {
        //关卡后移
//        dealFile(801, 809, -790);
        //svn关卡复制到项目
//        copyLevel("B");
//        copyLevel("B");
        //csv信息写入到关卡
//        dealLevel("A");
        dealLevel("B");
        //关卡内泡泡排序
//        sortLevvel(soure, soure);
        String source = "E:\\bubble_svn\\美术资源2\\切图\\开发切图\\1_2_sign\\道具";
        String output = "C:\\Users\\fly\\Desktop\\out\\";
//        reNameFile(source, output);
    }

    /**
     * @param file    要删除的目录
     * @param delSelf 是否删除自己
     */
    public static void deleteFile(File file, boolean delSelf) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                deleteFile(f, true);
            } else {
                f.delete();
            }
        }
        //最后是否要删除自己;
        if (delSelf) {
            file.delete();
        }
    }

    /**
     * 把每一关的csv数据更新到json中
     *
     * @param levelCag
     * @throws IOException
     */
    public static void dealLevel(String levelCag) throws IOException {
        String sourceLevel;
        String target;
        if (levelCag == "A") {
            sourceLevel = "E:\\bubble_svn\\1.策划文档\\关卡\\LevelA\\";
            target = "E:\\work\\android\\assets\\customData\\";
        } else {
            sourceLevel = "E:\\bubble_svn\\1.策划文档\\关卡\\LevelB\\customDatab\\";
            target = "C:\\Users\\fly\\Desktop\\downTest\\jsonLevel\\1.6.4-debug\\";
        }
        File files = new File(sourceLevel);
        if (!files.exists() || !files.isDirectory()) {
            System.err.println("dealLevel err");
            return;
        }
//        清空目标文件夹
        deleteFile(new File(target), false);
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("\n");
        String[] levelCsvInfo = readCsvFile(levelCag, sourceLevel);
        for (File f : files.listFiles()) {
//            不是json或者csv的话就不复制
            if (!(f.getName().endsWith(".json"))) {
                continue;
            }
            int levelNum = Integer.parseInt(f.getName().replaceAll("\\D", ""));
            String jsonStr = readJsonFile(f);
            //从字符串解析JSON对象
            JSONObject obj = JSON.parseObject(jsonStr);
            //从对象中获取key的值,要注意value前后类型
            String nameStr = obj.getString("name");
            //往json对象中写入参数
            obj.put("csv", levelCsvInfo[levelNum].replaceAll(",", ";"));
            //将JSON对象转化为字符串
            String objStr = JSON.toJSONString(obj);
            stringArrayList.add(levelNum + "-" + CheckCrc32.getCrc(objStr) + "\n");
            JsonUtil.writeStrToFile(objStr, target, f.getName());

        }
        String exp = ",";
        String str = stringArrayList.toString();
        str = str.replaceAll(exp, "");
        JsonUtil.writeStrToFile(str, target, "check.txt");


    }

    /**
     * 复制svn中的AB关卡到项目中
     */
    public static void copyLevel(String type) throws IOException {
        String sourceLevel;
        String target;
        if (type == "A") {
            sourceLevel = "E:\\bubble_svn\\1.策划文档\\关卡\\LevelA\\";
            target = "E:\\work\\android\\assets\\customData\\";
        } else {
            sourceLevel = "E:\\bubble_svn\\1.策划文档\\关卡\\LevelB\\customDatab\\";
            target = "E:\\work\\android\\assets\\customDatab\\";
        }
        File files = new File(sourceLevel);
        if (!files.exists() || !files.isDirectory()) {
            System.err.println("change err");
            return;
        }
//        清空目标文件夹
        deleteFile(new File(target), false);

        File outfile;
        for (File f : files.listFiles()) {
//            不是json或者csv的话就不复制
            if (!(f.getName().endsWith(".json") || f.getName().endsWith(".csv"))) {
                continue;
            }
            outfile = new File(target + f.getName());
            Files.copy(f.toPath(), outfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * @param inputPath
     * @param outPath   替换掉文件名种的空格和+
     * @throws IOException
     */
    public static void reNameFile(String inputPath, String outPath) throws IOException {
        File files = new File(inputPath);
        //获取input
        if (!files.exists() || !files.isDirectory()) {
            System.err.println("change err");
            return;
        }
        //清空output
//        deleteFile(new File(outPath), false);


        File outfile;
        for (File f : files.listFiles()) {
//            不是json或者csv的话就不复制
            if (!f.getName().endsWith(".png")) {
                continue;

            }
            String str = f.getName();
            //多个空格或者 - 或者 +
            String regx1 = "\\s+|-|\\+";
            String regx2 = "\\_+";
            String res = str.replaceAll(regx1, "_").replaceAll(regx2, "_");
            outfile = new File(outPath + res);
            Files.copy(f.toPath(), outfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * 读取json文件
     *
     * @return 返回json字符串
     */

    public static String readJsonFile(File jsonFile) {

        String jsonStr = "";

        try {

//File jsonFile = new File(fileName);

            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");

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

    //关卡延后多少关
    public static void delayLevelNum(int begin, int end, int offset) {
        String fileDir = "C:\\Users\\fly\\Desktop\\svn_atlas\\Bubble2019\\1.策划文档\\关卡\\LevelA\\";
        fileDir = "C:\\Users\\fly\\Desktop\\input\\";
        String desDir = "C:\\Users\\fly\\Desktop\\output\\";
        InputStream in = null;
        try {
            File files = new File(fileDir);
            //遍历所有关卡文件
            for (File f : files.listFiles()) {
                //只读取json文件
                if (f.getName().endsWith(".json")) {
                    String filePath = fileDir + f.getName();
                    //获取关卡序号
                    int levelNum = Integer.parseInt(f.getName().replaceAll(".json", "").replaceAll("Level", ""));
                    if (levelNum < begin || levelNum > end)
                        continue;
                    levelNum = levelNum + offset;
                    //输入流
                    FileInputStream fis = null;
                    fis = new FileInputStream(new File(filePath));
                    byte[] bys = new byte[4096];
                    StringBuffer buffer = new StringBuffer();
                    while (fis.read(bys, 0, bys.length) != -1) {
                        //将字节数组转换为字符串
                        buffer.append(new String(bys));
                    }
                    fis.close();
                    //读取原始关卡到字符串
                    String jsonStr = buffer.toString();
                    System.out.println(jsonStr);
                    //把字符串修改成对象
                    JSONObject obj = JSON.parseObject(jsonStr);
                    obj.put("l", levelNum);
                    //5.把json对象转换成json字符串
                    String jsonString = JSON.toJSONString(obj);
                    //6.将json字符串写入到文件,长度合理才能写入
                    System.out.println(jsonString);
                    //创建FileOutputStream对象，写入内容
                    File outFile = new File(desDir, "Level" + levelNum + ".json");
                    FileOutputStream fos = new FileOutputStream(outFile);
//                    向文件中写入内容
                    fos.write(jsonString.getBytes());
                    fos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            }
        }

    }

    /**
     * @param source 原关卡所在文件夹
     * @param target 新关卡所在文件夹
     */
    public static void sortBubble(String source, String target) {
//        1.先清空原来的文件
        File targetFile = new File(target);
//        deleteFile(targetFile, false);
        InputStream in = null;
        JSONArray crystal_arr = new JSONArray();
        JSONArray cloud_arr = new JSONArray();
        JSONArray ice_arr = new JSONArray();
        JSONArray baes_arr = new JSONArray();
        try {
            File files = new File(source);
            //遍历所有关卡文件
            for (File f : files.listFiles()) {
                //只读取json文件
                if (f.getName().endsWith(".json")) {
                    String filePath = source + f.getName();
                    //获取关卡序号
                    int levelNum = Integer.parseInt(f.getName().replaceAll(".json", "").replaceAll("Level", ""));
                    //输入流
                    FileInputStream fis = null;
                    fis = new FileInputStream(new File(filePath));
                    byte[] bys = new byte[4096];
                    StringBuffer buffer = new StringBuffer();
                    while (fis.read(bys, 0, bys.length) != -1) {
                        //将字节数组转换为字符串
                        buffer.append(new String(bys));
                    }
                    fis.close();
                    //读取原始关卡到字符串
                    String jsonStr = buffer.toString();
                    //把字符串修改成对象
                    JSONObject obj = JSON.parseObject(jsonStr);
                    //4.1获取泡泡数组
                    JSONArray listBubble = obj.getJSONArray("bubble");
                    //4.2对泡泡数组进行分开排序[其他球，水晶，云朵，冰球3]
                    String temp;
                    for (int i = 0, bubbleCode; i < listBubble.size(); i++) {
                        bubbleCode = (listBubble.get(i).toString().toCharArray()[0] - 48) * 10
                                + Integer.parseInt(listBubble.get(i).toString().substring(1, 2));
                        switch (bubbleCode) {
                            case 13:
                                ice_arr.add(listBubble.get(i).toString());
                                break;
                            case 7:
                                crystal_arr.add(listBubble.get(i).toString());
                                break;
                            case 10:
                                cloud_arr.add(listBubble.get(i).toString());
                                break;
                            default:
                                baes_arr.add(listBubble.get(i).toString());

                        }
                    }
                    //4.3合并泡泡数组
                    //4.4重新写入泡泡数组
                    listBubble.clear();
                    listBubble.addAll(baes_arr);
                    listBubble.addAll(cloud_arr);
                    listBubble.addAll(ice_arr);
                    listBubble.addAll(crystal_arr);
                    obj.put("bubble", listBubble);
                    //5.把json对象转换成json字符串
                    String jsonString = JSON.toJSONString(obj);
                    //6.将json字符串写入到文件,长度合理才能写入
                    System.out.println(jsonString);
                    //创建FileOutputStream对象，写入内容
                    File outFile = new File(target, "Level" + levelNum + ".json");
                    FileOutputStream fos = new FileOutputStream(outFile);
//                    向文件中写入内容
                    fos.write(jsonString.getBytes());
                    fos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] readCsvFile(String ab, String pathDir) {
        File targetFile;
        String filePath;
        if ("A".equals(ab)) {
            filePath = pathDir + "levels_a.csv";
        } else {
            filePath = pathDir + "levels_b.csv";
        }
        targetFile = new File(filePath);
        String resStr = JsonUtil.readJsonFile(targetFile);
        assert resStr != null;
        String str = resStr.replace("\r\n", "\n");
        return str.split("\n");
    }

}
