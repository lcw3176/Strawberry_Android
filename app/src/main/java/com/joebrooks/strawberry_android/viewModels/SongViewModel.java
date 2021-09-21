package com.joebrooks.strawberry_android.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.joebrooks.strawberry_android.models.Song;
import com.joebrooks.strawberry_android.services.FileService;

public class SongViewModel extends ViewModel {
    public ObservableField<Song> song = new ObservableField<>();

    private final FileService fileService = new FileService();

    public SongViewModel(){
        for(Song i : fileService.readAllSong()){
            song.set(i);
        }
    }
}