package com.example.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.example.gu.R;
import com.example.listener.BannerClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Flipper实现Banner
 * created by Gu at 2020/02/05
 */
public class BannerFlipper extends RelativeLayout {
    private static final String TAG = "BannerFlipper";

    private Context mContext;
    private BannerClickListener mListener;
    private ViewFlipper mFlipper;
    private LinearLayout mLayout;
    private int mCount;
    private List<Button> mButtonList = new ArrayList<>();
    private LayoutInflater mInflater;
    private GestureDetector mGesture;
    private float mFlipGap = 20f;

    public BannerFlipper(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BannerFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setOnBannerListener(BannerClickListener listener) {
        mListener = listener;
    }

    public void start() {
        startFlip();
    }

    public void setImage(ArrayList<Integer> imageList) {
        for (int i = 0; i < imageList.size(); i++) {
            Integer imageID = ((Integer) imageList.get(i)).intValue();
            ImageView ivNew = new ImageView(mContext);
            ivNew.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            ivNew.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivNew.setImageResource(imageID);
            mFlipper.addView(ivNew);
        }
        mCount = imageList.size();
        for (int i = 0; i < mCount; i++) {
            View view = mInflater.inflate(R.layout.banner_flipper_button, null);
            Button button = (Button) view.findViewById(R.id.banner_btn_num);
            mLayout.addView(view);
            mButtonList.add(button);
        }
        mFlipper.setDisplayedChild(mCount - 1);
        startFlip();
    }

    private void setButton(int position) {
        if (mCount > 1) {
            for (int m=0; m<mCount; m++) {
                if (m==position%mCount) {
                    mButtonList.get(m).setBackgroundResource(R.drawable.icon_point_c);
                } else {
                    mButtonList.get(m).setBackgroundResource(R.drawable.icon_point_n);
                }
            }
        }
    }

    private void init() {
        mInflater = ((Activity) mContext).getLayoutInflater();
        View view = mInflater.inflate(R.layout.banner_flipper, null);
        mFlipper = view.findViewById(R.id.banner_flipper);
        mLayout = view.findViewById(R.id.banner_flipper_num);
        addView(view);
        // 该手势的onSingleTapUp事件是点击时进入广告页
        mGesture = new GestureDetector(mContext, new BannerGestureListener());
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        mGesture.onTouchEvent(event);
        return true;
    }

    private class BannerGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()-e2.getX()>mFlipGap){
                startFlip();
                return true;
            }
            if (e1.getX()-e2.getX()<-mFlipGap){
                backFlip();
                return true;
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) { }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            boolean result = false;
            if(Math.abs(distanceY)<Math.abs(distanceX)){
                BannerFlipper.this.getParent().requestDisallowInterceptTouchEvent(false);
                result = true;
            }
            return result;
        }

        @Override
        public void onShowPress(MotionEvent e) {}

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mFlipper.getDisplayedChild();
            mListener.onBannerClick(position);
            return false;
        }
    }

    private void startFlip() {
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
        mFlipper.showNext();
    }

    private void backFlip() {
        mFlipper.startFlipping();
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_out));
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
    }

    private class BannerAnimationListener implements Animation.AnimationListener {
        private BannerAnimationListener(BannerFlipper bannerFlipper) {}

        @Override
        public void onAnimationEnd(Animation animation) {
            int position = mFlipper.getDisplayedChild();
            setButton(position);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}

        @Override
        public void onAnimationStart(Animation animation) {}
    }
}
