package com.joebrooks.strawberry_android.models;

import android.graphics.Bitmap;

public class Song {
    private String id;
    private String name;
    private String singer;
    private Bitmap thumbnail;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setSinger(String singer){
        this.singer = singer;
    }

    public String getSinger(){
        return this.singer;
    }

    public void setThumbnail(Bitmap thumbnail){
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail(){
        return this.thumbnail;
    }


}
