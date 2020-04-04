package com.example.utils;

import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class TextViewUtils {

    //TODO 根据字的个数多少变化字体大写，失败
    public static void autoMatchFont(final TextView view) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                boolean isNeedAdapta = true;
                float mWidth = v.getWidth();
                TextPaint paint = view.getPaint();
                String text = view.getText().toString();
                float textLen = paint.measureText(text);
                float oldSize = view.getTextSize();
                if (textLen != mWidth && isNeedAdapta) {
                    isNeedAdapta = false;
                    float size = mWidth*oldSize/textLen;
                    view.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
                }
            }
        });
    }
}
