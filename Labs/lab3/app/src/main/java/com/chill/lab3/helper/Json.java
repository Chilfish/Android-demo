//package com.chill.lab3.helper;
//
//import android.content.Context;
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//public class Json {
//
//  public void test(Context context) {
//    JSONParser parser = new JSONParser();
//
//    try {
//      // 读取JSON文件
//      InputStream is = context.getAssets().open("products.json");
//      InputStreamReader isr = new InputStreamReader(is);
//      Object obj = parser.parse(isr);
//
//      // 将读取的内容转换为JSONObject
//      JSONObject jsonObject = (JSONObject) obj;
//
//      System.out.println(obj);
//      Log.d("json", String.valueOf((JSONObject) obj));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//}
