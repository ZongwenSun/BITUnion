package cn.edu.bit.szw.bitunion.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.Forum;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.tools.LogUtils;

public class ForumExpandableListAdapter extends BaseExpandableListAdapter {
    private List<Forum> mForumList;
    private LayoutInflater mInflater;
    protected boolean mDataValid;

    public ForumExpandableListAdapter(Context context) {
        mDataValid = false;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateData(List<Forum> data) {
        if (data != null) {
            mDataValid = true;
            mForumList = data;
            notifyDataSetChanged();
        } else {
            mDataValid = false;
            notifyDataSetInvalidated();
        }
    }

    @Override
    public int getGroupCount() {
        if (mDataValid && mForumList != null) {
            return mForumList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mDataValid && mForumList != null && mForumList.get(groupPosition) != null) {
            return mForumList.get(groupPosition).subForumList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mDataValid && mForumList != null) {
            return mForumList.get(groupPosition);
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mDataValid && mForumList != null && mForumList.get(groupPosition) != null) {
            return mForumList.get(groupPosition).subForumList.get(childPosition);
        } else {
            return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        if (mDataValid) {
            return groupPosition;
        } else {
            return 0;
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (mDataValid) {
            return childPosition;
        } else {
            return 0;
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (!mDataValid) {
            throw new IllegalStateException(
                    "this should only be called when the data is valid");
        }
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.forumlistview_group, null);
        }
        TextView groupNameText = (TextView) view.findViewById(R.id.forum_group_name_text);
        ImageView arrowImg = (ImageView) view.findViewById(R.id.forum_group_arrow_img);
        groupNameText.setText(mForumList.get(groupPosition).name);
        if (isExpanded) {
            arrowImg.setBackgroundResource(R.drawable.arrow_down);
        } else {
            arrowImg.setBackgroundResource(R.drawable.arrow_right);
        }
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (!mDataValid) {
            throw new IllegalStateException(
                    "this should only be called when the data is valid");
        }
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.forumlistview_children, null);
        }

        ImageView forumImg = (ImageView) view.findViewById(R.id.img);
        TextView childForumNameText = (TextView) view.findViewById(R.id.forum_group_name_text);
        Forum forum = mForumList.get(groupPosition).subForumList.get(childPosition);

        ImageLoader.getInstance().displayImage(GlobalUrls.getImageUrl(forum.icon), forumImg);
        childForumNameText.setText(forum.name);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
