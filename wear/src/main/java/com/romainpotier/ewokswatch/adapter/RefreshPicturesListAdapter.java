package com.romainpotier.ewokswatch.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class RefreshPicturesListAdapter extends BaseListAdapter<Long> {

    private static final List<ConfigItem<Long>> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(6);
        sConfigItems.add(new ConfigItem<>(1000 * 60 * 60 * 3L, R.string.three_hours_reload, R.color.green));
        sConfigItems.add(new ConfigItem<>(1000 * 60 * 60L, R.string.one_hour_reload, R.color.green));
        sConfigItems.add(new ConfigItem<>(1000 * 60 * 30L, R.string.thirty_minutes_reload, R.color.green));
        sConfigItems.add(new ConfigItem<>(1000 * 60 * 10L, R.string.ten_minutes_reload, R.color.green));
        sConfigItems.add(new ConfigItem<>(1000 * 60 * 5L, R.string.five_minutes_reload, R.color.green));
        sConfigItems.add(new ConfigItem<>(1000 * 60L, R.string.one_minute_reload, R.color.green));
    }

    public RefreshPicturesListAdapter(Context context) {
        super(context, sConfigItems);
    }

    public int getIndexByDuration(long duration) {
        for (int i = 0; i < sConfigItems.size(); i++) {
            if (sConfigItems.get(i).mPrefValue == duration) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public View.OnClickListener getItemClickListener(final ConfigItem<Long> configItem, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(mContext).setRefreshTime(configItem.mPrefValue);
                notifyDataSetChanged();
                mSelectedParam = position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) mContext).finish();
                    }
                }, 300);

            }
        };
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item;
    }

    @Override
    public int getSelectedParam() {
        return getIndexByDuration(SharedPrefManager.getInstance(mContext).getRefreshTime());
    }
}

