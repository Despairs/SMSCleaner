package despairs.smscleaner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * Created by EKovtunenko on 13.02.2018.
 */

public class PermissionUtils {

    public static boolean isPermisssionsGranted(Context context, String... permissions) {
        for (String permisssion : permissions) {
            if (isPermisssionDenied(context, permisssion)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public static boolean isPermisssionGranted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isPermisssionDenied(Context context, String permission) {
        return !isPermisssionGranted(context, permission);
    }

    public static void requestPermissions(Activity activity, List<String> permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestCode);
    }

    public static boolean isAllPermissionsAccepted(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
