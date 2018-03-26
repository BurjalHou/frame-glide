package frameglide.burjal.com.frame_glide.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import frameglide.burjal.com.frame_glide.model.VideoItem;

/**
 * Created by burjal on 2018/3/10.
 */

public class VideoUtil {
    private static final String TAG = "VideoUtil";

    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            "duration"};
    private static final String SELECTION_ALL =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND "
                    + MediaStore.MediaColumns.SIZE + ">0";
    private static final String[] SELECTION_ALL_VIDEO_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };
    private static final String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";

    private VideoUtil() {
    }

    @WorkerThread
    public static void getVideos(Context context, int limit, int offset, final VideoLoaderCallback callback) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Query On Wrong Thread!");
        }

        int totalCount = -1;
        List<VideoItem> items = new ArrayList<>();
        Cursor cursor = null;

        try {
            ContentResolver cr = context.getContentResolver();

            cursor = cr.query(QUERY_URI.buildUpon().encodedQuery("limit=" + offset + "," + limit).build(),
                    PROJECTION,
                    SELECTION_ALL,
                    SELECTION_ALL_VIDEO_ARGS,
                    ORDER_BY);

            if (null != cursor) {
                totalCount = cursor.getCount();

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
                    String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
                    String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                    String mineType = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                    long size = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
                    long duration = cursor.getLong(cursor.getColumnIndex("duration"));
                    int width = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.WIDTH));
                    int height = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT));
                    Uri fileUri = Uri.parse("file://" + filePath);

                    Log.i(TAG, String.format("id = %s, displayName = %s, filePath = %s, fileUri = %s, mineType = %s, size = %s, duration = %s, width = %s, height = %s, cursorCount = %s",
                            id, displayName, filePath, fileUri, mineType, size, duration, width, height, cursor.getCount()));

                    if (duration > 1000) {
                        VideoItem videoItem = new VideoItem();
                        videoItem.id = id;
                        videoItem.name = displayName;
                        videoItem.videoPath = filePath;
                        videoItem.videoUrl = fileUri;
                        videoItem.mineType = mineType;
                        videoItem.size = size;
                        videoItem.duration = duration;
                        videoItem.height = height;
                        videoItem.width = width;

                        items.add(videoItem);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getGalleryVideos", e);
        } finally {
            CloseUtil.close(cursor);
        }

        if (null != callback) {
            callback.onVideoLoaded(items, totalCount);
        }
    }

    public interface VideoLoaderCallback {

        void onVideoLoaded(List<VideoItem> videoItems, int totalCount);

    }
}
