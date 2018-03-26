package frameglide.burjal.com.frame_glide;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import frameglide.burjal.com.frame_glide.model.VideoItem;
import frameglide.burjal.com.frame_glide.util.VideoUtil;
import frameglide.burjal.com.frame_glide.util.ViewUtil;
import frameglide.burjal.com.frame_glide.view.VideoItemView;
import glide.video.frame.model.VideoFrame;
import glide.video.frame.model.MediaStoreVideoFrameLoader;

/**
 * Created by burjal on 2018/3/9.
 */

public class MainActivity extends BaseActivity {

    private static final int CODE_PERMISSION_READ_STORAGE = 100;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        requestPermission();

        Glide.get(MainActivity.this)
                .getRegistry()
                .append(VideoFrame.class,
                        Bitmap.class,
                        new MediaStoreVideoFrameLoader.Factory(MainActivity.this));
    }

    private void initView() {
        mRecyclerView = findViewById(android.R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int padding = ViewUtil.dp2px(3);
                outRect.set(padding, padding, padding, padding);
            }
        });

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_PERMISSION_READ_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_PERMISSION_READ_STORAGE:
                if (isPermissionGranted(grantResults[0])) {
                    initVideo();
                }
                break;
        }
    }

    private void initVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                VideoUtil.getVideos(MainActivity.this, 20, 0, new VideoUtil.VideoLoaderCallback() {
                    @Override
                    public void onVideoLoaded(final List<VideoItem> videoItems, int totalCount) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.update(videoItems);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<VideoItem> data = new ArrayList<>();

        public MyAdapter() {
        }

        public void update(List<VideoItem> list) {
            data = list;
            notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            VideoItemView itemView = new VideoItemView(parent.getContext(), null);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.videoItemView.bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return null != data ? data.size() : 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private VideoItemView videoItemView;

        public MyViewHolder(VideoItemView itemView) {
            super(itemView);
            videoItemView = itemView;
        }
    }
}
