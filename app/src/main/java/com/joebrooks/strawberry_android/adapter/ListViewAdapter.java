package com.joebrooks.strawberry_android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joebrooks.strawberry_android.R;
import com.joebrooks.strawberry_android.models.Song;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<Song> lst = new ArrayList<>();

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return lst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }

        ImageView thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnail) ;
        TextView titleTextView = (TextView) view.findViewById(R.id.songTitle) ;
        TextView singerTextView = (TextView) view.findViewById(R.id.singer) ;

        Song listViewItem = lst.get(i);

        thumbnailImageView.setImageDrawable(new BitmapDrawable(listViewItem.getThumbnail()));
        titleTextView.setText(listViewItem.getName());
        singerTextView.setText(listViewItem.getSinger());

        return view;
    }

    public void addItem(Bitmap thumbnail, String songName, String id) {
        Song item = new Song();

        item.setId(id);
        item.setName(songName);
        item.setThumbnail(thumbnail);

        lst.add(item);
    }

    public void addItemWithSinger(Bitmap thumbnail, String songName, String singer){
        Song item = new Song();

        item.setSinger(singer);
        item.setName(songName);
        item.setThumbnail(thumbnail);

        lst.add(item);
    }

    public void clear() {
        lst.clear();
    }
}
