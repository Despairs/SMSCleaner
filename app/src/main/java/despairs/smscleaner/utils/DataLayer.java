package despairs.smscleaner.utils;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import despairs.smscleaner.app.ApplicationLoader;
import despairs.smscleaner.app.model.Sms;

/**
 * Created by EKovtunenko on 11.04.2017.
 */

public class DataLayer {

    public List<Sms> getSms() {
        List<Sms> ret = new ArrayList<>();
        Uri uri = Uri.parse("content://sms/inbox");
        try (Cursor c = ApplicationLoader.applicationContext.getContentResolver().query(uri, new String[]{"_id", "address", "body", "date"}, null, null, null)) {
            if (c.moveToFirst()) {
                for (int i = 0; i < c.getCount(); i++) {
                    Long id = c.getLong(c.getColumnIndexOrThrow("_id"));
                    String address = c.getString(c.getColumnIndexOrThrow("address"));
                    String text = c.getString(c.getColumnIndexOrThrow("body"));
                    Long dateInMillis = c.getLong(c.getColumnIndexOrThrow("date"));
                    Date date = new Date();
                    date.setTime(dateInMillis);
                    Sms sms = new Sms(id, address, text, date);
                    ret.add(sms);
                    c.moveToNext();
                }
            }
        }
        return ret;
    }

    public String getContactNameByPhone(String phone) {
        Log.i("DESPAIRS", "getContactNameByPhone " + phone);
        String ret = null;
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
        try (Cursor c = ApplicationLoader.applicationContext.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, phone, null, null)) {
            if (c.getCount() <= 1) {
                if (c.moveToFirst()) {
                    ret = c.getString(c.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                }
            } else {
                Log.i("DESPAIRS", "Все плохо");
            }
        }
        return ret;
    }
}
