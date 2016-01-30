package com.romainpotier.ewokswatch.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class ListAdapter extends WearableListView.Adapter {

    private final LayoutInflater mInflater;

    protected Context mContext;

    private static final List<ConfigItem> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(6);
        sConfigItems.add(new ConfigItem(1000 * 60 * 60 * 3, R.string.three_hours_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 60, R.string.one_hour_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 30, R.string.thirty_minutes_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 10, R.string.ten_minutes_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 5, R.string.five_minutes_reload));
        sConfigItems.add(new ConfigItem(1000 * 60, R.string.one_minute_reload));
    }

    public ListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public int getIndexByDuration(long duration) {
        for (int i = 0; i < sConfigItems.size(); i++) {
            if (sConfigItems.get(i).mDurationPicture == duration) {
                return i;
            }
        }
        return 0;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(final WearableListView.ViewHolder holder,
                                 int position) {

        final ConfigItem configItem = sConfigItems.get(position);

        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.mTextView;

        view.setText(mContext.getString(configItem.mDurationNameResource));

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(mContext).setRefreshTime(configItem.mDurationPicture);
                ((Activity) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sConfigItems.size();
    }

    private static class ConfigItem {
        long mDurationPicture;
        int mDurationNameResource;

        public ConfigItem(long durationPicture, int durationNameResource) {
            mDurationPicture = durationPicture;
            mDurationNameResource = durationNameResource;
        }
    }

}

