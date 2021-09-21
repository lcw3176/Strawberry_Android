package com.joebrooks.strawberry_android.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

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
import com.joebrooks.strawberry_android.models.Song;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class YoutubeService {

    public List<Song> search(String query){
        List<Song> result;

        try{
            SearchAsynTask searchAsynTask = new SearchAsynTask();
            result = searchAsynTask.execute(query).get();
        } catch (Exception e){
            result = null;
        }

        return result;
    }


    private class SearchAsynTask extends AsyncTask<String, Void, List<Song>>{

        @Override
        protected List<Song> doInBackground(String... query) {
            List<Song> lst = new ArrayList<>();

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

                search.setQ(query[0]);
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
                        temp.setThumbnail(getBitmapFromURL(url));

                        temp.setName(i.getSnippet().getTitle());

                        lst.add(temp);
                    }
                }
            } catch (Exception e){
                System.out.println("에러");
                System.out.println(e);
            }

            return lst;
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                return null;
            }
        }
    }

}
