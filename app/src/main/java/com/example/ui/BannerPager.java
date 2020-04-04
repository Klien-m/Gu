package com.example.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.gu.R;
import com.example.listener.BannerClickListener;
import com.example.utils.DipPxUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 图片切换，支持定时轮播
 * created by Gu at 2020/02/05
 */
@SuppressLint({"InflateParams", "ClickableViewAccessibility"})
public class BannerPager extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = "BannerPager";
    private Context mContext;
    private BannerClickListener mListener;
    private ViewPager mPager;
    private List<ImageView> mViewList = new ArrayList<>();
    private RadioGroup mGroup;
    private int mCount;
    private LayoutInflater mInflater;
    private BannerHandler mHandler;
    private int dip_13;
    private static int mInterval = 3000;

    public BannerPager(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setOnBannerListener(BannerClickListener listener) {
        mListener = listener;
    }

    public void start() {
        mHandler.sendEmptyMessageDelayed(0, mInterval);
    }

    public void setImage(ArrayList<Integer> imageList) {
        for (int i = 0; i < imageList.size(); i++) {
            Integer imageID = imageList.get(i).intValue();
            ImageView ivNew = new ImageView(mContext);
            ivNew.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            /**
             * ScaleType决定了图片在View上显示的样子，如进行何种比例的缩放，及显示图片的整体还是部分
             * CENTER截图图片的居中部分显示
             * CENTER_CROP按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)
             * CENTER_INSIDE将图片的内容完整居中显示，通过按比例缩小或原来的size使得图片长/宽等于或小于View的长/宽
             * FIT_CENTER把图片按比例扩大/缩小到View的宽度，居中显示
             * FIT_START,FIT_END缩放效果与FIT_CENTER一样，只是位置不同
             * FIX_XY不按比例缩放图片，目标是把图片塞满整个View
             */
            ivNew.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ivNew.setImageResource(imageID);
            ivNew.setOnClickListener(this);
            mViewList.add(ivNew);
        }
        mPager.setAdapter(new ImageAdapter());
        mPager.addOnPageChangeListener(new BannerChangeListener(this));
        mCount = imageList.size();
        for (int i = 0; i < mCount; i++) {
            RadioButton radio = new RadioButton(mContext);
            radio.setLayoutParams(new RadioGroup.LayoutParams(dip_13, dip_13));
            radio.setGravity(Gravity.CENTER);
            radio.setButtonDrawable(R.drawable.rbt_selector);
            mGroup.addView(radio);
        }
        mPager.setCurrentItem(0);
        setButton(0);
        mHandler = new BannerHandler();
        start();
    }

    private void setButton(int position) {
        ((RadioButton) mGroup.getChildAt(position)).setChecked(true);
    }

    private void init() {
        mInflater = ((Activity) mContext).getLayoutInflater();
        View view = mInflater.inflate(R.layout.banner_pager, null);
        mPager = view.findViewById(R.id.banner_pager);
        mGroup = view.findViewById(R.id.banner_pager_group);
        addView(view);
        dip_13 = DipPxUtils.dip2px(mContext, 13);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @SuppressLint("HandlerLeak")
    private class BannerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            scrollToNext();
            sendEmptyMessageDelayed(0, mInterval);
        }
    }

    private void scrollToNext() {
        int index = mPager.getCurrentItem() + 1;
        if (mViewList.size() <= index) {
            index = 0;
        }
        mPager.setCurrentItem(index);
    }

    private class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mViewList.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }
    }

    private class BannerChangeListener implements ViewPager.OnPageChangeListener {
        private BannerChangeListener(BannerPager bannerPager) {}

        @Override
        public void onPageScrollStateChanged(int state) {}

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            setButton(position);
        }
    }

    @Override
    public void onClick(View v) {
        int position = mPager.getCurrentItem();
        mListener.onBannerClick(position);
    }
}
