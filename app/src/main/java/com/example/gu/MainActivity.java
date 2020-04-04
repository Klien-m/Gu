package com.example.gu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private DividerItemDecoration mDivider;
    private List<String> practises = new ArrayList<>();

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void init() {
        initList();
        initView();
    }
    private void initList() {
        practises.add("可变字符串");
        practises.add("底部标签栏");
        practises.add("ViewPager实现Banner");
        practises.add("ViewFlipper实现Banner");
        practises.add("kotlin初次尝试");
        practises.add("LoadingView");
        practises.add("CardView");
        practises.add("通知");
    }

    private void initView() {
        mContext = getApplicationContext();
        recyclerView = findViewById(R.id.rv_practise);
        mAdapter = new RecyclerViewAdapter(mContext, practises,false);
        mLayoutManager = new LinearLayoutManager(mContext);
        mDivider = new DividerItemDecoration(recyclerView.getContext(), mLayoutManager.getOrientation());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(mDivider);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onClick(position);
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("各种学习小测试啦");
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);    // 返回箭头
        getSupportActionBar().setLogo(R.drawable.logo);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_commonly:
                        Toast("暂未实现呢");
                        break;
                    case R.id.action_waterfall:
                        Toast("暂未实现呢，瀑布流");
//                        mAdapter = new RecyclerViewAdapter(mContext, practises, true);
//                        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
//                        recyclerView.setAdapter(mAdapter);
                        break;
                }
                return true;
            }
        });
    }

    private void onClick(int position) {
        switch (position) {
            case 0:
                Intent i0 = new Intent(this, MutableStringActivity.class);
                startActivity(i0);
                break;
            case 1:
//                Intent i1 = new Intent(this, TabBarActivity.class);
//                startActivity(i1);
                break;
            case 2:
                Intent i2 = new Intent(this, BannerPagerActivity.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3 = new Intent(this, BannerFlipperActivity.class);
                startActivity(i3);
                break;
            case 4:
                Intent i4 = new Intent(this, KotlinMinActivity.class);
                startActivity(i4);
                break;
            case 5:
                CharSequence[] cs = {"普通点er的加载动画", "骚气的加载动画"};
                Dialog dialog = createDialog(MainActivity.this, cs);
                dialog.show();
                break;
            case 6:
                Intent i6 = new Intent(this, CardViewActivity.class);
                startActivity(i6);
            case 7:
                Intent i7 = new Intent(this, NotificationActivity.class);
                startActivity(i7);
        }
    }

    private Dialog createDialog(final Context context, CharSequence[] array) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("随便选一个")
                .setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        if (which == 0) {
                            intent = new Intent(context, LoadingActivity.class);
                        } else {
                            intent = new Intent(context, LoadingDrawableActivity.class);
                        }
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

    private void Toast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}