package com.romainpotier.ewokswatch.adapter;

import android.content.Context;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class TimeFormatListAdapter extends BaseListAdapter<Boolean> {

    private static final List<ConfigItem<Boolean>> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(2);
        sConfigItems.add(new ConfigItem<>(true, R.string.time_format_24, R.color.yellow));
        sConfigItems.add(new ConfigItem<>(false, R.string.time_format_12, R.color.yellow));
    }

    public TimeFormatListAdapter(Context context, Boolean defaultValue) {
        super(context, sConfigItems, defaultValue);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item;
    }

    @Override
    public void saveSharedPref(Boolean element) {
        SharedPrefManager.getInstance(mContext).setTimeFormat24(element);
    }

}

