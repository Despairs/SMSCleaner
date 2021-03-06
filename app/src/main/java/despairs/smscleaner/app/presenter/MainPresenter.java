package despairs.smscleaner.app.presenter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import despairs.smscleaner.app.model.GroupedSms;
import despairs.smscleaner.app.model.Sms;
import despairs.smscleaner.app.task.LoadSmsTask;
import despairs.smscleaner.app.task.RenameGroupIdTask;
import despairs.smscleaner.app.view.MainView;

/**
 * Created by Despairs on 18.03.16.
 */
public class MainPresenter extends BasePresenter<MainView> implements LoadSmsTask.ILoadSmsTaskCallback, RenameGroupIdTask.IRenameSmsAddressTaskCallback {

    private LoadSmsTask loadSmsTask = null;
    private RenameGroupIdTask renameTask = null;

    private List<GroupedSms> groupedSms;

    public void loadSmsList() {
        view.showProgress(true);
        loadSmsTask = new LoadSmsTask(this);
        loadSmsTask.execute();
    }

    @Override
    public void receiveLoadSmsTaskCallback(List<Sms> result) {
        loadSmsTask = null;
        if (groupedSms == null) {
            groupedSms = new ArrayList<>();
        }
        for (Sms sms : result) {
            GroupedSms group = new GroupedSms(sms.getAddress());
            if (!groupedSms.contains(group)) {
                groupedSms.add(group);
            }
            int index = groupedSms.indexOf(group);
            groupedSms.get(index).getSmsList().add(sms);
        }
        Collections.sort(groupedSms);
        view.changeGroupedSmsAdapterData(groupedSms);
        view.showProgress(false);
        renameTask = new RenameGroupIdTask(this);
        renameTask.execute(groupedSms);
    }

    @Override
    public void onRenameSmsAddressCallback(List<GroupedSms> result) {
        this.groupedSms = result;
        renameTask = null;
        view.changeGroupedSmsAdapterData(groupedSms);
    }
}
