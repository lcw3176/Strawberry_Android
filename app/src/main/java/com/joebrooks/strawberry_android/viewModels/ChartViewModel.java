package com.joebrooks.strawberry_android.viewModels;

import android.widget.ListView;

import androidx.lifecycle.ViewModel;

import com.joebrooks.strawberry_android.adapter.ListViewAdapter;
import com.joebrooks.strawberry_android.models.Song;
import com.joebrooks.strawberry_android.services.FileService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChartViewModel extends ViewModel {
    private ListViewAdapter adapter;
    private ListView listView;
    private final FileService fileService = new FileService();
    private final String url = "https://www.melon.com/chart/day/index.htm";
    private Disposable backgroundTask;

    public void setListView(ListView listView){
        this.listView = listView;
        this.adapter = (ListViewAdapter) listView.getAdapter();
    }

    public void initPage() {
        backgroundTask = Observable.fromCallable(() ->{
            List<Song> lst = new ArrayList<>();

            try{
                Document doc = Jsoup.connect(url).get();
                Elements songElements = doc.getElementsByClass("ellipsis rank01").select("span").select("a");
                Elements singerElements = doc.getElementsByClass("checkEllipsis").select("span");
                Elements thumbnailElements = doc.getElementsByClass("image_typeAll").select("img");
                for(int i = 0; i < songElements.size(); i++){
                    Song temp = new Song();
                    temp.setName(songElements.get(i).text());
                    temp.setSinger(singerElements.get(i).text());
                    temp.setThumbnail(fileService.getBitmapFromURL(thumbnailElements.get(i).attr("src")));

                    lst.add(temp);
                }
            } catch (Exception e){

            }

            return lst;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Song>>() {
            @Override
            public void accept(List<Song> songs) {
                for(Song i : songs){
                    adapter.addItemWithSinger(i.getThumbnail(), i.getName(), i.getSinger());
                }

                adapter.notifyDataSetChanged();
                backgroundTask.dispose();
            }
        });
    }
}
