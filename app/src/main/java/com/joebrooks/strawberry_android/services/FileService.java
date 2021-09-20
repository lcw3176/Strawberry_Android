package com.joebrooks.strawberry_android.services;

import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;

import com.joebrooks.strawberry_android.models.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private final File file = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_MUSIC + File.separator + "strawberryMusic");

    public FileService(){
        if(!isExist()){
            makeStrawberryDir();
        }
    }

    private void makeStrawberryDir(){
        file.mkdirs();
    }

    private Boolean isExist(){
        return file.exists();
    }

    public List<Song> readAllSong() {
        List<Song> lst = new ArrayList<>();

        for(File i : file.listFiles()){
            Song temp = new Song();
            temp.setName("에픽하이 - Fly");
            temp.setThumbnail(ThumbnailUtils.createVideoThumbnail(i.getPath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND));
            lst.add(temp);
        }

        return lst;
    }
}
