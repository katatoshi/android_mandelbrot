package com.katatoshi.androidmandelbrot.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.common.base.Objects;
import com.katatoshi.androidmandelbrot.BR;

/**
 * メインの model。
 */
public class MainModel extends BaseObservable {

    private MainModel() {
    }


    //region シングルトンインスタンスのプロパティ。
    private static MainModel instance;

    public static MainModel getInstance() {
        if (instance == null) {
            instance = new MainModel();
        }

        return instance;
    }
    //endregion


    //region サンプルテキストのプロパティ。
    private String sampleText = "Hello World!";

    @Bindable
    public String getSampleText() {
        return sampleText;
    }

    public void setSampleText(String sampleText) {
        if (Objects.equal(this.sampleText, sampleText)) {
            return;
        }

        this.sampleText = sampleText;
        notifyPropertyChanged(BR.sampleText);
    }
    //endregion
}
