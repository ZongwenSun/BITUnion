package cn.edu.bit.szw.bitunion.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cn.edu.bit.szw.bitunion.R;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by szw on 16-2-3.
 */
public class LoginView extends RootView {
    EditText mUsernameEdit;
    EditText mPasswordEdit;
    Button mLoginBtn;

    @Override
    public View createContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, null);
        return view;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(getString(R.string.login));
        mUsernameEdit = get(R.id.login_username_edit);
        mPasswordEdit = get(R.id.login_password_edit);
        mLoginBtn = get(R.id.login_sign_in_button);
    }

    public String getUserName() {
        return mUsernameEdit.getText().toString();
    }

    public String getPassword() {
        return mPasswordEdit.getText().toString();
    }

    public Observable<Void> clickLogin() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(final Subscriber<? super Void> subscriber) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }
                };
                mLoginBtn.setOnClickListener(listener);
            }
        });
    }

    public void emptyUsernameOrPassword() {
        toast(getContext().getString(R.string.username_and_pass_should_not_be_empty));
    }

}
