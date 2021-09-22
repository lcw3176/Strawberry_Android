package com.joebrooks.strawberry_android.viewModels;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.joebrooks.strawberry_android.adapter.ListViewAdapter;
import com.joebrooks.strawberry_android.models.Song;
import com.joebrooks.strawberry_android.services.YoutubeService;

public class SearchViewModel extends AndroidViewModel {
    private YoutubeService youtubeService;
    private ListViewAdapter adapter;
    private ListView listView;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        youtubeService = new YoutubeService(application);
    }


    public boolean search(String query){
        adapter.clear();
        youtubeService.search(query, adapter);

        return false;
    }

    public void setListView(ListView listView){
        this.listView = listView;
        this.adapter = (ListViewAdapter) listView.getAdapter();

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Song song = (Song)adapter.getItem(position);
                youtubeService.download(song.getName(), song.getId());
            }
        });
    }

}
