package despairs.smscleaner.app.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

import despairs.smscleaner.app.model.Sms;
import despairs.smscleaner.utils.DataLayer;

/**
 * Created by Despairs on 12.01.16.
 */
public class LoadSmsTask extends AsyncTask<Activity, Void, List<Sms>> {

    public interface ILoadSmsTaskCallback {
        void receiveLoadSmsTaskCallback(List<Sms> result);
    }

    private final ILoadSmsTaskCallback caller;

    public LoadSmsTask(ILoadSmsTaskCallback caller) {
        this.caller = caller;
    }

    @Override
    protected List<Sms> doInBackground(Activity... params) {
        return new DataLayer().getSms();
    }

    @Override
    protected void onPostExecute(List<Sms> result) {
        caller.receiveLoadSmsTaskCallback(result);
    }


}
