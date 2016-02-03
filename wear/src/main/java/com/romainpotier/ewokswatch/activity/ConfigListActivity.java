package com.romainpotier.ewokswatch.activity;

import android.os.Bundle;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;
import com.romainpotier.ewokswatch.adapter.ConfigListAdapter;

public class ConfigListActivity extends BaseListActivity<Integer> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseListAdapter<Integer> getBaseListAdapter() {
        return new ConfigListAdapter(this);
    }

    @Override
    protected int getHeader() {
        return R.string.configuration;
    }
}
