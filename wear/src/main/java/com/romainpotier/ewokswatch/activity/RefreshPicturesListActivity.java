package com.romainpotier.ewokswatch.activity;

import android.os.Bundle;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;
import com.romainpotier.ewokswatch.adapter.RefreshPicturesListAdapter;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class RefreshPicturesListActivity extends BaseListActivity<Long> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final long refreshTime = SharedPrefManager.getInstance(this).getRefreshTime();
        mWearableListView.scrollToPosition(((RefreshPicturesListAdapter)mBaseListAdapter).getIndexByDuration(refreshTime));

    }

    @Override
    protected BaseListAdapter<Long> getBaseListAdapter() {
        return new RefreshPicturesListAdapter(this);
    }

    @Override
    protected int getHeader() {
        return R.string.refresh_pictures;
    }
}
