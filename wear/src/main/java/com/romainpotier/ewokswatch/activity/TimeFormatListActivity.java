package com.romainpotier.ewokswatch.activity;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;
import com.romainpotier.ewokswatch.adapter.TimeFormatListAdapter;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class TimeFormatListActivity extends BaseListActivity<Boolean> {

    @Override
    protected BaseListAdapter<Boolean> getBaseListAdapter() {
        return new TimeFormatListAdapter(this, SharedPrefManager.getInstance(this).getTimeFormat24());
    }

    @Override
    protected int getHeader() {
        return R.string.time_format;
    }
}
