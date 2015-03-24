package cn.edu.bit.bitunion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TabPageIndicator;

public class HomeActivity extends BaseActivity {
	private TabsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setTitle("北理联盟");
		initUI();
	}

	private void initUI() {
		// Set the pager with an adapter
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
		// Bind the title indicator to the adapter

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
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
