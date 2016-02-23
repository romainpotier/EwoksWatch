package com.romainpotier.ewokswatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.activity.BurnModeListActivity;
import com.romainpotier.ewokswatch.activity.DateFormatListActivity;
import com.romainpotier.ewokswatch.activity.EwokFixedListActivity;
import com.romainpotier.ewokswatch.activity.RefreshPicturesListActivity;
import com.romainpotier.ewokswatch.activity.TimeFormatListActivity;

import java.util.ArrayList;
import java.util.List;

public class ConfigListAdapter extends BaseListAdapter<Integer> {

    private static final List<ConfigItem<Integer>> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(3);
        sConfigItems.add(new ConfigItem<>(1, R.string.refresh_pictures, R.color.green, R.drawable.faces));
        sConfigItems.add(new ConfigItem<>(2, R.string.time_format, R.color.yellow, R.drawable.hours));
        sConfigItems.add(new ConfigItem<>(3, R.string.date_format, R.color.red, R.drawable.date));
        sConfigItems.add(new ConfigItem<>(4, R.string.burn_mode, R.color.orange, R.drawable.burn));
        sConfigItems.add(new ConfigItem<>(5, R.string.ewok_fixed, R.color.green, R.drawable.ewok1));
    }

    public ConfigListAdapter(Context context) {
        super(context, sConfigItems, null);
    }

    @Override
    public View.OnClickListener getItemClickListener(final ConfigItem<Integer> configItem, int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (configItem.mPrefValue) {
                    case 1:
                        intent = new Intent(mContext, RefreshPicturesListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(mContext, TimeFormatListActivity.class);
                        break;
                    case 3:
                        intent = new Intent(mContext, DateFormatListActivity.class);
                        break;
                    case 4:
                        intent = new Intent(mContext, BurnModeListActivity.class);
                        break;
                    case 5:
                        intent = new Intent(mContext, EwokFixedListActivity.class);
                        break;
                    default:
                        break;
                }
                mContext.startActivity(intent);
            }
        };
    }

    @Override
    public int getItemLayout() {
        return R.layout.list_item;
    }

    @Override
    public void saveSharedPref(Integer element) {

    }

    @Override
    public int getCircleFullDrawable() {
        return R.drawable.circle_full_picture;
    }
}

