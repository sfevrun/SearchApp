package com.example.saul.searchapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SAUL on 2/15/2018.
 */

public class Article implements Serializable{
    public String getWebUrl() {
        return webUrl;
    }

    public String getHaedline() {
        return haedline;
    }

    public String getThumbNail() {
        return thumbNail;
    }
    String webUrl;
    String haedline;
    String thumbNail;
    public Article(JSONObject jsonObject){
        try{
            this.webUrl=jsonObject.getString("web_url");
            this.haedline=jsonObject.getJSONObject("headline").getString("main");
            JSONArray _multimedia=jsonObject.getJSONArray("multimedia");
            if(_multimedia.length()>0){
                JSONObject _multimediajson=_multimedia.getJSONObject(0);
                this.thumbNail="http://www.nytimes.com/"+_multimediajson.getString("url");
            }else{
                this.thumbNail="";
            }

        }catch (JSONException e){

        }
    }
    public static ArrayList<Article> fromJsonArray(JSONArray arr){
        ArrayList<Article> results=new ArrayList<>();
        for(int i=0;i<arr.length();i++){
            try{
                results.add(new Article(arr.getJSONObject(i)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
