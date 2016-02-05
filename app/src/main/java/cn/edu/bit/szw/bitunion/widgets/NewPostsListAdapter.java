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

public class NewPostsListAdapter extends BaseDataAdapter<NewPost> {

	public NewPostsListAdapter(Context context) {
		super(context);
	}

	@Override
	public void bindView(View view, int position, NewPost post) {
		TextView postNameText = (TextView) view.findViewById(R.id.post_name_textview);
		TextView forumNameText = (TextView) view.findViewById(R.id.post_message_tv);
		TextView authorNameText = (TextView) view.findViewById(R.id.post_author_name_textview);
		TextView replyCountText = (TextView) view.findViewById(R.id.reply_count_textview);

		postNameText.setText(post.pname);
		forumNameText.setText(post.fname);
		authorNameText.setText(post.author);
		replyCountText.setText(post.tid_sum);

	}

	@Override
	public View newView(Context context, NewPost data, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.new_posts_list_item, null);
	}

}
