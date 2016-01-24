package cn.edu.bit.szw.bitunion.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.edu.bit.szw.bitunion.R;

/**
 * Created by szw on 15-10-11.
 */
public abstract class BaseFragment extends Fragment {
    protected View mTitleBar;
    protected TextView mTitle;
    protected ImageView mLeft;
    protected FrameLayout mContent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        mTitleBar = view.findViewById(R.id.base_fragment_titlebar);
        mTitle = (TextView) view.findViewById(R.id.base_titlebar_title);
        mLeft = (ImageView) view.findViewById(R.id.base_titlebar_left);
        mContent = (FrameLayout) view.findViewById(R.id.base_fragment_content);
        View contentView = createContentView(inflater, savedInstanceState);
        //ButterKnife.bind(contentView);
        FrameLayout.LayoutParams contentLp = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        if (contentView != null) {
            mContent.addView(contentView, contentLp);
        }
        return view;
    }


    protected abstract View createContentView(LayoutInflater inflater, Bundle savedInstanceState);

    protected void startFragment(Class<? extends Fragment> cls, Bundle bundle) {
        ((BaseActivity)getActivity()).startFragment(cls, bundle);
    }

    protected void startAndFinishSelf(final Class<? extends Fragment> cls, Bundle bundle) {
        ((BaseActivity)getActivity()).startAndFinishSelf(cls, bundle);
    }

    protected void startFragmentForResult(final Class<? extends Fragment> cls, Bundle bundle, int requestCode) {
        ((BaseActivity) getActivity()).startFragmentForResult(cls, bundle, requestCode);
    }

    public abstract void reload(int type);
}
