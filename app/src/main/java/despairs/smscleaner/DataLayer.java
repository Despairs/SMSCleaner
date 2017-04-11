package despairs.smscleaner;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EKovtunenko on 11.04.2017.
 */

public class DataLayer implements IDataLayer {

    private final Activity a;

    public DataLayer(Activity a) {
        this.a = a;
    }

    @Override
    public List<SMSData> getSms() {
        List<SMSData> ret = new ArrayList<>();

        Uri uri = Uri.parse("content://sms/inbox");
        try (Cursor c = a.getContentResolver().query(uri, new String[]{"address", "count(1)"}, "0 == 0) GROUP BY (address", null, "count(1) DESC")) {
            a.startManagingCursor(c);
            // Read the sms data and store it in the list
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    int count = c.getInt(c.getColumnIndexOrThrow("count(1)"));
                    String address = c.getString(c.getColumnIndexOrThrow("address"));
                    if (count < 10) {
                        continue;
                    }
                    SMSData sms = new SMSData();
                    sms.setCount(count);
                    sms.setTitle(address);
                    ret.add(sms);
                    c.moveToNext();
                }
            }
            a.stopManagingCursor(c);
        }
        return ret;
    }
}
