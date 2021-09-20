package com.joebrooks.strawberry_android.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.joebrooks.strawberry_android.models.Song;
import com.joebrooks.strawberry_android.services.FileService;

import java.util.List;

public class SongViewModel extends ViewModel {
    public ObservableField<Song> song = new ObservableField<>();
    public ObservableField<String> test = new ObservableField<>("hello");
    private int count = 0;
    private final FileService fileService = new FileService();

    public SongViewModel(){
        for(Song i : fileService.readAllSong()){
            song.set(i);
        }
    }
    public void onClickButton(){
        test.set(String.valueOf(count++));
    }
    // TODO: Implement the ViewModel
}