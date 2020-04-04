package com.example.gu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnOri;
    private Button btnFold;
    private Button btnHang;
    private Toolbar toolbar;
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        btnOri = findViewById(R.id.ordinary);
        btnFold = findViewById(R.id.folding);
        btnHang = findViewById(R.id.hang);

        toolbar = findViewById(R.id.noti_tool_bar);
        toolbar.setTitle("各种通知");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnOri.setOnClickListener(this);
        btnFold.setOnClickListener(this);
        btnHang.setOnClickListener(this);


        notificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ordinary:
                Notification.Builder oriBuilder = createBuilder(this,"普通通知","安卓中文开发文档");
                notificationManager.notify(1, oriBuilder.build());
                break;
            case R.id.folding:
                Notification.Builder foldBuilder = createBuilder(this,"折叠式通知","安卓中文开发文档");
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.view_fold);
                Notification notification = foldBuilder.build();
//                builder.setCustomBigContentView(remoteViews);
                notification.bigContentView = remoteViews;
                notificationManager.notify(2, notification);
                break;
            case R.id.hang:
                Notification.Builder hangBuilder = createBuilder(this,"悬挂式通知","安卓中文开发文档");
                Intent hangIntent = new Intent();
                hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                hangIntent.setClass(this, NotificationActivity.class);
                PendingIntent hangPendingIntent = PendingIntent.getActivity(this,
                        0,hangIntent,PendingIntent.FLAG_CANCEL_CURRENT);
                hangBuilder.setFullScreenIntent(hangPendingIntent, true);
                notificationManager.notify(3, hangBuilder.build());
                break;
        }
    }

    private Notification.Builder createBuilder(Context context, String title, String text) {
        Notification.Builder builder = new Notification.Builder(context);
        Intent mIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://developer.android.com/reference/android/service/" +
                        "notification/package-summary?hl=en"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,mIntent,0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.logo);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.banner_1));
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(text);
        // 设置显示等级
        // VISIBILITY_PUBLIC 任何情况都会显示通知;
        // VISIBILITY_PRIVATE 只有在没有锁屏时会显示通知;
        // VISIBILITY_SECRET 在pin、password等安全锁和没有锁屏的情况下才能够显示通知
//        builder.setVisibility();
        return builder;
    }
}
