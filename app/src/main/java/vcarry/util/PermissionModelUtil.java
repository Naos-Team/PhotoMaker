package vcarry.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;


public class PermissionModelUtil {
    public static final String[] NECESSARY_PERMISSIONS = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final String PERMISSION_CHECK_PREF = "marshmallow_permission_check";
    private Context context;
    private SharedPreferences sharedPrefs;

    private PermissionModelUtil() {
    }

    public PermissionModelUtil(Context context) {
        this.context = context;
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean needPermissionCheck() {
        boolean z = false;
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        if (this.context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            z = true;
        }
        return z;
    }

    public void showPermissionExplanationThenAuthorization() {
//        new Builder(this.context, 0)
//                .setTitle((int) R.string.permission_check_title)
//                .setMessage((int) R.string.permission_check_message)
//                .setPositiveButton((int) R.string.ok, new BLSUBLSL_PermissionModelUtil1(this)).setCancelable(false).create().show();
    }

    private void requestPermissions() {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((Activity) this.context).requestPermissions(NECESSARY_PERMISSIONS, 1);
        }
    }
}
