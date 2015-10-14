package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import cn.edu.bit.szw.bitunion.base.BaseFragment;
import cn.edu.bit.szw.bitunion.widgets.viewpageindicator.TabPageIndicator;


public class HomeFragment extends BaseFragment {
	ViewPager mPager;

	@Override
	protected View createContentView(LayoutInflater inflater, Bundle savedInstanceState) {
		mTitle.setText(getString(R.string.app_name));
		View view = inflater.inflate(R.layout.activity_home, null);
		mPager = (ViewPager) view.findViewById(R.id.pager);
		mPager.setAdapter(new TabsAdapter(getFragmentManager()));
		// Bind the title indicator to the adapter

		TabPageIndicator indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		return view;
	}

	@Override
	public void reload(int type) {

	}


	class TabsAdapter extends FragmentPagerAdapter {

		public TabsAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int pos) {
			// TODO Auto-generated method stub
			switch (pos) {
			case 0:
				return new NewPostsFragment();
			case 1:
				return new ForumListFragment();
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				return "最新";
			case 1:
				return "全部";
			default:
				return null;
			}
		}

	}
}
