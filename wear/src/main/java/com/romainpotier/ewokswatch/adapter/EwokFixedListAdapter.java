package com.romainpotier.ewokswatch.adapter;

import android.content.Context;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class EwokFixedListAdapter extends BaseListAdapter<Integer> {

    private static final List<ConfigItem<Integer>> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(7);
        sConfigItems.add(new ConfigItem<>(-1, R.string.ewok_choose_0, R.color.green, android.R.color.transparent));
        sConfigItems.add(new ConfigItem<>(0, R.string.ewok_choose_1, R.color.middlegrey, R.drawable.ewok1));
        sConfigItems.add(new ConfigItem<>(1, R.string.ewok_choose_2, R.color.green, R.drawable.ewok2));
        sConfigItems.add(new ConfigItem<>(2, R.string.ewok_choose_3, R.color.yellow, R.drawable.ewok3));
        sConfigItems.add(new ConfigItem<>(3, R.string.ewok_choose_4, R.color.orange, R.drawable.ewok4));
        sConfigItems.add(new ConfigItem<>(4, R.string.ewok_choose_5, R.color.red, R.drawable.ewok5));
        sConfigItems.add(new ConfigItem<>(5, R.string.ewok_choose_6, R.color.green, R.drawable.ewok6));
    }

    public EwokFixedListAdapter(Context context, Integer defaultValue) {
        super(context, sConfigItems, defaultValue);
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item;
    }

    @Override
    public void saveSharedPref(Integer element) {
        SharedPrefManager.getInstance(mContext).setEwokFixed(element);
    }

    @Override
    public int getCircleFullDrawable() {
        return R.drawable.circle_full_picture;
    }

}

