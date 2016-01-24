/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.romainpotier.ewokswatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.text.format.Time;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MyWatchFace extends CanvasWatchFaceService {

    private static final Typeface BOLD_TYPEFACE =
            Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD);

    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1);

    private static final int MSG_UPDATE_TIME = 0;

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private static class EngineHandler extends Handler {
        private final WeakReference<MyWatchFace.Engine> mWeakReference;

        public EngineHandler(MyWatchFace.Engine reference) {
            mWeakReference = new WeakReference<>(reference);
        }

        @Override
        public void handleMessage(Message msg) {
            MyWatchFace.Engine engine = mWeakReference.get();
            if (engine != null) {
                switch (msg.what) {
                    case MSG_UPDATE_TIME:
                        engine.handleUpdateTimeMessage();
                        break;
                }
            }
        }
    }

    private static final ResourceCollection[] RESOURCES = {
            new ResourceCollection(R.drawable.ewok1, R.drawable.ewok_ambient1, R.color.middlegrey, R.color.orange, R.color.white),
            new ResourceCollection(R.drawable.ewok2, R.drawable.ewok_ambient2, R.color.green, R.color.red, R.color.white),
            new ResourceCollection(R.drawable.ewok3, R.drawable.ewok_ambient3, R.color.yellow, R.color.green, R.color.darkgrey),
            new ResourceCollection(R.drawable.ewok4, R.drawable.ewok_ambient4, R.color.orange, R.color.darkgrey, R.color.white),
            new ResourceCollection(R.drawable.ewok5, R.drawable.ewok_ambient5, R.color.red, R.color.darkgrey, R.color.white),
            new ResourceCollection(R.drawable.ewok3, R.drawable.ewok_ambient3, R.color.yellow, R.color.green, R.color.darkgrey),
            new ResourceCollection(R.drawable.ewok4, R.drawable.ewok_ambient4, R.color.orange, R.color.darkgrey, R.color.white),
            new ResourceCollection(R.drawable.ewok6, R.drawable.ewok_ambient6, R.color.green, R.color.beige, android.R.color.white)
    };

    private static class ResourceCollection {
        int picture;
        int backgroundColor;
        int hoursColor;
        int minutesColor;
        int pictureAmbient;

        public ResourceCollection(int picture, int pictureAmbient, int backgroundColor, int hoursColor, int minutesColor) {
            this.picture = picture;
            this.pictureAmbient = pictureAmbient;
            this.backgroundColor = backgroundColor;
            this.hoursColor = hoursColor;
            this.minutesColor = minutesColor;
        }

    }

    private class Engine extends CanvasWatchFaceService.Engine {
        final Handler mUpdateTimeHandler = new EngineHandler(this);
        boolean mRegisteredTimeZoneReceiver = false;

        Paint mTextPaintHours;
        Paint mTextPaintMinutes;

        boolean mAmbient;
        Time mTime;
        final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTime.clear(intent.getStringExtra("time-zone"));
                mTime.setToNow();
            }
        };
        int mTapCount;

        float mXOffset;
        float mYOffset;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            setWatchFaceStyle(new WatchFaceStyle.Builder(MyWatchFace.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_VARIABLE)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false)
                    .setStatusBarGravity(Gravity.RIGHT | Gravity.TOP)
                    .setAcceptsTapEvents(true)
                    .build());
            Resources resources = MyWatchFace.this.getResources();
            mYOffset = resources.getDimension(R.dimen.digital_y_offset);

            mTextPaintHours = createTextPaintHours();
            mTextPaintMinutes = createTextPaintMinutes();

            mTime = new Time();
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            super.onDestroy();
        }

        private Paint createTextPaintHours() {
            Paint paint = new Paint();
            paint.setTypeface(BOLD_TYPEFACE);
            paint.setAntiAlias(true);
            return paint;
        }

        private Paint createTextPaintMinutes() {
            Paint paint = new Paint();
            paint.setTypeface(Typeface.createFromAsset(getAssets(), "roboto_light.ttf"));
            paint.setAntiAlias(true);
            return paint;
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                registerReceiver();

                mTime.clear(TimeZone.getDefault().getID());
                mTime.setToNow();
            } else {
                unregisterReceiver();
            }

            updateTimer();
        }

        private void registerReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            MyWatchFace.this.registerReceiver(mTimeZoneReceiver, filter);
        }

        private void unregisterReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = false;
            MyWatchFace.this.unregisterReceiver(mTimeZoneReceiver);
        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            super.onApplyWindowInsets(insets);

            // Load resources that have alternate values for round watches.
            Resources resources = MyWatchFace.this.getResources();
            boolean isRound = insets.isRound();
            mXOffset = resources.getDimension(isRound
                    ? R.dimen.digital_x_offset_round : R.dimen.digital_x_offset);
            float textSize = resources.getDimension(isRound
                    ? R.dimen.digital_text_size_round : R.dimen.digital_text_size);

            mTextPaintHours.setTextSize(textSize);
            mTextPaintMinutes.setTextSize(textSize);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode;
                invalidate();
            }

            updateTimer();
        }

        @Override
        public void onTapCommand(int tapType, int x, int y, long eventTime) {
            Resources resources = MyWatchFace.this.getResources();
            switch (tapType) {
                case TAP_TYPE_TOUCH:
                    // The user has started touching the screen.
                    break;
                case TAP_TYPE_TOUCH_CANCEL:
                    // The user has started a different gesture or otherwise cancelled the tap.
                    break;
                case TAP_TYPE_TAP:
                    // The user has completed the tap gesture.
                    mTapCount++;
                    //mBackgroundPaint.setColor(resources.getColor(mTapCount % 2 == 0 ?
                    //        R.color.background : R.color.background2));
                    break;
            }
            invalidate();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {

            // Draw H:MM in pictureAmbient mode or H:MM:SS in interactive mode.
            mTime.setToNow();
            // Get resource with time
            int rang = mTime.hour / 3;
            ResourceCollection resourceCollection = RESOURCES[rang];

            final int screenSize = getScreenSize();

            Rect dest = new Rect(0, 0, screenSize, screenSize);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);

            int picture;

            // Draw the background.
            if (mAmbient) {
                paint.setColor(ContextCompat.getColor(MyWatchFace.this, android.R.color.black));

                picture = resourceCollection.pictureAmbient;

                mTextPaintHours.setColor(ContextCompat.getColor(MyWatchFace.this, android.R.color.white));
                mTextPaintMinutes.setColor(ContextCompat.getColor(MyWatchFace.this, android.R.color.white));

            } else {

                paint.setColor(ContextCompat.getColor(MyWatchFace.this, resourceCollection.backgroundColor));

                picture = resourceCollection.picture;

                mTextPaintHours.setColor(ContextCompat.getColor(MyWatchFace.this, resourceCollection.hoursColor));
                mTextPaintMinutes.setColor(ContextCompat.getColor(MyWatchFace.this, resourceCollection.minutesColor));

            }

            canvas.drawRect(dest, paint);

            Bitmap b = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), picture), screenSize , screenSize , true);
            canvas.drawBitmap(b, - (screenSize / 6), 0, null);

            String hours = String.format("%02d", mTime.hour);
            canvas.drawText(hours, screenSize * 0.63f, screenSize * 0.5f, mTextPaintHours);

            String minutes = String.format("%02d", mTime.minute);
            canvas.drawText(minutes, screenSize * 0.63f, screenSize * 0.7f, mTextPaintMinutes);


        }

        /**
         * Starts the {@link #mUpdateTimeHandler} timer if it should be running and isn't currently
         * or stops it if it shouldn't be running but currently is.
         */
        private void updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        /**
         * Returns whether the {@link #mUpdateTimeHandler} timer should be running. The timer should
         * only run when we're visible and in interactive mode.
         */
        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }

        /**
         * Handle updating the time periodically in interactive mode.
         */
        private void handleUpdateTimeMessage() {
            invalidate();
            if (shouldTimerBeRunning()) {
                long timeMs = System.currentTimeMillis();
                long delayMs = INTERACTIVE_UPDATE_RATE_MS
                        - (timeMs % INTERACTIVE_UPDATE_RATE_MS);
                mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
            }
        }
    }

    private int getScreenSize() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return Math.min(size.x, size.y);
    }

}
