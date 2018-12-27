package com.example.myjob.servic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.myjob.Main2Activity;
import com.example.myjob.MainActivity;

import cn.jpush.android.api.JPushInterface;

public class MyService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //打开自定义的Activity
            Intent i = new Intent(context, Main2Activity.class);
            i.putExtra("pid",1+"");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);
        }

    }
}
