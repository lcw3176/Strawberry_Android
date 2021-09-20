package com.joebrooks.strawberry_android.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.joebrooks.strawberry_android.BR;
import com.joebrooks.strawberry_android.R;
import com.joebrooks.strawberry_android.viewModels.ChartViewModel;

public class ChartFragment extends Fragment {

    private ChartViewModel viewModel;
    private ViewDataBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(ChartViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false);
        binding.setLifecycleOwner(this.getViewLifecycleOwner());
        binding.setVariable(BR.viewModel, viewModel);

        return binding.getRoot();
    }
}