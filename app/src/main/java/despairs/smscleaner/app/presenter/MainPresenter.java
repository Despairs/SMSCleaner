package despairs.smscleaner.app.presenter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import despairs.smscleaner.app.task.LoadSmsTask;
import despairs.smscleaner.app.task.RenameSmsAddressTask;
import despairs.smscleaner.utils.DataLayer;
import despairs.smscleaner.app.model.GroupedSms;
import despairs.smscleaner.app.model.Sms;
import despairs.smscleaner.app.ApplicationLoader;
import despairs.smscleaner.app.view.MainView;

/**
 * Created by Despairs on 18.03.16.
 */
public class MainPresenter extends BasePresenter<MainView> {

    private LoadSmsTask loadSmsTask = null;
    private RenameSmsAddressTask renameTask = null;

    public void init(Activity activity) {
        ApplicationLoader.initApplication();
        DataLayer data = new DataLayer(activity);
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

        Collections.sort(groupedSms);
        view.changeGroupedSmsAdapterData(groupedSms);
    }

}
