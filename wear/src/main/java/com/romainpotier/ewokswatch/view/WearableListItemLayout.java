/*
 *
 * Copyright (c) 2015, Viadeo Mobile Team
 * All right reserved.
 */

package com.romainpotier.ewokswatch.view;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.romainpotier.ewokswatch.R;


/**
 * Custom layout for the list items.
 * Each item is an ImageView and a TextView
 */
public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private ImageView mCircle;
    private TextView mName;

    private final float mFadedTextAlpha;

    private final static long ANIMATION_DURATION = 150;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);

        mFadedTextAlpha = 50 / 100f;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (ImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.animate().scaleX(1f).scaleY(1f).alpha(1).setDuration(ANIMATION_DURATION);
        mCircle.animate().scaleX(1f).scaleY(1f).alpha(1).setDuration(ANIMATION_DURATION);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.animate().scaleX(0.8f).scaleY(0.8f).alpha(mFadedTextAlpha).setDuration(ANIMATION_DURATION);
        mCircle.animate().scaleX(0.8f).scaleY(0.8f).alpha(mFadedTextAlpha).setDuration(ANIMATION_DURATION);
    }
}
