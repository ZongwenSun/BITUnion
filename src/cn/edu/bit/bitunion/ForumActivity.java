package cn.edu.bit.bitunion;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import cn.edu.bit.bitunion.entities.Forum;
import cn.edu.bit.bitunion.widgets.SubForumListAdapter;

public class ForumActivity extends BaseActivity {
	private Forum mForum;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_forum);
		mForum = (Forum) getIntent().getSerializableExtra("forum");
		initUI();
	}

	private void initUI() {
		if (mForum.getSubForumList() != null && mForum.getSubForumList().size() > 0) {
			findViewById(R.id.subforum_layout).setVisibility(View.VISIBLE);
			ListView subForumListView = (ListView) findViewById(R.id.subforum_list);
			subForumListView.setAdapter(new SubForumListAdapter(ForumActivity.this, mForum.getSubForumList()));
		}

	}
}
