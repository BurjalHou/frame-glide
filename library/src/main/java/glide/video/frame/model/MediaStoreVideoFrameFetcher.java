package glide.video.frame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.GlideException;

import static android.media.MediaMetadataRetriever.METADATA_KEY_DURATION;

/**
 * Created by burjal on 2018/3/25.
 */

public class MediaStoreVideoFrameFetcher implements DataFetcher<Bitmap> {
    private static final String TAG = MediaStoreVideoFrameFetcher.class.getSimpleName();

    private final Context context;
    private final VideoFrame videoFrame;

    private MediaMetadataRetriever mediaMetadataRetriever;

    public MediaStoreVideoFrameFetcher(Context context,
                                       VideoFrame videoFrame) {
        this.context = context;
        this.videoFrame = videoFrame;
    }

    @Override
    public void loadData(@NonNull Priority priority,
                         @NonNull DataCallback<? super Bitmap> callback) {

        if (!FrameUtil.isReadStorageGranted(context)) {
            callback.onLoadFailed(new GlideException("No permission for [android.permission.READ_EXTERNAL_STORAGE]!"));
            return;
        }

        try {
            initMediaMetadataRetriever();
            videoFrame.setDuration(extractMediaDuration());

            long timeUS = 0L;
            if (videoFrame.frameTime > 0l) {
                timeUS = videoFrame.frameTime;
            } else if (videoFrame.percent > 0 && videoFrame.percent <= 100) {
                timeUS = (long) (videoFrame.getDuration() * (videoFrame.percent / 100f));
            }

            Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(timeUS, videoFrame.option);
            callback.onDataReady(bitmap);
        } catch (Exception e) {
            callback.onLoadFailed(e);
        }
    }

    private long extractMediaDuration() {
        return Long.parseLong(mediaMetadataRetriever.extractMetadata(METADATA_KEY_DURATION));
    }

    private void initMediaMetadataRetriever() {
        mediaMetadataRetriever = new MediaMetadataRetriever();
        if (!TextUtils.isEmpty(videoFrame.path)) {
            mediaMetadataRetriever.setDataSource(videoFrame.path);
        } else if (null != videoFrame.path) {
            mediaMetadataRetriever.setDataSource(context, videoFrame.uri);
        }
    }

    @Override
    public void cleanup() {
        if (null != mediaMetadataRetriever) {
            try {
                mediaMetadataRetriever.release();
                mediaMetadataRetriever = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void cancel() {
        // no op.
    }

    @NonNull
    @Override
    public Class<Bitmap> getDataClass() {
        return Bitmap.class;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return DataSource.LOCAL;
    }
}
