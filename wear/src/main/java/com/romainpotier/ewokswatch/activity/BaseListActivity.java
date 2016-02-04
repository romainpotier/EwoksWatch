package com.romainpotier.ewokswatch.activity;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.BaseListAdapter;

public abstract class BaseListActivity<T> extends WearableActivity implements WearableListView.OnScrollListener {

    protected TextView mHeader;
    protected WearableListView mWearableListView;
    protected BaseListAdapter<T> mBaseListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        mHeader = (TextView) findViewById(R.id.header);
        mHeader.setText(getHeader());

        mWearableListView = (WearableListView) findViewById(R.id.wearable_list);
        mBaseListAdapter = getBaseListAdapter();
        mWearableListView.setAdapter(mBaseListAdapter);

        final int selectedParam = mBaseListAdapter.getSelectedParam();
        if (selectedParam != -1) {
            mWearableListView.scrollToPosition(selectedParam);
        }

        mWearableListView.setHasFixedSize(true);
        mWearableListView.addOnScrollListener(this);

    }

    @Override
    public void onScroll(int i) {

    }

    @Override
    public void onAbsoluteScrollChange(int scroll) {
        float newTranslation = Math.min(-scroll, 0);
        mHeader.setTranslationY(newTranslation);
    }

    @Override
    public void onScrollStateChanged(int i) {

    }

    @Override
    public void onCentralPositionChanged(int i) {

    }

    protected abstract BaseListAdapter<T> getBaseListAdapter();

    protected abstract int getHeader();

}
