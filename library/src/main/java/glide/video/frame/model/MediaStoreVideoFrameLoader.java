package glide.video.frame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.signature.ObjectKey;

/**
 * Created by burjal on 2018/3/25.
 */

public class MediaStoreVideoFrameLoader implements ModelLoader<VideoFrame, Bitmap> {
    private final Context context;

    public MediaStoreVideoFrameLoader(Context context) {
        this.context = context.getApplicationContext();
    }

    @Nullable
    @Override
    public LoadData<Bitmap> buildLoadData(@NonNull VideoFrame videoFrame, int width, int height,
                                          @NonNull Options options) {
        return new LoadData<>(new ObjectKey(videoFrame),
                new MediaStoreVideoFrameFetcher(context, videoFrame));
    }

    @Override
    public boolean handles(@NonNull VideoFrame videoEntity) {
        // TODO: 2018/3/25 Check path or uri for video.
        return true;
    }

    public static class Factory implements ModelLoaderFactory<VideoFrame, Bitmap> {

        private final Context context;

        public Factory(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ModelLoader<VideoFrame, Bitmap> build(
                @NonNull MultiModelLoaderFactory multiFactory) {
            return new MediaStoreVideoFrameLoader(context);
        }

        @Override
        public void teardown() {
            // No op.
        }
    }
}
