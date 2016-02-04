package com.romainpotier.ewokswatch.adapter;

import android.content.Context;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class DateFormatListAdapter extends BaseListAdapter<String> {

    private static final List<ConfigItem<String>> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(2);
        sConfigItems.add(new ConfigItem<>("", R.string.date_format_no_date, R.color.red));
        sConfigItems.add(new ConfigItem<>("EEE dd MMM yyyy", R.string.date_format_text, R.color.red));
        sConfigItems.add(new ConfigItem<>("dd / MM / yyyy", R.string.date_format_jj_mm_yyyy, R.color.red));
        sConfigItems.add(new ConfigItem<>("yyyy / MM / dd", R.string.date_format_yyyy_mm_jj, R.color.red));
        sConfigItems.add(new ConfigItem<>("MM / dd / yyyy", R.string.date_format_mm_jj_aaaa, R.color.red));
    }

    public DateFormatListAdapter(Context context, String defaultValue) {
        super(context, sConfigItems, defaultValue);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item;
    }

    @Override
    public void saveSharedPref(String element) {
        SharedPrefManager.getInstance(mContext).setDateFormat(element);
    }

}

