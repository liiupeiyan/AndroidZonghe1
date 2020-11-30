package com.example.androidzonghe1.Utils.yyWork;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class LngAndLatUtil {
    public static Map<String, Double> getLngAndLat(String address){
        Map<String, Double> map=new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=你的百度地图key";
        String json = loadJSON(url);
        Log.e("jsonnnn",json);
        try {
            JSONObject obj = new JSONObject(json);
            if(obj.get("status").toString().equals("0")){
                double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
                double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
                map.put("lng", lng);
                map.put("lat", lat);
            }else{
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
