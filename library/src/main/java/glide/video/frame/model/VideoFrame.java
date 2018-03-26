package glide.video.frame.model;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by burjal on 2018/3/25.
 */

public class VideoFrame implements Serializable {

    /**
     * 视频路径
     * <p>
     * 若同时设置 {@link #uri} 和 {@link #path},优先取 {@link #path}.
     * </p>
     */
    public Uri uri;

    /**
     * 视频路径
     * <p>
     * 若同时设置 {@link #uri} 和 {@link #path},优先取 {@link #path}.
     * </p>
     */
    public String path;
    /**
     * 获取指定帧的画面(单位：us)
     * <p>
     * 若同时设置 {@link #frameTime} 和 {@link #percent},优先取 {@link #frameTime}.
     * </p>
     */
    public long frameTime;
    /**
     * 获取指定帧的画面
     * <p>
     * 若同时设置 {@link #frameTime} 和 {@link #percent},优先取 {@link #frameTime}.
     * </p>
     */
    @IntRange(from = 0, to = 100)
    public int percent;
    /**
     * Option
     * See {@link android.media.MediaMetadataRetriever#OPTION_PREVIOUS_SYNC}.
     * See {@link android.media.MediaMetadataRetriever#OPTION_NEXT_SYNC}.
     * See {@link android.media.MediaMetadataRetriever#OPTION_CLOSEST_SYNC}.
     * See {@link android.media.MediaMetadataRetriever#OPTION_CLOSEST}.
     */
    public int option = MediaMetadataRetriever.OPTION_CLOSEST_SYNC;
    /**
     * 视频时长(单位：ms)
     */
    private long duration;

    public VideoFrame(Builder builder) {
        this.uri = builder.uri;
        this.path = builder.path;
        this.frameTime = builder.frameTime;
        this.percent = builder.percent;
        this.option = builder.option;
        this.duration = builder.duration;
    }

    public long getDuration() {
        return duration;
    }

    void setDuration(long duration) {
        this.duration = duration;
    }

    public static class Builder {
        Uri uri;
        String path;
        long frameTime;
        int percent;
        int option;
        long duration;

        public Builder() {
            this.option = MediaMetadataRetriever.OPTION_CLOSEST_SYNC;
            this.duration = 0;
        }

        /**
         * 若同时设置 {@link #uri} 和 {@link #path},优先取 {@link #path}.
         *
         * @param uri 视频路径
         * @return
         */
        public Builder uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        /**
         * 若同时设置 {@link #uri} 和 {@link #path},优先取 {@link #path}.
         *
         * @param path 视频路径
         * @return
         */
        public Builder path(String path) {
            this.path = path;
            return this;
        }

        /**
         * 若同时设置 {@link #frameTime} 和 {@link #percent},优先取 {@link #frameTime}.
         *
         * @param timeUS 获取指定帧的画面(单位：us)
         * @return
         */
        public Builder frameTime(long timeUS) {
            this.frameTime = timeUS;
            return this;
        }

        /**
         * 若同时设置 {@link #frameTime} 和 {@link #percent},优先取 {@link #frameTime}.
         *
         * @param percent 获取指定帧的画面
         * @return
         */
        public Builder percent(@IntRange(from = 0, to = 100) int percent) {
            this.percent = percent;
            return this;
        }

        /**
         * @param option :See {@link android.media.MediaMetadataRetriever#OPTION_PREVIOUS_SYNC}.
         *               See {@link android.media.MediaMetadataRetriever#OPTION_NEXT_SYNC}.
         *               See {@link android.media.MediaMetadataRetriever#OPTION_CLOSEST_SYNC}.
         *               See {@link android.media.MediaMetadataRetriever#OPTION_CLOSEST}.
         * @return
         */
        public Builder option(int option) {
            this.option = option;
            return this;
        }

        public VideoFrame build() {
            if (uri == null && TextUtils.isEmpty(path)) {
                throw new IllegalArgumentException("Null path!");
            }
            if (option < android.media.MediaMetadataRetriever.OPTION_PREVIOUS_SYNC ||
                    option > android.media.MediaMetadataRetriever.OPTION_CLOSEST) {
                throw new IllegalArgumentException("Unsupported option: " + option);
            }
            return new VideoFrame(this);
        }

    }
}
