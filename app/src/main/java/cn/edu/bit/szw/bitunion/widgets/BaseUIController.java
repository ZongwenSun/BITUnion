package cn.edu.bit.szw.bitunion.widgets;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.base.BaseFragment;
import cn.edu.bit.szw.bitunion.network.BUError;

/**
 * Created by szw on 15-10-11.
 */
public abstract class BaseUIController implements Response.Listener<JSONObject> {

    private static final String TAG = "BaseUIController";
    private static final boolean DEBUG = false;

    private WeakReference<BaseFragment> mBaseFragment;
    private JSONObject mData;
    private LoadingView mLoadingView;

    // private UIControlListener mCallback;

    public BaseUIController(BaseFragment fragment, LoadingView loadingView) {
        mBaseFragment = new WeakReference<BaseFragment>(fragment);
        mLoadingView = loadingView;
    }

    protected abstract void onRefreshUI(JSONObject response);

    @Override
    public void onStart() {
        if (DEBUG) {
            Log.d(TAG, "onStart");
        }
        if (mLoadingView != null) {
            mLoadingView.startLoading(null == mData);
        }
    }

    @Override
    public void onSuccess(JSONObject response, boolean isFromCache) {
        if (DEBUG) {
            Log.d(TAG, "onSuccess >> isFromCache : " + isFromCache);
        }
        mData = response;
        onRefreshUI(response);
    }

    @Override
    public void onError(VolleyError error) {
        if (DEBUG) {
            Log.d(TAG, "onError >> " + error.toString());
        }
        if (error instanceof BUError) {
            BUError apiError = (BUError) error;
            BUError.ErrorType errorType = apiError.getErrorType();
            switch (errorType) {
                case NOCONNECTION:
                    if (mData == null) {
                        if (mLoadingView != null) {
                            mLoadingView.showErrorWithActionButton(errorType.getErrorTipRes(),
                                    errorType.getErrorBtnRes(),
                                    new Handler.Callback() {
                                        @Override
                                        public boolean handleMessage(Message msg) {
                                            BaseFragment fragment = mBaseFragment.get();
                                            if (fragment != null) {
                                                fragment.startActivity(
                                                        new Intent(Settings.ACTION_WIFI_SETTINGS));
                                            }
                                            return true;
                                        }
                                    });
                        }
                    } else {
                        if (mLoadingView != null) {
                            mLoadingView.showErrorAsToast(errorType.getErrorTipRes());
                        }
                    }
                    break;
                case SERVER:
                case TIMEOUT:
                case PARSE:
                case UNKNOW:
                    if (mData == null) {
                        if (mLoadingView != null) {
                            mLoadingView.showErrorWithActionButton(errorType.getErrorTipRes(),
                                    errorType.getErrorBtnRes(),
                                    new Handler.Callback() {
                                        @Override
                                        public boolean handleMessage(Message msg) {
                                            BaseFragment fragment = mBaseFragment.get();
                                            if (fragment != null) {
                                                fragment.reload(0);
                                            }
                                            return true;
                                        }
                                    });
                        }
                    } else {
                        if (mLoadingView != null) {
                            mLoadingView.showErrorAsToast(errorType.getErrorTipRes());
                        }
                    }
                    break;

                default:
                    if (mData == null) {
                        if (mLoadingView != null) {
                            mLoadingView.showErrorWithActionButton(errorType.getErrorTipRes(),
                                    errorType.getErrorBtnRes(),
                                    new Handler.Callback() {
                                        @Override
                                        public boolean handleMessage(Message msg) {
                                            BaseFragment fragment = mBaseFragment.get();
                                            if (fragment != null) {
                                                fragment.reload(0);
                                            }
                                            return true;
                                        }
                                    });
                        }
                    } else {
                        if (mLoadingView != null) {
                            mLoadingView.showErrorAsToast(errorType.getErrorTipRes());
                        }
                    }
            }
        } else {
            // 数据解析出错
            if (mData == null) {
                if (mLoadingView != null) {
                    mLoadingView.showErrorWithActionButton(R.string.network_errortip_unknow,
                            R.string.network_errorbtntip_unknow,
                            new Handler.Callback() {
                                @Override
                                public boolean handleMessage(Message msg) {
                                    BaseFragment fragment = mBaseFragment.get();
                                    if (fragment != null) {
                                        fragment.reload(0);
                                    }
                                    return true;
                                }
                            });
                }
            } else {
                if (mLoadingView != null) {
                    mLoadingView.showErrorAsToast(R.string.network_errortip_unknow);
                }
            }
        }
    }


    @Override
    public void onFinish() {
        if (DEBUG) {
            Log.d(TAG, "onFinish >> ");
        }
        if (mLoadingView != null) {
            mLoadingView.stopLoading();
        }
    }
}
