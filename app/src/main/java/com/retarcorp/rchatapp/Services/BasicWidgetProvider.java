package com.retarcorp.rchatapp.Services;

import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.retarcorp.rchatapp.MainActivity;
import com.retarcorp.rchatapp.R;

import java.util.Random;

/**
 * Created by CaptainOsmant on 23.01.2018.
 */

public class BasicWidgetProvider extends AppWidgetProvider {

    @Override
    public void onDeleted(Context context, int[] widgetIds){
        super.onDeleted(context, widgetIds);
        //Toast.makeText(context, "onDeleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context ctx){
        super.onDisabled(ctx);
        //Toast.makeText(ctx, "onDisabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
        //Toast.makeText(context, "onEnabled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] widgetIds){
       super.onUpdate(context, manager, widgetIds);
        //Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Random rnd = new Random();
        views.setTextViewText(R.id.widget_text, rnd.nextInt(100000000)+99999999+"");
        Intent intent = new Intent(context,BasicWidgetProvider.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("OPEN");
        for(int id: widgetIds) {
            Intent refreshIntent = new Intent(context, getClass());
            refreshIntent.setAction("REFRESH");
            views.setOnClickPendingIntent(R.id.widget_layout
                    , PendingIntent.getBroadcast(context, 0, refreshIntent, 0));
            views.setOnClickFillInIntent(R.id.widget_layout,intent);
            manager.updateAppWidget(id,views);
        }
        //manager.updateAppWidget(widgetIds, views);

    }

    @Override
    public void onReceive(Context ctx, Intent intent){
        super.onReceive(ctx, intent);

        Toast.makeText(ctx, "intent received: "+intent.getAction(),Toast.LENGTH_SHORT).show();
        if("REFRESH".equals(intent.getAction())) {
            AppWidgetManager manager = AppWidgetManager.getInstance(ctx);
            RemoteViews views = new RemoteViews(ctx.getPackageName(),R.layout.widget_layout);
            ComponentName widget = new ComponentName(ctx, BasicWidgetProvider.class);
            Random rnd = new Random();
            String pos = rnd.nextInt(100000000)+99999999+"";

            views.setTextViewText(R.id.widget_text,pos);
            manager.updateAppWidget(widget, views);

        }
    }

}
