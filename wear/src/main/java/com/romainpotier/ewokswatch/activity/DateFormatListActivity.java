package com.romainpotier.ewokswatch.activity;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;
import com.romainpotier.ewokswatch.adapter.DateFormatListAdapter;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class DateFormatListActivity extends BaseListActivity<String> {

    @Override
    protected BaseListAdapter<String> getBaseListAdapter() {
        return new DateFormatListAdapter(this, SharedPrefManager.getInstance(this).getDateFormat());
    }

    @Override
    protected int getHeader() {
        return R.string.date_format;
    }
}
