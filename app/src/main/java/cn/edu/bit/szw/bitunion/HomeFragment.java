package cn.edu.bit.szw.bitunion;

import android.os.Bundle;

import com.szw.mvp.presenter.FragmentActivityPresenter;

import cn.edu.bit.szw.bitunion.view.HomeView;
import cn.edu.bit.szw.bitunion.widgets.HomeTabAdapter;



public class HomeFragment extends FragmentActivityPresenter<HomeView> {

	@Override
	public void afterCreateView(Bundle savedInstanceState) {
		super.afterCreateView(savedInstanceState);
		mView.setAdapter(new HomeTabAdapter(getSupportFragmentManager()));

	}

}
