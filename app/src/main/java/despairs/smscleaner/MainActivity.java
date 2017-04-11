package despairs.smscleaner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private String defaultApp = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ExpandableListView) findViewById(R.id.expanded_list_view);

        DataLayer data = new DataLayer(this);
        List<Sms> smsList = data.getSms();

        List<GroupedSms> groupedSms = new ArrayList<>();
        for (Sms sms : smsList) {
            GroupedSms group = new GroupedSms(sms.getFrom());
            if (!groupedSms.contains(group)) {
                groupedSms.add(group);
            }
            int index = groupedSms.indexOf(group);
            groupedSms.get(index).getSmsList().add(sms);
        }
        for (GroupedSms group : groupedSms) {
            String contactName = data.getContactNameByPhone(group.getGroupId());
            if (contactName != null) {
                group.setGroupId(contactName);
            }
        }
        Collections.sort(groupedSms);
        GroupedSmsAdapter adapter = new GroupedSmsAdapter(getApplicationContext(), groupedSms);
        listView.setAdapter(adapter);
        Log.i("DESPAIRS", defaultApp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        defaultAppResolve();
    }

    private void defaultAppResolve() {

        if (!isDefaultSmsApp(getApplicationContext())) {
            Log.d("", "not default app");
            // App is not default.
            // Show the "not currently set as the default SMS app" interface
            View viewGroup = findViewById(R.id.not_default_app);
            viewGroup.setVisibility(View.VISIBLE);

            // Set up a button that allows the user to change the default SMS app
            setUpDefaultAppResolver();
        } else {
            // App is the default.
            // Hide the "not currently set as the default SMS app" interface
            View viewGroup = findViewById(R.id.not_default_app);
            viewGroup.setVisibility(View.GONE);
        }
    }

    @TargetApi(19)
    private void setUpDefaultAppResolver(){
        Button button = (Button) findViewById(R.id.change_default_app);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
                startActivityForResult(intent, 256);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 256:
                String msgId;
                if (resultCode == Activity.RESULT_OK) {
                    View viewGroup = findViewById(R.id.not_default_app);
                    viewGroup.setVisibility(View.GONE);
                    msgId = "Nice work";
                } else {
                    msgId = "Not nice work";
                }
                Toast.makeText(getBaseContext(), msgId, Toast.LENGTH_SHORT).show();

        }
    }

    public static boolean isDefaultSmsApp(Context context){
        final String myPackageName = context.getPackageName();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if(!Telephony.Sms.getDefaultSmsPackage(context).equals(myPackageName))
                return false;
        }
        return true;
    }
}
