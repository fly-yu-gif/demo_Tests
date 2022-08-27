import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.JsonParser;
import com.json.JsonUtil;
import com.json.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jz
 * @create 2022-08-27 14:47
 * 从效率上来讲，
 * 当数据量较小时，org.json  处理速度最快，
 * 当数据量较大时500万以上，com.alibaba.fastjson处理速度最快。
 * https://www.cnblogs.com/amigou/p/15751342.html
 * https://www.runoob.com/w3cnote/fastjson-intro.html
 */
public class Demo {
    public static void main(String[] args) {
//        testJson();
//        createObj();
        objToJsonStr();
//        jsonStrToObj();
    }

    static void objToJsonStr() {
        List<Person> listOfPersons = new ArrayList<Person>();
        listOfPersons.add(new Person(15, "John Doe", new Date()));
        listOfPersons.add(new Person(20, "Janette Doe", new Date()));
        String jsonOutput = JSON.toJSONString(listOfPersons);
        //以Arr方式输出
        String jsonOutputArr = JSON.toJSONString(listOfPersons, SerializerFeature.BeanToArray);
        System.out.println(jsonOutput);
        System.out.println(jsonOutputArr);
        //把字符串写入文件
//        JsonUtil.writeStrToFile(jsonOutput+"\n", "E://", "1.json");
//        JsonUtil.writeStrToFile(jsonOutputArr, "E://", "1.json");
    }

    static void createObj() {
        //jsonObj
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("AGE", 10);
        jsonObject.put("FULL NAME", "Doe ");
        jsonObject.put("DATE OF BIRTH", "27/08/2022");
        //javaObj
        Person p = JSONObject.parseObject(jsonObject.toJSONString(), Person.class);
        System.out.println(p);
    }

    static void jsonStrToObj() {
        String str = "{\"AGE\":20,\"FULL NAME\":\"John\",\"DATE OF BIRTH\":\"27/08/2022\"}";
        //to JavaBean
        Person newPerson = JSON.parseObject(str, Person.class);
        System.out.println(newPerson);
        //to JsonObject
        JSONObject jsonObject1 = JSON.parseObject(str);

//        JSONObject jsonObject1=JSONObject.parseObject(jsonObject);
        System.out.println(jsonObject1.toJSONString());
    }

    static void testJson() {
        int data = 100000;
        System.out.println("data:" + data);
        Long t1 = System.currentTimeMillis();
        org.json.JSONObject jsonObject1 = new org.json.JSONObject();
        for (int i = 0; i < data; i++) {
            jsonObject1.put("k" + i, "v" + i);
        }
        Long t2 = System.currentTimeMillis();
        System.out.println("org.json:" + (t2 - t1));


        Long t5 = System.currentTimeMillis();
        com.alibaba.fastjson.JSONObject jsonObject3 = new com.alibaba.fastjson.JSONObject();
        for (int i = 0; i < data; i++) {
            jsonObject3.put("k" + i, "v" + i);
        }
        Long t6 = System.currentTimeMillis();
        System.out.println("com.alibaba.fastjson:" + (t6 - t5));

        Long t7 = System.currentTimeMillis();
        com.google.gson.JsonObject jsonObject4 = new com.google.gson.JsonObject();
        for (int i = 0; i < data; i++) {
            jsonObject4.addProperty("k" + i, "v" + i);
        }
        Long t8 = System.currentTimeMillis();
        System.out.println("com.google.gson:" + (t8 - t7));
    }
}

