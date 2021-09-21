package com.joebrooks.strawberry_android.viewModels;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import com.joebrooks.strawberry_android.adapter.ListViewAdapter;
import com.joebrooks.strawberry_android.models.Song;
import com.joebrooks.strawberry_android.services.YoutubeService;

public class SearchViewModel extends ViewModel {

    private final YoutubeService youtubeService = new YoutubeService();
    private ListViewAdapter adapter;
    private ListView listView;


    public boolean search(String query){
        adapter.clear();

        for(Song i : youtubeService.search(query)) {
            adapter.addItem(i.getThumbnail(), i.getName(), i.getId());
        }

        adapter.notifyDataSetChanged();

        return false;
    }

    public void setListView(ListView listView){
        this.listView = listView;
        this.adapter = (ListViewAdapter) listView.getAdapter();

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String link = "https://youtube.com/watch?v=";
                System.out.println(link + ((Song)adapter.getItem(position)).getId());
            }
        });
    }

}
