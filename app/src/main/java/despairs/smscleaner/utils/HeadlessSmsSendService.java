package despairs.smscleaner.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by EKovtunenko on 11.04.2017.
 */

public class HeadlessSmsSendService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
