package com.katatoshi.androidmandelbrot.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.katatoshi.androidmandelbrot.R;
import com.katatoshi.androidmandelbrot.databinding.ActivityMainBinding;
import com.katatoshi.androidmandelbrot.viewmodel.MainViewModel;

/**
 * メインの Activity
 */
public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new MainViewModel();
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.addPropertyChangedCallbackToModel();

        viewModel.refresh();
    }

    @Override
    protected void onPause() {
        viewModel.removePropertyChangedCallbackToModel();

        super.onPause();
    }
}
