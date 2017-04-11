package despairs.smscleaner.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.List;

import despairs.smscleaner.app.model.GroupedSms;
import despairs.smscleaner.R;
import despairs.smscleaner.app.presenter.MainPresenter;
import despairs.smscleaner.app.view.MainView;
import despairs.smscleaner.ui.adapter.GroupedSmsAdapter;

public class MainActivity extends AppCompatActivity implements MainView {

    private ExpandableListView listView;
    private GroupedSmsAdapter adapter;

    private MainPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ExpandableListView) findViewById(R.id.expanded_list_view);

        if (presenter == null) {
            presenter = new MainPresenter();
        }
        if (!presenter.isBinded()) {
            presenter.bindView(this);
        }
        presenter.init(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbindView();
    }

//    private void defaultAppResolve() {
//        if (defaultApp != null) {
//            setUpDefaultAppResolver(defaultApp);
//        } else if (!isDefaultSmsApp(getApplicationContext())) {
//            Log.d("", "not default app");
//            View viewGroup = findViewById(R.id.not_default_app);
//            viewGroup.setVisibility(View.VISIBLE);
//            setUpDefaultAppResolver(getPackageName());
//        } else {
//            View viewGroup = findViewById(R.id.not_default_app);
//            viewGroup.setVisibility(View.GONE);
//        }
//    }
//
//    @TargetApi(19)
//    private void setUpDefaultAppResolver(final String pkg) {
//        Button button = (Button) findViewById(R.id.change_default_app);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, pkg);
//                startActivityForResult(intent, 256);
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 256:
//                String msgId;
//                if (resultCode == Activity.RESULT_OK) {
//                    View viewGroup = findViewById(R.id.not_default_app);
//                    viewGroup.setVisibility(View.GONE);
//                    msgId = "Nice work";
//                } else {
//                    msgId = "Not nice work";
//                }
//                Toast.makeText(getBaseContext(), msgId, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultApp);
//                startActivity(intent);
//        }
//    }
//
//    public static boolean isDefaultSmsApp(Context context) {
//        final String myPackageName = context.getPackageName();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (!Telephony.Sms.getDefaultSmsPackage(context).equals(myPackageName))
//                return false;
//        }
//        return true;
//    }

    @Override
    public void changeGroupedSmsAdapterData(List<GroupedSms> smsList) {
        if (adapter == null) {
            adapter = new GroupedSmsAdapter(getApplicationContext(), smsList);
            listView.setAdapter(adapter);
        } else {
            adapter.setData(smsList);
            adapter.notifyDataSetChanged();
        }
    }
}
