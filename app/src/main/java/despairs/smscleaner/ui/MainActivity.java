package despairs.smscleaner.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import despairs.smscleaner.R;
import despairs.smscleaner.app.model.GroupedSms;
import despairs.smscleaner.app.presenter.MainPresenter;
import despairs.smscleaner.app.view.MainView;
import despairs.smscleaner.ui.adapter.GroupedSmsAdapter;
import despairs.smscleaner.utils.PermissionUtils;

public class MainActivity extends AsyncActivity implements MainView {

    private static final int PERMISSION_REQUEST = 666;

    private ExpandableListView listView;
    private GroupedSmsAdapter adapter;

    private MainPresenter presenter = null;

    private Boolean showMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ExpandableListView) findViewById(R.id.expanded_list_view);

        presenter = new MainPresenter();
        presenter.bindView(this);

        if (PermissionUtils.isPermisssionsGranted(this, Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS)) {
            presenter.loadSmsList();
        } else {
            requestPermissions();
        }

        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if (parent.isGroupExpanded(groupPosition)) {
                parent.collapseGroup(groupPosition);
            } else {
                parent.expandGroup(groupPosition);
                final int groupFlatPos = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                parent.smoothScrollToPosition(groupFlatPos + adapter.getChildrenCount(groupPosition), groupFlatPos);
            }
            return true;

        });
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            long packedPosition = listView.getExpandableListPosition(position);
            if (ExpandableListView.getPackedPositionType(packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                GroupedSms group = (GroupedSms) listView.getItemAtPosition(position);
                group.setSelected(!group.isSelected());
                adapter.notifyDataSetChanged();
                showMenu = group.isSelected();
                invalidateOptionsMenu();
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action_bar, menu);
        if (showMenu) {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_selected) {
            Toast.makeText(getBaseContext(), "Жмакнул!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    @Override
    public void showErrorAlert(String message) {
        showAsyncTaskError(message);
    }

    @Override
    public void showProgress(boolean show) {
        showAsyncTaskProgress(show);
    }

    @Override
    protected String getProgressDialogMessage() {
        return "Пожалуйста, подождите";
    }

    private void requestPermissions() {
        List<String> permissionsToRequest = new ArrayList<>();
        if (PermissionUtils.isPermisssionDenied(this, Manifest.permission.READ_SMS)) {
            permissionsToRequest.add(Manifest.permission.READ_SMS);
        }
        if (PermissionUtils.isPermisssionDenied(this, Manifest.permission.READ_CONTACTS)) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS);
        }
        if (!permissionsToRequest.isEmpty()) {
            PermissionUtils.requestPermissions(this, permissionsToRequest, PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (PermissionUtils.isAllPermissionsAccepted(grantResults)) {
                    presenter.loadSmsList();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
