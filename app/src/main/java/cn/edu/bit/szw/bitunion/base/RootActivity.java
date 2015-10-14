package cn.edu.bit.szw.bitunion.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;
/**
 * Created by szw on 15-10-11.
 */
public class RootActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(getContentView());
        installFragment();
    }

    protected View getContentView() {
        FrameLayout viewRoot = new FrameLayout(this);
        viewRoot.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        viewRoot.setId(android.R.id.primary);
        return viewRoot;
    }

    private void installFragment(){
        String fragClass = getIntent().getData().getHost();
        ClassLoader classLoader = getClassLoader();
        try {
            Fragment fragment = (Fragment) classLoader.loadClass(fragClass).newInstance();
            Bundle bundle = getIntent().getExtras();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(android.R.id.primary, fragment).commit();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
