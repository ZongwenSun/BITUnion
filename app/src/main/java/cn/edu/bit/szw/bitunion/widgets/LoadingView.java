package cn.edu.bit.szw.bitunion.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.IPullToRefresh;

import java.lang.ref.WeakReference;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;

/**
 * Created by szw on 15-10-11.
 */
public class LoadingView extends RelativeLayout {

    protected View mProgressContainer;
    protected View mErrorContainer;
    protected TextView mError;
    protected Button mButton;

    protected View mCustomErrorView;

    protected WeakReference<IPullToRefresh<?>> mPullToRefreshRef;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public LoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.loading_view, this, true);
        mProgressContainer = findViewById(R.id.loadingview_progress_container);
        mErrorContainer = findViewById(R.id.loadingview_txtcontainer);
        mError = (TextView) findViewById(R.id.loadingview_txterror);
        mButton = (Button) findViewById(R.id.loadingview_button);

        mProgressContainer.setVisibility(GONE);
        mErrorContainer.setVisibility(GONE);
    }
    /**
     * 如果和下拉刷新控件一起使用，调用此方法设置。
     *
     * @param pullToRefresh SimplePullToRefreshLayout 控件
     */
    public void bindWithPullToRefreshLayout(IPullToRefresh<?> pullToRefresh) {
        mPullToRefreshRef = new WeakReference<IPullToRefresh<?>>(pullToRefresh);
    }

    /**
     * 开始load数据时调用。
     *
     * @param showProgress 是否显示转圈圈
     */
    public void startLoading(boolean showProgress) {
        mProgressContainer.setVisibility(showProgress ? VISIBLE : GONE);
        mErrorContainer.setVisibility(GONE);
        if (mCustomErrorView != null) {
            mCustomErrorView.setVisibility(GONE);
        }
    }

    /**
     * 数据load结束时调用。
     */
    public void stopLoading() {
        mProgressContainer.setVisibility(GONE);

        if (mPullToRefreshRef != null) {
            IPullToRefresh<?> mPullToRefresh = mPullToRefreshRef.get();
            if (mPullToRefresh != null) {
                mPullToRefresh.onRefreshComplete();
            }
        }
    }

    public void loadingSuccess() {
        mErrorContainer.setVisibility(GONE);
        if (mCustomErrorView != null) {
            mCustomErrorView.setVisibility(GONE);
        }
    }

    /**
     * 以toast方式报错。
     *
     * @param msgResId
     */
    public void showErrorAsToast(int msgResId) {
        if (null == getContext()) {
            return;
        }
        showErrorAsToast(getContext().getString(msgResId));
    }

    /**
     * 以toast方式报错。
     *
     * @param msg
     */
    public void showErrorAsToast(String msg) {
        showError(msg, true, null, null);
    }

    /**
     * 以界面方式报错。
     *
     * @param msgResId
     */
    public void showErrorFormally(int msgResId) {
        if (null == getContext()) {
            return;
        }
        showError(getContext().getString(msgResId), false, null, null);
    }

    /**
     * 以界面方式报错。
     *
     * @param msg
     */
    public void showErrorFormally(String msg) {
        showError(msg, false, null, null);
    }

    /**
     * 以界面方式报错，并提供错误处理按钮（如重新加载）。
     *
     * @param msgResId
     * @param btnTextResId
     * @param btnCallback
     */
    public void showErrorWithActionButton(int msgResId, int btnTextResId, final Handler.Callback btnCallback) {
        if (null == getContext()) {
            return;
        }
        showErrorWithActionButton(getContext().getString(msgResId),
                getContext().getString(btnTextResId),
                btnCallback);
    }

    /**
     * 以界面方式报错，并提供错误处理按钮（如重新加载）。
     *
     * @param msg
     * @param btnText
     * @param btnCallback
     */
    public void showErrorWithActionButton(String msg, String btnText, final Handler.Callback btnCallback) {
        if (null == getContext()) {
            return;
        }
        if (TextUtils.isEmpty(btnText)) {
           // btnText = getContext().getString(R.string.try_again);
        }
        showError(msg, false, btnText, btnCallback);
    }

    private void showError(String msg, boolean useToast, String btnText, final Handler.Callback btnCallback) {
        mProgressContainer.setVisibility(GONE);
        if (useToast) {
            mErrorContainer.setVisibility(GONE);
            ToastHelper.show(msg);
        } else {
            mErrorContainer.setVisibility(VISIBLE);
            if (mCustomErrorView != null) {
                mErrorContainer.setVisibility(GONE);
            }
            mError.setText(msg);
            if (btnCallback != null) {
                mButton.setVisibility(VISIBLE);
                mButton.setText(btnText);
                mButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnCallback.handleMessage(null);
                    }
                });
            } else {
                mButton.setVisibility(GONE);
            }
        }
    }

    public void setErrorView(View view) {
        if (view == null) {
            return;
        }

        if (mCustomErrorView != null) {
            mCustomErrorView = null;
        }
        mCustomErrorView = view;
        mProgressContainer.setVisibility(GONE);
        mErrorContainer.setVisibility(GONE);
        mButton.setVisibility(GONE);
        addView(view, 0);
    }

}
