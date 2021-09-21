package com.joebrooks.strawberry_android.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.joebrooks.strawberry_android.BR;
import com.joebrooks.strawberry_android.R;
import com.joebrooks.strawberry_android.adapter.ListViewAdapter;
import com.joebrooks.strawberry_android.viewModels.SongViewModel;

public class SongFragment extends Fragment {
    private SongViewModel viewModel;
    private ViewDataBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(SongViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_song, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setVariable(BR.viewModel, viewModel);

        ListViewAdapter adapter = new ListViewAdapter();
        ListView listview = (ListView) binding.getRoot().findViewById(R.id.songListView);


        listview.setAdapter(adapter);

        return binding.getRoot();
    }
}