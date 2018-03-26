package frameglide.burjal.com.frame_glide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import frameglide.burjal.com.frame_glide.model.VideoItem;
import glide.video.frame.model.VideoFrame;

public class VideoDetailActivity extends BaseActivity {
    private static final String TAG = "VideoDetailActivity";

    private VideoItem videoItem;

    private ImageView ivFrame;
    private TextView tvInfo;
    private SeekBar seekBar;
    private TextView tvSeekBar;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updatePercent(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.i(TAG, "[onStartTrackingTouch]");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i(TAG, "[onStopTrackingTouch]");
        }
    };

    public static void openVideoDetail(Context context, VideoItem data) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("video", (Parcelable) data);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        setBackEnable();

        Intent intent = getIntent();
        if (null == intent) {
            finish();
        } else {
            videoItem = intent.getParcelableExtra("video");
        }

        initView();
        initData();
    }

    private void initData() {
        tvInfo.setText("Info:\n" + videoItem);

        updatePercent(0);
    }

    private void initView() {
        tvInfo = findViewById(R.id.tv_info);
        ivFrame = findViewById(R.id.iv_frame);
        seekBar = findViewById(R.id.seek_bar);
        tvSeekBar = findViewById(R.id.tv_seekbar);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivFrame.getLayoutParams();
        layoutParams.width = videoItem.width;
        layoutParams.height = videoItem.height;

        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    private void updatePercent(int progress) {
        if (null == videoItem) {
            tvSeekBar.setText(String.format("progress=%d; time=%d", progress, 0));
            return;
        }
        tvSeekBar.setText(String.format("progress=%d; time=%d", progress, (int) ((progress / 100f) * videoItem.duration)));

        Glide.with(this)
                .load(new VideoFrame.Builder()
                        .path(videoItem.videoPath)
                        .percent(progress)
                        .build())
                .into(ivFrame);
    }
}
