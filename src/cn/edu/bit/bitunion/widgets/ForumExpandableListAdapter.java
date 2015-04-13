package cn.edu.bit.bitunion.widgets;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.bit.bitunion.R;
import cn.edu.bit.bitunion.entities.Forum;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.tools.LogUtils;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ForumExpandableListAdapter extends BaseExpandableListAdapter {
	private List<Forum> mForumList;
	private LayoutInflater mInflater;

	public ForumExpandableListAdapter(Context context, List<Forum> forumList) {
		mForumList = forumList;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mForumList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mForumList.get(groupPosition).getSubForumList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mForumList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mForumList.get(groupPosition).getSubForumList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		GroupHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.forumlistview_group, null);
			holder = new GroupHolder();
			holder.grouptext = (TextView) view.findViewById(R.id.listgroup);
			// Typeface typeface =
			// Typeface.createFromAsset(getActivity().getAssets(),
			// "fonts/xingkai.ttf");
			// holder.grouptext.setTypeface(typeface);
			view.setTag(holder);
		} else {
			holder = (GroupHolder) view.getTag();
		}
		holder.grouptext.setText(mForumList.get(groupPosition).getName());
		return view;

	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ChildHolder cholder;
		if (view == null) {
			view = mInflater.inflate(R.layout.forumlistview_children, null);
			cholder = new ChildHolder();
			cholder.imageView = (ImageView) view.findViewById(R.id.img);
			cholder.childtext = (TextView) view.findViewById(R.id.listgroup);
			view.setTag(cholder);
		} else {
			cholder = (ChildHolder) view.getTag();
		}
		Forum forum = mForumList.get(groupPosition).getSubForumList().get(childPosition);
		LogUtils.log(forum.getIcon());
		ImageLoader.getInstance().displayImage(GlobalUrls.getImageUrl(forum.getIcon()), cholder.imageView);
		cholder.childtext.setText(forum.getName());
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	static class GroupHolder {
		TextView grouptext;
	}

	static class ChildHolder {
		ImageView imageView;
		TextView childtext;
	}
}
