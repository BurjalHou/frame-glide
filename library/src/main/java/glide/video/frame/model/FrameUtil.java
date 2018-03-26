package glide.video.frame.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by burjal on 2018/3/25.
 */

public class FrameUtil {
    private static final String READ_STORAGE_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";

    private FrameUtil() {
    }

    public static boolean isReadStorageGranted(Context context) {
        return ContextCompat.checkSelfPermission(context.getApplicationContext(), READ_STORAGE_PERMISSION)
                == PackageManager.PERMISSION_GRANTED;
    }

}
