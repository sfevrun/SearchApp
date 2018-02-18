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

    String webUrl;
    String haedline;
    String thumbNail;
    String snippet;
    public Article(JSONObject jsonObject){
        try{
            this.webUrl=jsonObject.getString("web_url");
            this.snippet=jsonObject.getString("snippet");
            this.haedline=jsonObject.getJSONObject("headline").getString("main");
            JSONArray _multimedia=jsonObject.getJSONArray("multimedia");
            if(_multimedia.length()>0){
              /*  for (int i=0;i>_multimedia.length();i++){
                    if (_multimedia.getJSONObject(i).getString("subtype")=="xlarge"){
                        this.thumbNail="http://www.nytimes.com/"+_multimedia.getJSONObject(i).getString("url");
                    }
                }
                */
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

    }
    public Article(){}
}
