package despairs.smscleaner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;

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
    }
}
