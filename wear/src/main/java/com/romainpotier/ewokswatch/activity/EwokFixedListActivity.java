package com.romainpotier.ewokswatch.activity;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;
import com.romainpotier.ewokswatch.adapter.BurnModeListAdapter;
import com.romainpotier.ewokswatch.adapter.EwokFixedListAdapter;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class EwokFixedListActivity extends BaseListActivity<Integer> {

    @Override
    protected BaseListAdapter<Integer> getBaseListAdapter() {
        return new EwokFixedListAdapter(this, SharedPrefManager.getInstance(this).getEwokFixed());
    }

    @Override
    protected int getHeader() {
        return R.string.ewok_fixed;
    }
}
