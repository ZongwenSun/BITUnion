package com.szw.mvp.presenter;

import android.os.Bundle;

import com.szw.mvp.view.IView;

/**
 * Created by szw on 16-2-3.
 */
public interface IPresenter<T extends IView> {
    Class<T> getViewClass();

    void preCreateView(Bundle savedInstanceState);

    void afterCreateView(Bundle savedInstanceState);
}
