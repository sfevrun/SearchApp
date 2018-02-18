package com.example.saul.searchapp.models;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SAUL on 2/15/2018.
 */

public class Article implements Parcelable {
    public String getWebUrl() {
        return webUrl;
    }

    public String getHaedline() {
        return haedline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getNew_desk() {
        return new_desk;
    }

    String webUrl;
    String haedline;
    String thumbNail;
    String snippet;
    String keywords;
    String new_desk;
    public Article(JSONObject jsonObject){
        try{
            this.webUrl=jsonObject.getString("web_url");
            this.snippet=jsonObject.getString("snippet");
            this.haedline=jsonObject.getJSONObject("headline").getString("main");
            this.new_desk=jsonObject.getString("new_desk");
            JSONArray _multimedia=jsonObject.getJSONArray("multimedia");
            if(_multimedia.length()>0){

               for (int i=0;i<_multimedia.length();i++){
           JSONObject jsonObject1=   _multimedia.getJSONObject(i);
                    if (jsonObject1.getString("subtype").equals("xlarge")){
                        this.thumbNail="http://www.nytimes.com/"+jsonObject1.getString("url");
                        break;
                    }
                }

           //     JSONObject _multimediajson=_multimedia.getJSONObject(0);
             //   this.thumbNail="http://www.nytimes.com/"+_multimediajson.getString("url");
            }else{
                this.thumbNail="";
            }

            JSONArray _keywords=jsonObject.getJSONArray("keywords");
            if(_keywords.length()>0){

                  JSONObject _keywordsjon=_keywords.getJSONObject(0);
                 this.keywords=_keywordsjon.getString("value");
            }else{
                this.keywords="";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {

        parcel.writeString(webUrl);
        parcel.writeString(haedline);
        parcel.writeString(thumbNail);
        parcel.writeString(snippet);
        parcel.writeString(keywords);
        parcel.writeString(new_desk);
    }
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>()
    {
        @Override
        public Article createFromParcel(android.os.Parcel parcel) {
            return new Article(parcel);
        }

        @Override
        public Article[] newArray(int i) {
            return new Article[i];
        }
    };

    public Article(Parcel in) {
        this.webUrl = in.readString();
        this.haedline= in.readString();
        this.thumbNail = in.readString();
        this.snippet = in.readString();
        this.keywords = in.readString();
        this.new_desk = in.readString();
    }
    public Article(){}
}
