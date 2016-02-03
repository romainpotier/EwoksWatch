package com.romainpotier.ewokswatch.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.activity.RefreshPicturesListActivity;

import java.util.ArrayList;
import java.util.List;

public class ConfigListAdapter extends BaseListAdapter<Integer> {

    private static final List<ConfigItem<Integer>> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(3);
        sConfigItems.add(new ConfigItem<>(1, R.string.refresh_pictures, R.color.green));
        sConfigItems.add(new ConfigItem<>(2, R.string.time_format, R.color.yellow));
        sConfigItems.add(new ConfigItem<>(3, R.string.date_format, R.color.red));
    }

    public ConfigListAdapter(Context context) {
        super(context, sConfigItems);
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
                        intent = new Intent(mContext, RefreshPicturesListActivity.class);
                        break;
                    case 3:
                        intent = new Intent(mContext, RefreshPicturesListActivity.class);
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
    public int getSelectedParam() {
        return -1;
    }

}

