package net.kodont.booruwallpaperswitcher;

import android.app.WallpaperManager;
import android.graphics.BitmapFactory;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import net.kodont.booruwallpaperswitcher.AutomaticImageFetcher.JSONArrayListener;

import java.io.IOException;

public class WallpaperAlarmListener
        extends BroadcastReceiver
{
    public static final String FILE_PATH = "file_path";
    public static final String NEXT_IMAGE_URL = "imageURL";

    String mFilePath;
    String mImageURL;
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mFilePath = intent.getStringExtra(FILE_PATH);
        mImageURL = intent.getStringExtra(NEXT_IMAGE_URL);
        mContext = context;

        try {
            WallpaperManager.
                    getInstance(mContext).
                    setBitmap(BitmapFactory.decodeFile(mFilePath));
        } catch (IOException e) {
            Log.e("IOException", e.getMessage(), e);
        }

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(new JsonArrayRequest(
                Request.Method.GET,
                mImageURL,
                null,
                new JSONArrayListener(mContext, queue),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.getMessage(), error);
                    }
                }
        ));

    }

}
