package cn.edu.bit.szw.bitunion.view;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.widgets.viewpageindicator.TabPageIndicator;

/**
 * Created by szw on 16-2-4.
 */
public class HomeView extends RootView{
    ViewPager mPager;
    TabPageIndicator indicator;
    @Override
    public View createContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        return view;
    }

    public void setAdapter(PagerAdapter adapter) {
        mPager.setAdapter(adapter);
        indicator.setViewPager(mPager);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(getString(R.string.app_name));
        mPager = get(R.id.home_viewpager);
        indicator = get(R.id.home_indicator);

    }
}
