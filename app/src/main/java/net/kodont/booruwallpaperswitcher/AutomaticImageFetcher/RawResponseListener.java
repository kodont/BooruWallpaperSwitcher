package net.kodont.booruwallpaperswitcher.AutomaticImageFetcher;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.kodont.booruwallpaperswitcher.OnRequestFinishedListener;
import net.kodont.booruwallpaperswitcher.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RawResponseListener implements Response.Listener<byte[]> {

    private Context mContext;
    private RequestQueue queue;

    RawResponseListener(Context context) {
        this.mContext = context;
        this.queue = Volley.newRequestQueue(context);
    }

    RawResponseListener(Context context, RequestQueue queue) {
        this.mContext = context;
        this.queue = queue;
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
            WallpaperManager manager = WallpaperManager.getInstance(mContext);
            manager.setBitmap(BitmapFactory.decodeFile(filePath));
        }
        catch (IOException e) {
            Log.e("IOException", e.getMessage(), e);
            return;
        }

        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            ImageView iv = activity.findViewById(R.id.imageView);
            iv.setImageBitmap(BitmapFactory.decodeFile(filePath));
        }
    }

}
