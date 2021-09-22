package com.joebrooks.strawberry_android.services;

import android.app.Application;
import android.util.SparseArray;

import androidx.annotation.Nullable;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.joebrooks.strawberry_android.BuildConfig;
import com.joebrooks.strawberry_android.adapter.ListViewAdapter;
import com.joebrooks.strawberry_android.models.Song;

import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class YoutubeService {
    private Disposable backgroundTask;
    private Application application;
    private final FileService fileService = new FileService();

    public YoutubeService(Application application){
        this.application = application;
    }

    public void search(String query, ListViewAdapter adapter){
        backgroundTask = Observable.fromCallable(() ->{
            List<Song> result = new ArrayList<>();

            try {

                HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
                final JsonFactory JSON_FACTORY = new JacksonFactory();
                final long NUMBER_OF_VIDEOS_RETURNED = 30;

                YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName(BuildConfig.APP_NAME).build();

                YouTube.Search.List search = youtube.search().list("id,snippet");
                search.setKey(BuildConfig.APP_KEY);

                search.setQ(query);
                search.setType("video");

                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
                SearchListResponse searchResponse = search.execute();
                List<SearchResult> searchResultList = searchResponse.getItems();

                if (searchResultList != null) {
                    for(SearchResult i : searchResultList){
                        Song temp = new Song();
                        temp.setId(i.getId().getVideoId());
                        String url = ((Thumbnail) i.getSnippet().getThumbnails().get("default")).getUrl();
                        temp.setThumbnail(fileService.getBitmapFromURL(url));
                        temp.setName(StringEscapeUtils.unescapeHtml4(i.getSnippet().getTitle()));

                        result.add(temp);
                    }
                }
            } catch (Exception e){

            }

            return result;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Song>>() {
            @Override
            public void accept(List<Song> songs) {
                for(Song i : songs){
                    adapter.addItem(i.getThumbnail(), i.getName(), i.getId());
                }

                adapter.notifyDataSetChanged();
                backgroundTask.dispose();
            }
        });
    }


    public void download(String videoName, String videoId) {

        String videoUrl = "https://www.youtube.com/watch?v=" + videoId;


        new YouTubeExtractor(application.getApplicationContext()) {
            @Override
            protected void onExtractionComplete(@Nullable SparseArray<YtFile> ytFiles, @Nullable VideoMeta videoMeta) {
                if (ytFiles != null) {
                    int itag = ytFiles.keyAt(0);
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    fileService.downloadFromUrl(application, downloadUrl, videoName, videoName + ".mp3");
                }
            }
        }.extract(videoUrl);
    }


}
