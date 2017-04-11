package despairs.smscleaner.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import despairs.smscleaner.utils.UserConfig;

/**
 * Created by Home on 11.04.2017.
 */

public class ApplicationLoader extends Application {

    public static volatile Context applicationContext;
    public static volatile Handler applicationHandler;
    private static volatile boolean applicationInited = false;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
    }

    public static void initApplication() {
        if (applicationInited) {
            return;
        }
        applicationInited = true;
        UserConfig.init();
    }

}
