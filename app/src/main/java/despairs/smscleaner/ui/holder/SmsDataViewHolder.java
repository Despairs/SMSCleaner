package despairs.smscleaner.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import despairs.smscleaner.R;

/**
 * Created by Home on 10.04.2017.
 */

public class SmsDataViewHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private TextView textView;

    public SmsDataViewHolder(View view) {
        super(view);
        titleView = (TextView) view.findViewById(R.id.tv_title);
        textView = (TextView) view.findViewById(R.id.tv_count);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
