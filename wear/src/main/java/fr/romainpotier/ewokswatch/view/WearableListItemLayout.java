/*
 *
 * Copyright (c) 2015, Viadeo Mobile Team
 * All right reserved.
 */

package fr.romainpotier.ewokswatch.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.romainpotier.ewokswatch.R;


/**
 * Custom layout for the list items.
 * Each item is an ImageView and a TextView
 */
public class WearableListItemLayout extends LinearLayout
        implements WearableListView.OnCenterProximityListener {

    private ImageView mCircle;
    private TextView mName;

    // Only used if we don't use a custom src image in mCircle
    private final float mFadedTextAlpha;
    private final int mFadedCircleColor;
    private final int mChosenCircleColor;

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
        mFadedCircleColor = ContextCompat.getColor(context, R.color.beige);
        mChosenCircleColor = ContextCompat.getColor(context, R.color.green);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (ImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mName.setAlpha(1f);
        mCircle.animate().scaleX(1.4f).scaleY(1.4f).setDuration(150);
        if (mCircle.getDrawable() instanceof LayerDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) ((LayerDrawable) mCircle.getDrawable()).findDrawableByLayerId(R.id.shape_color);
            gradientDrawable.setColor(mChosenCircleColor);
        }
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mName.setAlpha(mFadedTextAlpha);
        mCircle.animate().scaleX(1f).scaleY(1f).setDuration(150);
        if (mCircle.getDrawable() instanceof LayerDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) ((LayerDrawable) mCircle.getDrawable()).findDrawableByLayerId(R.id.shape_color);
            gradientDrawable.setColor(mFadedCircleColor);
        }
    }
}
