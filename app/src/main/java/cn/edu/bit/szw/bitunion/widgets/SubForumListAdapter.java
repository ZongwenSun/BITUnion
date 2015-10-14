package cn.edu.bit.szw.bitunion.widgets;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.Forum;
import cn.edu.bit.szw.bitunion.tools.LogUtils;

public class SubForumListAdapter extends BaseAdapter {
	private List<Forum> mForums;
	private final LayoutInflater inflater;

	public SubForumListAdapter(Context context, List<Forum> forums) {
		this.mForums = forums;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mForums.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mForums.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		if (rowView == null) {
			rowView = inflater.inflate(R.layout.subforum_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.forumIcon = (ImageView) rowView.findViewById(R.id.subforum_icon);
			holder.desciptionTextView = (TextView) rowView.findViewById(R.id.subforum_descrip);
			holder.forumNameTextView = (TextView) rowView.findViewById(R.id.subforum_name);
			rowView.setTag(holder);
		}
		ViewHolder viewHolder = (ViewHolder) rowView.getTag();
		Forum forum = mForums.get(position);
		if (forum == null) {
			LogUtils.log("subForumAdapter", "froum is null");
		} else {
			viewHolder.forumNameTextView.setText(forum.name);
			viewHolder.desciptionTextView.setText(forum.description);
		}
		return rowView;
	}

	static class ViewHolder {
		ImageView forumIcon;
		TextView forumNameTextView;
		TextView desciptionTextView;
	}
}
