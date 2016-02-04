package com.romainpotier.ewokswatch.activity;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;
import com.romainpotier.ewokswatch.adapter.BurnModeListAdapter;
import com.romainpotier.ewokswatch.adapter.TimeFormatListAdapter;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class BurnModeListActivity extends BaseListActivity<Boolean> {

    @Override
    protected BaseListAdapter<Boolean> getBaseListAdapter() {
        return new BurnModeListAdapter(this, SharedPrefManager.getInstance(this).getBurnMode());
    }

    @Override
    protected int getHeader() {
        return R.string.burn_mode;
    }
}
