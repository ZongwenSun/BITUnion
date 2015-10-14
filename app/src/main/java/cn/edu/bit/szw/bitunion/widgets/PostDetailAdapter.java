package cn.edu.bit.szw.bitunion.widgets;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.Post;

public class PostDetailAdapter extends BaseAdapter {

	private List<Post> mData;
	private final Context context;
	private final LayoutInflater inflater;

	public PostDetailAdapter(Context context, List<Post> data) {
		this.mData = data;
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		// TODO Auto-generated method stub
		// View rowView = convertView;

		if (position == 0) {
			View firstView = convertView;
			firstView = null;
			if (firstView == null) {
				firstView = inflater.inflate(R.layout.post_subject, null);
				ViewHolder holder = new ViewHolder();
				holder.postSubjectText = (TextView) firstView
						.findViewById(R.id.post_subject_textview);
				holder.forumNameText = (TextView) firstView
						.findViewById(R.id.forum_name_textview);
				holder.replyTotalButton = (Button) firstView
						.findViewById(R.id.reply_totol_button);
				firstView.setTag(holder);
			}
			Post post = mData.get(position);
			ViewHolder holder = (ViewHolder) firstView.getTag();
			holder.postSubjectText.setText(post.subject);
			holder.forumNameText.setText("论坛");
			convertView = firstView;
		}

		else {
			View otherView = convertView;
			otherView = null;
			if (otherView == null) {
				otherView = inflater.inflate(R.layout.post_details_item, null);
				ItemHolder holder = new ItemHolder();
				holder.postAuthorText = (TextView) otherView
						.findViewById(R.id.post_author);
				holder.postLevelText = (TextView) otherView
						.findViewById(R.id.post_level);
				holder.postReplyTimeText = (TextView) otherView
						.findViewById(R.id.post_reply_time);
				holder.postContentText = (TextView) otherView
						.findViewById(R.id.post_content);
				holder.postReplyButton = (Button) otherView
						.findViewById(R.id.post_reply_button);
				otherView.setTag(holder);
			}
			Post post = mData.get(position);
			ItemHolder holder = (ItemHolder) otherView.getTag();
			holder.postAuthorText.setText(post.author);
			holder.postLevelText.setText("楼");
			holder.postReplyTimeText.setText("00:00");
			holder.postContentText.setText(post.message);

			convertView = otherView;
		}

		return convertView;
	}

	static class ViewHolder {
		public TextView postSubjectText;
		public TextView forumNameText;
		public Button replyTotalButton;
	}

	static class ItemHolder {
		public TextView postAuthorText;
		public TextView postLevelText;
		public TextView postReplyTimeText;
		public TextView postContentText;
		public Button postReplyButton;
	}
}
