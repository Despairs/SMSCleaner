package despairs.smscleaner.app.view;

import java.util.List;

import despairs.smscleaner.app.model.GroupedSms;

/**
 * Created by Despairs on 18.03.16.
 */
public interface MainView extends AsyncView{
    void changeGroupedSmsAdapterData(List<GroupedSms> smsList);
}
