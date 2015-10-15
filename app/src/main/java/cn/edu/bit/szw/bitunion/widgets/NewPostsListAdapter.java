package cn.edu.bit.szw.bitunion.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.NewPost;

public class NewPostsListAdapter extends BaseAdapter {
	private List<NewPost> mData;
	private final Context context;
	private final LayoutInflater inflater;

	public NewPostsListAdapter(Context context, List<NewPost> data) {
		this.mData = data;
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
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
			rowView = inflater.inflate(R.layout.new_posts_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.postNameText = (TextView) rowView.findViewById(R.id.post_name_textview);
			holder.forumNameText = (TextView) rowView.findViewById(R.id.post_message_tv);
			holder.authorNameText = (TextView) rowView.findViewById(R.id.post_author_name_textview);
			holder.replyCountText = (TextView) rowView.findViewById(R.id.reply_count_textview);
			rowView.setTag(holder);
		}
		NewPost post = mData.get(position);
		ViewHolder viewHolder = (ViewHolder) rowView.getTag();
		viewHolder.postNameText.setText(post.pname);
		viewHolder.forumNameText.setText(post.fname);
		viewHolder.authorNameText.setText(post.author);
		viewHolder.replyCountText.setText(post.tid_sum);
		return rowView;
	}

	static class ViewHolder {
		public TextView postNameText;
		public TextView forumNameText;
		public TextView authorNameText;
		public TextView replyCountText;
	}
}
