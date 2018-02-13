package despairs.smscleaner.app.task;

import android.os.AsyncTask;

import java.util.List;

import despairs.smscleaner.app.model.GroupedSms;
import despairs.smscleaner.app.model.Sms;
import despairs.smscleaner.utils.DataLayer;

/**
 * Created by Despairs on 12.01.16.
 */
public class RenameGroupIdTask extends AsyncTask<Object, Void, List<GroupedSms>> {

    public interface IRenameSmsAddressTaskCallback {
        void onRenameSmsAddressCallback(List<GroupedSms> result);
    }

    private final IRenameSmsAddressTaskCallback caller;

    public RenameGroupIdTask(IRenameSmsAddressTaskCallback caller) {
        this.caller = caller;
    }

    @Override
    protected List<GroupedSms> doInBackground(Object... params) {
        DataLayer data = new DataLayer();
        for (GroupedSms group : (List<GroupedSms>) params[0]) {
            if (group.getGroupId().startsWith("+7")) {
                String contactName = data.getContactNameByPhone(group.getGroupId());
                if (contactName != null) {
                    group.setGroupId(contactName);
                    for (Sms sms : group.getSmsList()) {
                        sms.setMappedAddress(contactName);
                    }
                }
            }
        }
        return (List<GroupedSms>) params[0];
    }

    @Override
    protected void onPostExecute(List<GroupedSms> result) {
        caller.onRenameSmsAddressCallback(result);
    }


}
