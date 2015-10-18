package cn.edu.bit.szw.bitunion.widgets;

import java.lang.ref.SoftReference;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import cn.edu.bit.szw.bitunion.R;
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.entities.Quote;
import cn.edu.bit.szw.bitunion.tools.StringUtils;

public class PostDetailAdapter extends BaseAdapter {
	HashMap<String, SoftReference<Drawable>> mDrawableCache;
	private static int ITEM_VIEW_TYPE_AUTHOR = 0;
	private static int ITEM_VIEW_TYPE_REPLY = 1;

	private List<Post> mData;
	final Context context;
	private final LayoutInflater inflater;

	private String authorId;

	public PostDetailAdapter(Context context, List<Post> data) {
		this.mData = data;
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mDrawableCache = new HashMap<>();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return ITEM_VIEW_TYPE_AUTHOR;
		} else {
			return ITEM_VIEW_TYPE_REPLY;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Post post = mData.get(position);
		if (getItemViewType(position) == ITEM_VIEW_TYPE_AUTHOR) {
			view = inflater.inflate(R.layout.post_subject, null);
			ImageView avatarIV = (ImageView) view.findViewById(R.id.avadar_iv);
			TextView authorTv = (TextView) view.findViewById(R.id.author_tv);
			TextView timeTv = (TextView) view.findViewById(R.id.time_tv);
			TextView subjectTv = (TextView) view.findViewById(R.id.post_subject_tv);
			TextView messageTv = (TextView) view.findViewById(R.id.post_message_tv);

			authorTv.setText(post.author);
			subjectTv.setText(post.subject);
			timeTv.setText(getTime(post.dateline));
			messageTv.setText(Html.fromHtml(post.message));
			authorId = post.authorid;
		} else {
			ViewHolder holder;
			if (view == null) {
				view = inflater.inflate(R.layout.post_details_item, null);
				holder = new ViewHolder();
				holder.avatarIv = (ImageView) view.findViewById(R.id.avadar_iv);
				holder.authorTv = (TextView) view.findViewById(R.id.author_tv);
				holder.authorLabelTv = (TextView) view.findViewById(R.id.author_label_tv);
				holder.floorNoTv = (TextView) view.findViewById(R.id.floorNo_tv);
				holder.timeTv = (TextView) view.findViewById(R.id.time_tv);
				holder.subjectTv = (TextView) view.findViewById(R.id.post_subject_tv);
				holder.quoteTv = (TextView) view.findViewById(R.id.post_quote_tv);
				holder.messageTv = (TextView) view.findViewById(R.id.post_message_tv);
				view.setTag(holder);
			}

			holder = (ViewHolder) view.getTag();
			holder.authorTv.setText(post.author);
			if (TextUtils.isEmpty(post.subject)) {
				holder.subjectTv.setVisibility(View.GONE);
			} else {
				holder.subjectTv.setVisibility(View.VISIBLE);
				holder.subjectTv.setText(post.subject);
			}

			holder.floorNoTv.setText(context.getString(R.string.floor_format, position + 1));
			holder.timeTv.setText(getTime(post.dateline));

			if (post.quotes != null && post.quotes.size() > 0) {
				holder.quoteTv.setVisibility(View.VISIBLE);
				StringBuilder quoteStrBuilder = new StringBuilder();
				for (int i = 0; i < post.quotes.size(); i++) {
					Quote quote = post.quotes.get(i);
					if (i > 0) {
						quoteStrBuilder.append("<br/><br/>");
					}
					quoteStrBuilder.append("&nbsp;&nbsp;&nbsp;")
							.append(quote.author)
							.append(":&nbsp;")
							.append(quote.content);
				}
				String quoteStr = quoteStrBuilder.toString();
				holder.quoteTv.setText(Html.fromHtml(quoteStr, new ImageGetterNet(holder.quoteTv, quoteStr), null));
			} else {
				holder.quoteTv.setVisibility(View.GONE);
			}
			holder.messageTv.setText(Html.fromHtml(post.message, new ImageGetterNet(holder.messageTv, post.message), null));
			if (TextUtils.equals(authorId, post.authorid)) {
				holder.authorLabelTv.setVisibility(View.VISIBLE);
			} else {
				holder.authorLabelTv.setVisibility(View.GONE);
			}

		}

		return view;
	}

	private String getTime(String time) {
		Date date = new Date(Long.valueOf(time) * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static class ViewHolder {
		public ImageView avatarIv;
		public TextView authorTv;
		public TextView authorLabelTv;
		public TextView subjectTv;
		public TextView quoteTv;
		public TextView messageTv;
		public TextView timeTv;
		public TextView replyIv;
		public TextView floorNoTv;
	}


	static class ImageDownloadData {
		private String imageSource;
		private String message;
		private TextView textView;

		public ImageDownloadData(String imageSource, String message,
								 TextView textView) {
			this.imageSource = imageSource;
			this.message = message;
			this.textView = textView;
		}

		public String getImageSource() {
			return imageSource;
		}

		public String getMessage() {
			return message;
		}

		public TextView getTextView() {
			return textView;
		}
	}
	class ImageGetterNet implements Html.ImageGetter {
		TextView textView;
		String content;
		public ImageGetterNet(TextView textView, String content){
			this.textView = textView;
			this.content = content;
		}
		@Override
		public Drawable getDrawable(String source) {
			if (mDrawableCache.containsKey(source)) {
				return mDrawableCache.get(source).get();
			}
			else {

				new GetImageTask(new ImageDownloadData(source,content,textView)).execute();
				return context.getResources().getDrawable(R.drawable.default_no_picture);
			}
		}
	}

	class ImageGetterInCache implements Html.ImageGetter{
		@Override
		public Drawable getDrawable(String source) {
			if (mDrawableCache.containsKey(source)) {
				return mDrawableCache.get(source).get();
			}
			return  null;
		}
	}

	class GetImageTask extends AsyncTask<String, Void, Drawable> {
		ImageDownloadData mData;
		public GetImageTask(ImageDownloadData data) {
			mData = data;
		}
		@Override
		protected Drawable doInBackground(String... params) {
			Bitmap bitmap = ImageLoader.getInstance().loadImageSync(mData.getImageSource());
			BitmapDrawable drawable = new BitmapDrawable(bitmap);
			return drawable;
		}

		@Override
		protected void onPostExecute(Drawable drawable) {
			mData.getTextView().setText(Html.fromHtml(mData.getImageSource(), new ImageGetterInCache(), null));
		}
	}
}
