package com.joebrooks.strawberry_android.models;

import android.graphics.Bitmap;

public class Song {
    private String name;
    private Bitmap thumbnail;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setThumbnail(Bitmap thumbnail){
        this.thumbnail = thumbnail;
    }

    public Bitmap getThumbnail(){
        return this.thumbnail;
    }


}
