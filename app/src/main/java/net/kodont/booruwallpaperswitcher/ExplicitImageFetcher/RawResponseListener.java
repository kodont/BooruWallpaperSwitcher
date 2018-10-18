package net.kodont.booruwallpaperswitcher.ExplicitImageFetcher;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.kodont.booruwallpaperswitcher.MainActivity;
import net.kodont.booruwallpaperswitcher.OnRequestFinishedListener;
import net.kodont.booruwallpaperswitcher.WallpaperAlarmListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class RawResponseListener implements Response.Listener<byte[]> {

    private Context mContext;
    private RequestQueue queue;
    private String mFutureURL;

    RawResponseListener(Context context) {
        this.mContext = context;
        this.queue = Volley.newRequestQueue(context);
    }

    RawResponseListener(Context context, RequestQueue queue) {
        this.mContext = context;
        this.queue = queue;
    }

    RawResponseListener(Context context, RequestQueue queue, String futureURL) {
        this.mContext = context;
        this.queue = queue;
        this.mFutureURL = futureURL;
    }

    @Override
    public void onResponse(byte[] response) {

        String filePath = mContext.getFilesDir() + "testfile";
        try {
            File file = new File(filePath);
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream writer = new FileOutputStream(file);
            writer.write(response);
            writer.close();
        }
        catch (IOException e) {
            Log.e("IOException", e.getMessage(), e);
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        Intent intent = new Intent(mContext, WallpaperAlarmListener.class);
        intent.putExtra(WallpaperAlarmListener.FILE_PATH, filePath);
        intent.putExtra(WallpaperAlarmListener.NEXT_IMAGE_URL, mFutureURL);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        AlarmManager am = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        if (am != null)
            am.setInexactRepeating(
                    AlarmManager.RTC,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        else {
            Log.e("NullPointerAvoidance", "AlarmManager returns null pointer");
        }

        if (mContext instanceof OnRequestFinishedListener) {
            ((OnRequestFinishedListener) mContext).onRequestFinished();
        }
    }

}
