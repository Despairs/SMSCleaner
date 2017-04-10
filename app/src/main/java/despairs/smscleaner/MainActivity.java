package despairs.smscleaner;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<SMSData> smsList = new ArrayList<SMSData>();

        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c= getContentResolver().query(uri, new String[]{"address", "count(1)"}, "0 == 0) GROUP BY (address" ,null, "count(1) DESC");
        startManagingCursor(c);

        // Read the sms data and store it in the list
        if(c.moveToFirst()) {
            for(int i=0; i < c.getCount(); i++) {
                int count = c.getInt(c.getColumnIndexOrThrow("count(1)"));
                String address = c.getString(c.getColumnIndexOrThrow("address"));
                if (count < 10) {
                    continue;
                }
                SMSData sms = new SMSData();
                sms.setCount(count);
                sms.setTitle(address);
                smsList.add(sms);

                c.moveToNext();
            }
        }
        c.close();
        // specify an adapter (see also next example)
        mAdapter = new SmsDataAdapter(smsList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
