package com.szw.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.szw.mvp.helper.GenericHelper;
import com.szw.mvp.view.IView;

/**
 * Created by szw on 16-2-3.
 */
public abstract class ActivityPresenter<T extends IView> extends Activity implements IPresenter<T>{
    protected T mView;

    public ActivityPresenter() {
        try {
            mView = getViewClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IView error" , e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IView error", e);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preCreateView(savedInstanceState);
        mView.create(getLayoutInflater(), null, savedInstanceState);
        mView.initWidget();
        setContentView(mView.getRootView());
        afterCreateView(savedInstanceState);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mView = null;
    }

    @Override
    public Class<T> getViewClass() {
        return GenericHelper.getViewClass(getClass());
    }

    @Override
    public void afterCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void preCreateView(Bundle savedInstanceState) {

    }
}
