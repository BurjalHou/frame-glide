package frameglide.burjal.com.frame_glide.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by burjal on 2018/3/10.
 */

public class ViewUtil {
    private static int SCREEN_WIDTH_PX_CACHE = -1;
    private static int SCREEN_HEIGHT_PX_CACHE = -1;
    private static int SCREEN_WIDTH_DP_CACHE = -1;
    private static int SCREEN_HEIGHT_DP_CACHE = -1;
    private static float SCREEN_ONE_DP_TO_PX = -1.0F;
    private static float SCREEN_ONE_PX_TO_DP = -1.0F;
    private static int STATUS_BAR_HEIGHT = -1;
    private static final Map<Float, Integer> DP_TO_PX_CACHE = new HashMap(50);

    private ViewUtil() {
    }

    public static int getScreenWidthPx() {
        if(SCREEN_WIDTH_PX_CACHE < 0) {
            SCREEN_WIDTH_PX_CACHE = getResource().getDisplayMetrics().widthPixels;
        }

        return SCREEN_WIDTH_PX_CACHE;
    }

    public static int getScreenHeightPx() {
        if(SCREEN_HEIGHT_PX_CACHE < 0) {
            SCREEN_HEIGHT_PX_CACHE = getResource().getDisplayMetrics().heightPixels;
        }

        return SCREEN_HEIGHT_PX_CACHE;
    }

    private static Resources getResource() {
        Resources var0;
//        if(context != null) {
//            var0 = context.getResources();
//        } else {
//            var0 = Resources.getSystem();
//        }
        var0 = Resources.getSystem();

        return var0;
    }

    public static int dp2px(float var0) {
        if(DP_TO_PX_CACHE.containsKey(Float.valueOf(var0)) && DP_TO_PX_CACHE.get(Float.valueOf(var0)) != null) {
            return ((Integer)DP_TO_PX_CACHE.get(Float.valueOf(var0))).intValue();
        } else {
            if(SCREEN_ONE_DP_TO_PX < 0.0F) {
                SCREEN_ONE_DP_TO_PX = TypedValue.applyDimension(1, 1.0F, getResource().getDisplayMetrics());
            }

            int var1 = (int)(SCREEN_ONE_DP_TO_PX * var0);
            DP_TO_PX_CACHE.put(Float.valueOf(var0), Integer.valueOf(var1));
            return var1;
        }
    }

    public static int px2dp(float var0) {
        if(SCREEN_ONE_PX_TO_DP < 0.0F) {
            DisplayMetrics var1 = getResource().getDisplayMetrics();
            SCREEN_ONE_PX_TO_DP = 1.0F / ((float)var1.densityDpi / 160.0F);
        }

        return (int)(SCREEN_ONE_PX_TO_DP * var0);
    }

    public static int px2sp(float var0) {
        float var1 = getResource().getDisplayMetrics().scaledDensity;
        return (int)(var0 / var1 + 0.5F);
    }

    public static int sp2px(float var0) {
        float var1 = getResource().getDisplayMetrics().scaledDensity;
        return (int)(var0 * var1 + 0.5F);
    }
}
