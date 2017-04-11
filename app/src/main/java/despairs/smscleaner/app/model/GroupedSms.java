package despairs.smscleaner.app.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EKovtunenko on 11.04.2017.
 */

public class GroupedSms implements Comparable<GroupedSms> {
    private String groupId;
    private List<Sms> smsList;

    public GroupedSms(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<Sms> getSmsList() {
        if (smsList == null) {
            smsList = new ArrayList<>();
        }
        return smsList;
    }

    public void setSmsList(List<Sms> smsList) {
        this.smsList = smsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupedSms that = (GroupedSms) o;

        return groupId.equals(that.groupId);

    }

    @Override
    public int hashCode() {
        return groupId.hashCode();
    }

    @Override
    public int compareTo(@NonNull GroupedSms o) {
        return o.getSmsList().size() > this.getSmsList().size() ? 1 : -1;
    }
}
