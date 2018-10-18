package net.kodont.booruwallpaperswitcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import net.kodont.booruwallpaperswitcher.ExplicitImageFetcher.JSONArrayListener;
import net.kodont.booruwallpaperswitcher.ExplicitImageFetcher.RawResponseListener;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity
        extends AppCompatActivity
        implements OnRequestFinishedListener
{

    public static final String PREFERENCES_NAME = "prefsName";
    public static final String TAGS_KEY = "tag_list";
    public static final String DAY_OF_WEEK = "day_of_week";

    Button fetchButton, clearButton, setButton;
    ImageView imageView;
    RequestQueue requestQueue;
    WallpaperManager wallpaperManager;
    String testFilePath;
    SharedPreferences mSharedPreferences;
    AlarmManager mAlarmManager;

    private static String TEST_URL =
            "https://gelbooru.com/index.php?page=dapi&s=post" +
                    "&q=index&json=1&limit=100&pid=1" +
                    "&tags=rating:safe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchButton = findViewById(R.id.fetchButton);
        clearButton = findViewById(R.id.clearButton);
        setButton = findViewById(R.id.setButton);
        imageView = findViewById(R.id.imageView);
        requestQueue = Volley.newRequestQueue(this);
        wallpaperManager = WallpaperManager.getInstance(this);
        testFilePath = getFilesDir() + "testfile";
        mSharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        File file = new File(testFilePath);
        if (file.exists())
            imageView.setImageBitmap(BitmapFactory.decodeFile(testFilePath));

     }

    public void onClickFetch(View view) {

        String requestURL = loadRequestURL();
        if (requestURL == null) {
            Toast.makeText(
                    this,
                    "No tags loaded!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        requestQueue.add(new JsonArrayRequest(
            Request.Method.GET,
            requestURL,
            null,
            new JSONArrayListener(this, requestQueue, loadRequestURL()),
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VolleyError", error.getMessage(), error);
                }
            }
        ));
    }

    public void onClickWallpaper(View view) {

        try {
            wallpaperManager.setBitmap(BitmapFactory.decodeFile(testFilePath));
        }
        catch (IOException e) {
            Log.e("IOException", e.getMessage(), e);
            Toast.makeText(this, "Unable to set wallpaper!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickClear(View view) {

        File file = new File(testFilePath);
        if (file.exists())
            file.delete();

        imageView.setImageResource(android.R.color.transparent);
    }

    public void toTagList(View view) {

        Intent toNext = new Intent(this, ThirdActivity.class);
        startActivity(toNext);
    }

    public void toTimeSetting(View view) {

        startActivity(new Intent(this, FourthActivity.class));
    }

    public void changeWallpaperImmediately(View view) {

        try {
            WallpaperManager manager = WallpaperManager.getInstance(this);
            manager.setBitmap(BitmapFactory.decodeFile(testFilePath));
        }
        catch (IOException e) {
            Log.e("IOException", e.getMessage(), e);
        }
    }

    /*
    public void jobTester(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(this, WallpaperAlarmListener.class);
        intent.putExtra(WallpaperAlarmListener.FILE_PATH, testFilePath);
        intent.putExtra(WallpaperAlarmListener.NEXT_IMAGE_URL, loadRequestURL());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        mAlarmManager.setInexactRepeating(
                AlarmManager.RTC,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);

        Toast.makeText(this, "Wallpaper queued for tomorrow!", Toast.LENGTH_SHORT).show();

    }
    */

    private String loadRequestURL() {

        StringBuilder urlBuffer = new StringBuilder(TEST_URL);
        urlBuffer.append("%20");

        Set<String> tagSet = mSharedPreferences.getStringSet(TAGS_KEY,
                new HashSet<String>());
        if(tagSet.isEmpty()) {
            return null;
        }
        for (String s: tagSet) {
            urlBuffer.append("%20");
            urlBuffer.append(s);
        }

        return urlBuffer.toString();
    }

    @Override
    public void onRequestFinished() {

        imageView.setImageBitmap(BitmapFactory.decodeFile(testFilePath));
        Toast.makeText(
                this,
                "Image queued for setting wallpaper",
                Toast.LENGTH_SHORT).show();
    }
}

