/**
 * @author jz
 * @create 2022-08-27 14:47
 */
public class Demo {
    public static void main(String[] args) {
        testJson();
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

