package cn.edu.bit.szw.bitunion.network;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import cn.edu.bit.szw.bitunion.R;

/**
 * Created by szw on 15-10-11.
 */
public class BUError extends VolleyError {

    public BUError(NetworkResponse response) {
        super(response);
    }

    public BUError(Throwable throwable) {
        super(throwable);
    }

    public ErrorType getErrorType() {
        return mErrorType;
    }

    public void setErrorType(ErrorType mErrorType) {
        this.mErrorType = mErrorType;
    }

    private ErrorType mErrorType;


    public enum ErrorType {
        NOCONNECTION(R.string.network_errortip_noconnection,
                R.string.network_errorbtntip_noconnection),
        SERVER(R.string.network_errortip_server, R.string.network_errorbtntip_server),
        TIMEOUT(R.string.network_errortip_timeout, R.string.network_errorbtntip_timeout),
        PARSE(R.string.network_errortip_parse, R.string.network_errorbtntip_parse),
        CUSTOM(R.string.network_errortip_custom, R.string.network_errorbtntip_custom),
        UNKNOW(R.string.network_errortip_unknow, R.string.network_errorbtntip_unknow),
        LOGIN(R.string.network_errortip_login, R.string.network_errorbtntip_login);

        private int mErrorTipRes;
        private int mErrorBtnRes;

        ErrorType(int errorTipRes, int errorBtnRes) {
            mErrorTipRes = errorTipRes;
            mErrorBtnRes = errorBtnRes;
        }

        public int getErrorTipRes() {
            return mErrorTipRes;
        }

        public int getErrorBtnRes() {
            return mErrorBtnRes;
        }
    }
}
