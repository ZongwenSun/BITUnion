package cn.edu.bit.szw.bitunion.widgets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.edu.bit.szw.bitunion.ForumListFragment;
import cn.edu.bit.szw.bitunion.NewPostsFragment;

/**
 * Created by szw on 16-2-4.
 */
public class HomeTabAdapter  extends FragmentPagerAdapter {

    public HomeTabAdapter(FragmentManager fragmentManager) {
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