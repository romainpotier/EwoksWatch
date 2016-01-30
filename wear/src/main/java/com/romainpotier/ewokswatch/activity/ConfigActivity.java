package com.romainpotier.ewokswatch.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.adapter.ListAdapter;
import com.romainpotier.ewokswatch.preferences.SharedPrefManager;

public class ConfigActivity extends Activity implements WearableListView.OnScrollListener{

    private TextView mHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        mHeader = (TextView) findViewById(R.id.header);

        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
        ListAdapter listAdapter = new ListAdapter(this);
        listView.setAdapter(listAdapter);

        final long refreshTime = SharedPrefManager.getInstance(this).getRefreshTime();
        listView.scrollToPosition(listAdapter.getIndexByDuration(refreshTime));

        listView.setHasFixedSize(true);
        listView.addOnScrollListener(this);

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
}
