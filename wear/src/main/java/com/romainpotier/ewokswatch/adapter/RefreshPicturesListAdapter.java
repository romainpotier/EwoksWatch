package com.romainpotier.ewokswatch.adapter;

import android.content.Context;

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

    public RefreshPicturesListAdapter(Context context, Long defaultParam) {
        super(context, sConfigItems, defaultParam);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item;
    }

    @Override
    public void saveSharedPref(Long element) {
        SharedPrefManager.getInstance(mContext).setRefreshTime(element);
    }

}

