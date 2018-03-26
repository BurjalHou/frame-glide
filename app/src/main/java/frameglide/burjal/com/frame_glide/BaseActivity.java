package frameglide.burjal.com.frame_glide;

import android.content.pm.PackageManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by burjal on 2018/3/10.
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean isBackHomeEnable;

    protected boolean isPermissionGranted(int grantResult) {
        return grantResult == PackageManager.PERMISSION_GRANTED;
    }

    protected void setBackEnable() {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            this.isBackHomeEnable = true;
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (isBackHomeEnable) {
                    this.finish();
                    return true;
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
