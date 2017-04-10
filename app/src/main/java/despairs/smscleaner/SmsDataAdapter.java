package despairs.smscleaner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Home on 10.04.2017.
 */

public class SmsDataAdapter extends RecyclerView.Adapter<SmsDataViewHolder> {
    private List<SMSData> data;

    public static class ViewHolder {

    }

    public SmsDataAdapter(List<SMSData> data) {
        this.data = data;
    }

    @Override
    public SmsDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new SmsDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SmsDataViewHolder holder, int position) {
        holder.getTitleView().setText(data.get(position).getTitle());
        holder.getTextView().setText(String.valueOf(data.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}