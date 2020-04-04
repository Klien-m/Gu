package com.example.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.gu.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class MutableStringFragment extends Fragment {

    TextView tvTitle;
    TextView tvContent;
    WebView wvPoerty;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mutable_string_fragment,container,false);
        tvTitle = view.findViewById(R.id.tv_title);
        tvContent = view.findViewById(R.id.tv_content);
        wvPoerty = view.findViewById(R.id.wv_poetry);
        String content = createString();
        tvContent.setText(content);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://baike.baidu.com/item/%E9%A2%98%E9%BE%99%E9%98%B3%E5%8E%BF%E9%9D%92%E8%8D%89%E6%B9%96";
                wvPoerty.loadUrl(url);
                wvPoerty.requestFocus();
                wvPoerty.setWebViewClient(new WebViewClient());
            }
        });
        return view;
    }

    private String createString(){
        SpannableString spanText = new SpannableString("西风吹老洞庭波，一夜湘君白发多。醉后不知天在水，满船清梦压星河。");
        //第四个参数用来标识在Span范围内的文本前后输入新的字符时是否把它们也应用这个效果（主要对EditText有用）。
        //SPAN_EXCLUSIVE_EXCLUSIVE（前后都不包括）
        //SPAN_INCLUSIVE_EXCLUSIVE（前面包括，后面不包括）
        //SPAN_EXCLUSIVE_INCLUSIVE（前面不包括，后面包括）
        //SPAN_INCLUSIVE_INCLUSIVE（前后都包括）

        //大小RelativeSizeSpan
        spanText.setSpan(new RelativeSizeSpan(1.5f),0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //加粗StyleSpan
        spanText.setSpan(new StyleSpan(Typeface.BOLD),8,16,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //改变颜色ForegroundColorSpan
        spanText.setSpan(new ForegroundColorSpan(Color.GRAY),16,24,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(Color.BLUE),24,32,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spanText.toString();
    }
}

