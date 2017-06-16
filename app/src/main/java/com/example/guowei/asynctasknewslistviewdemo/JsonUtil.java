package com.example.guowei.asynctasknewslistviewdemo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Guowei on 2017/6/15.
 */

public class JsonUtil {
    private static final String TAG = "JsonUtil";

    /**
     * 根据url和获取JSON数据
     * @param url
     * @return
     */
    public static List<Bean> getJsonData(String url) {
        List<Bean> datas=new ArrayList<>();
        try {
            //从url中获取json数据
            String jsonString= readStream(new URL(url).openStream());
            Log.d(TAG, "getJsonData: jsonString=="+jsonString);
            JSONObject jsonObj = new JSONObject(jsonString);
            JSONArray jsonArr = jsonObj.getJSONArray("data");
            if(jsonArr.length()>0){
                for (int i=0;i<jsonArr.length();i++){
                    Bean b = new Bean();
                    JSONObject j =jsonArr.getJSONObject(i);
                    b.setmPhotoUrl(j.getString("picSmall"));
                    b.setTitle(j.getString("name"));
                    b.setContent(j.getString("description"));
                    datas.add(b);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datas;
    }
    //从输入流中读取出数据
    private static String readStream(InputStream inputStream) {
        InputStreamReader isread;
        String result="";
        String line;
        try {
            isread=new InputStreamReader(inputStream,"utf-8");
            BufferedReader br=new BufferedReader(isread);
            while((line=br.readLine())!=null){
                result+=line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return result;
    }
}
