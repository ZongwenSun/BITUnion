package cn.edu.bit.szw.bitunion.widgets;

import android.content.Context;
import android.widget.AbsListView;

/**
 * Created by szw on 15-10-14.
 */
public class PageController implements AbsListView.OnScrollListener {
    public static final int UNDEFINED = -1;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public boolean mNeedNextPage;
    public int mPageIndex;
    public int mPageSize = DEFAULT_PAGE_SIZE; // 可以修改
    private int nextFrom;
    private int nextTo;
    private int maxSize = UNDEFINED;
    private Runnable mCallback;
    private boolean mIsLastItem;
    private boolean mOnlyOnePage;
    private Context mContext;


    public PageController(Runnable callback, Context context) {
        mPageIndex = 1;
        mNeedNextPage = false;
        mCallback = callback;
        nextFrom = 0;
        nextTo = nextFrom + mPageSize;

        mIsLastItem = false;
        mOnlyOnePage = false;
        mContext = context;
    }

    public void nextPage() {
        // 只要翻页了，就不需要从数据库取数据，只从服务器取数据
        if (mNeedNextPage) {
            if (maxSize != UNDEFINED) {
                nextFrom = nextTo;
                if (nextFrom >= maxSize) {
                    mNeedNextPage = false;
                    return;
                }
                nextTo += mPageSize;
                if (nextTo > maxSize) {
                    nextTo = maxSize;
                }
                mPageIndex++;
            }
            else {
                nextFrom = nextTo;
                nextTo += mPageSize;
                mPageIndex++;
            }

        }
    }

    public void setMaxSize(int max) {
        maxSize = max;
        if (nextFrom >= max) {
            mNeedNextPage = false;
        }
        if (nextTo > max) {
            nextTo = max;
        }
    }

    public void resetPage() {
        mPageIndex = 1;
    }

    public int getPageIndex() {
        return mPageIndex;
    }

    public int getNextFrom() {
        return nextFrom;
    }

    public int getNextTo(){
        return nextTo;
    }
    public boolean isFirstPage() {
        return mPageIndex == 1;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        mIsLastItem = (totalItemCount == firstVisibleItem + visibleItemCount);
        mOnlyOnePage = (totalItemCount == visibleItemCount);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            if (mIsLastItem && mCallback != null && !mOnlyOnePage && mNeedNextPage) {
                mCallback.run();
            }
        }
    }
}