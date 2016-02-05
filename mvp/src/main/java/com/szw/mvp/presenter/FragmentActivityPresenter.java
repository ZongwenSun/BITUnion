package com.szw.mvp.presenter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.szw.mvp.helper.GenericHelper;
import com.szw.mvp.view.IView;

/**
 * Created by szw on 16-2-3.
 */
public class FragmentActivityPresenter<T extends IView> extends FragmentActivity implements IPresenter<T> {

    protected T mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preCreateView(savedInstanceState);

        try {
            mView = getViewClass().newInstance();
            mView.create(getLayoutInflater(), null, savedInstanceState);
            mView.initWidget();
            setContentView(mView.getRootView());
            afterCreateView(savedInstanceState);

        } catch(Exception e) {
            throw new RuntimeException("create IView error" , e);
        }
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
