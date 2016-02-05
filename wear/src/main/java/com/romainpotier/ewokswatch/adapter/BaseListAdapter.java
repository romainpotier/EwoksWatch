package com.romainpotier.ewokswatch.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.romainpotier.ewokswatch.R;
import com.romainpotier.ewokswatch.util.BusProvider;
import com.romainpotier.ewokswatch.util.events.CloseActivityEvent;

import java.util.List;

public abstract class BaseListAdapter<T> extends WearableListView.Adapter {

    private final LayoutInflater mInflater;

    protected Context mContext;

    private List<ConfigItem<T>> mConfigItems;

    protected int mSelectedParam;

    public BaseListAdapter(Context context, List<ConfigItem<T>> configItems, T defaultParam) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mConfigItems = configItems;
        mSelectedParam = getIndexByParam(defaultParam);
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView mTextView;
        private ImageView mCircle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.name);
            mCircle = (ImageView) itemView.findViewById(R.id.circle);
        }
    }

    public int getSelectedParam() {
        return mSelectedParam;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(getItemLayout(), null));
    }

    @Override
    public void onBindViewHolder(final WearableListView.ViewHolder holder,
                                 final int position) {

        final ConfigItem<T> configItem = mConfigItems.get(position);

        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.mTextView;

        view.setText(mContext.getString(configItem.mNameResource));

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(getItemClickListener(configItem, position));

        Drawable drawable;
        GradientDrawable gradientDrawable;
        if ((mSelectedParam != -1 && mSelectedParam == position) || mSelectedParam == -1) {
            drawable = ContextCompat.getDrawable(mContext, getCircleFullDrawable());
            gradientDrawable = (GradientDrawable) ((LayerDrawable) drawable).findDrawableByLayerId(R.id.shape_full_color);
            gradientDrawable.setColor(ContextCompat.getColor(mContext, configItem.mCircleColor));
        } else {
            drawable = ContextCompat.getDrawable(mContext, R.drawable.circle);
            gradientDrawable = (GradientDrawable) ((LayerDrawable) drawable).findDrawableByLayerId(R.id.shape_color);
            gradientDrawable.setStroke(3, ContextCompat.getColor(mContext, configItem.mCircleColor));
        }

        if (configItem.mPicture != 0) {
//            gradientDrawable = (GradientDrawable) ((LayerDrawable) drawable).findDrawableByLayerId(R.id.picture_circle);
//            gradientDrawable.set

            Drawable replace = ContextCompat.getDrawable(mContext, configItem.mPicture);
            ((LayerDrawable)drawable).setDrawableByLayerId(R.id.picture_circle, replace);

        }

        itemHolder.mCircle.setImageDrawable(drawable);

    }

    public int getCircleFullDrawable() {
        return R.drawable.circle_full;
    }

    @Override
    public int getItemCount() {
        return mConfigItems.size();
    }

    public int getIndexByParam(T param) {
        for (int i = 0; i < mConfigItems.size(); i++) {
            if (mConfigItems.get(i).mPrefValue.equals(param)) {
                return i;
            }
        }
        return -1;
    }

    protected static class ConfigItem<T> {
        protected T mPrefValue;
        protected int mNameResource;
        protected int mCircleColor;
        protected int mPicture;

        public ConfigItem(T prefValue, int nameResource, int circleColor, int picture) {
            mPrefValue = prefValue;
            mNameResource = nameResource;
            mCircleColor = circleColor;
            mPicture = picture;
        }

        public ConfigItem(T prefValue, int nameResource, int circleColor) {
            mPrefValue = prefValue;
            mNameResource = nameResource;
            mCircleColor = circleColor;
        }
    }

    public View.OnClickListener getItemClickListener(final ConfigItem<T> configItem, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPref(configItem.mPrefValue);
                notifyDataSetChanged();
                mSelectedParam = position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BusProvider.getInstance().post(new CloseActivityEvent());
                    }
                }, 300);
            }
        };
    }

    public abstract int getItemLayout();

    public abstract void saveSharedPref(T element);

}

