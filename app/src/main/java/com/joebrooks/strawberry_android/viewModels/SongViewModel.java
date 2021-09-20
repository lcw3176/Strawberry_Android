package com.joebrooks.strawberry_android.viewModels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class SongViewModel extends ViewModel {
    public ObservableField<String> test = new ObservableField<>("hello");
    private int count = 0;

    public void onClickButton(){
        test.set(String.valueOf(count++));
    }
    // TODO: Implement the ViewModel
}