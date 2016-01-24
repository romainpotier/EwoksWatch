package fr.romainpotier.ewokswatch.adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.romainpotier.ewokswatch.R;

public class ListAdapter extends WearableListView.Adapter {

    private final LayoutInflater mInflater;
    private boolean mAmbientMode;

    protected Context mContext;

    private static final List<ConfigItem> sConfigItems;

    static {
        sConfigItems = new ArrayList<>(6);
        sConfigItems.add(new ConfigItem(1000 * 60, R.string.one_minute_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 5, R.string.five_minutes_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 10, R.string.ten_minutes_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 30, R.string.thirty_minutes_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 60, R.string.one_hour_reload));
        sConfigItems.add(new ConfigItem(1000 * 60 * 60 * 3, R.string.three_hours_reload));
    }

    public ListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public boolean isAmbientMode() {
        return mAmbientMode;
    }

    public void setAmbientMode(boolean ambientMode) {
        mAmbientMode = ambientMode;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.name);
            mImageView = (ImageView) itemView.findViewById(R.id.circle);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {

        final ConfigItem configItem = sConfigItems.get(position);

        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.mTextView;

        view.setText(mContext.getString(configItem.mDurationNameResource));

        holder.itemView.setTag(position);

//        if (!mAmbientMode) {
////            loadRowImage(position, itemHolder.mImageView);
//            holder.itemView.setOnClickListener(getRowListener(position));
//            itemHolder.mTextView.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
//            itemHolder.mTextView.getPaint().setAntiAlias(true);
//        } else {
//            itemHolder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ambient_circle));
//            itemHolder.mTextView.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
//            itemHolder.mTextView.getPaint().setAntiAlias(false);
//        }

    }

    @Override
    public int getItemCount() {
        return sConfigItems.size();
    }

//    public abstract View.OnClickListener getRowListener(int position);
//
//    public abstract int getListItemLayout();

    private static class ConfigItem {
        long mDurationPicture;
        int mDurationNameResource;

        public ConfigItem(long durationPicture, int durationNameResource) {
            mDurationPicture = durationPicture;
            mDurationNameResource = durationNameResource;
        }
    }

}

