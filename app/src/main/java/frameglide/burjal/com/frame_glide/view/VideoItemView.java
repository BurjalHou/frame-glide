package frameglide.burjal.com.frame_glide.view;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import frameglide.burjal.com.frame_glide.R;
import frameglide.burjal.com.frame_glide.VideoDetailActivity;
import frameglide.burjal.com.frame_glide.model.VideoItem;
import glide.video.frame.model.VideoFrame;

/**
 * Created by burjal on 2018/3/10.
 */

public class VideoItemView extends RelativeLayout {
    private static final String TAG = "VideoItemView";

    private ImageView ivCover;
    private TextView tvDuration;

    private VideoItem mData;

    public VideoItemView(Context context) {
        this(context, null);
    }

    public VideoItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View rootView = inflate(context, R.layout.view_video_item, this);

        ivCover = rootView.findViewById(R.id.iv_cover);
        tvDuration = rootView.findViewById(R.id.tv_duration);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDetailActivity.openVideoDetail(getContext(), mData);
            }
        });
    }

    public void bind(VideoItem data) {
        this.mData = data;

        refresh();
    }

    private void refresh() {
        if (null == mData) {
            return;
        }

        Glide.with(ivCover)
                .load(new VideoFrame.Builder()
                        .path(mData.videoPath)
                        .percent(0).build())
                .into(ivCover);

        tvDuration.setText(DateUtils.formatElapsedTime(mData.duration / 1000));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
