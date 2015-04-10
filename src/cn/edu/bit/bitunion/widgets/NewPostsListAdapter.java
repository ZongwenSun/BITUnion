package cn.edu.bit.bitunion.widgets;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.edu.bit.bitunion.R;
import cn.edu.bit.bitunion.entities.NewPost;

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
			holder.forumNameText = (TextView) rowView.findViewById(R.id.forum_name_textview);
			holder.authorNameText = (TextView) rowView.findViewById(R.id.post_author_name_textview);
			holder.replyCountText = (TextView) rowView.findViewById(R.id.reply_count_textview);
			rowView.setTag(holder);
		}
		NewPost post = mData.get(position);
		ViewHolder viewHolder = (ViewHolder) rowView.getTag();
		viewHolder.postNameText.setText(post.getPname());
		viewHolder.forumNameText.setText(post.getFname());
		viewHolder.authorNameText.setText(post.getAuthor());
		viewHolder.replyCountText.setText(post.getTid_sum());
		return rowView;
	}

	static class ViewHolder {
		public TextView postNameText;
		public TextView forumNameText;
		public TextView authorNameText;
		public TextView replyCountText;
	}
}
