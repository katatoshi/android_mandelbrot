package com.katatoshi.androidmandelbrot.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableField;

import com.katatoshi.androidmandelbrot.BR;
import com.katatoshi.androidmandelbrot.model.MainModel;

/**
 * メインの view model。
 */
public class MainViewModel {

    final public ObservableField<String> sampleText = new ObservableField<>();

    private int counter = 0;

    /**
     * Model から同期します。
     */
    public void refresh() {
        sampleText.set(MainModel.getInstance().getSampleText());
    }

    public void updateSampleText() {
        counter++;
        MainModel.getInstance().setSampleText("Hello World! (" + counter + ")");
    }


    //region OnPropertyChangedCallback
    public void addPropertyChangedCallbackToModel() {
        addMainModelPropertyChangedCallbackToModel();
    }

    public void removePropertyChangedCallbackToModel() {
        removeMainModelPropertyChangedCallbackToModel();
    }


    //region
    private Observable.OnPropertyChangedCallback onMainModelPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == BR.sampleText) {
                sampleText.set(MainModel.getInstance().getSampleText());
                return;
            }
        }
    };

    private void addMainModelPropertyChangedCallbackToModel() {
        MainModel.getInstance().addOnPropertyChangedCallback(onMainModelPropertyChangedCallback);
    }

    private void removeMainModelPropertyChangedCallbackToModel() {
        MainModel.getInstance().removeOnPropertyChangedCallback(onMainModelPropertyChangedCallback);
    }
    //endregion
    //endregion
}
