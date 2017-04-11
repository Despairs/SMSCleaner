package despairs.smscleaner.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import despairs.smscleaner.app.model.GroupedSms;
import despairs.smscleaner.R;
import despairs.smscleaner.utils.DateUtils;

/**
 * Created by Home on 10.04.2017.
 */

public class GroupedSmsAdapter extends BaseExpandableListAdapter {

    private List<GroupedSms> data;
    private Context ctx;

    public GroupedSmsAdapter(Context ctx, List<GroupedSms> data) {
        this.data = data;
        this.ctx = ctx;
    }

    public void setData(List<GroupedSms> data) {
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getSmsList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getSmsList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }
        TextView groupTitleView = (TextView) convertView.findViewById(R.id.group_title);
        TextView groupChildCountView = (TextView) convertView.findViewById(R.id.group_child_count);
        groupTitleView.setText(data.get(groupPosition).getGroupId());
        groupChildCountView.setText(String.format("(%d)", data.get(groupPosition).getSmsList().size()));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_child_view, null);
        }
        TextView childTextView = (TextView) convertView.findViewById(R.id.child_text);
        TextView childDateView = (TextView) convertView.findViewById(R.id.child_date);
        childTextView.setText(data.get(groupPosition).getSmsList().get(childPosition).getText());
        childDateView.setText(DateUtils.toString(data.get(groupPosition).getSmsList().get(childPosition).getDate()));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}