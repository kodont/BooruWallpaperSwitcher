package net.kodont.booruwallpaperswitcher.AutomaticImageFetcher;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.kodont.booruwallpaperswitcher.RawRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;

public class JSONArrayListener implements Response.Listener<JSONArray> {

    private Context mContext;
    private RequestQueue queue;

    public JSONArrayListener(Context context) {
        this.mContext = context;
        queue = Volley.newRequestQueue(context);
    }

    public JSONArrayListener(Context context, RequestQueue queue) {
        this.mContext = context;
        this.queue = queue;
    }

    @Override
    public void onResponse(JSONArray response) {

        try {
            String url = response.getJSONObject(new Random().
                    nextInt(response.length())).
                    getString("file_url");
            queue.add(new RawRequest(
                url,
                new RawResponseListener(mContext, queue),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.getMessage(), error);
                    }
                }
            ));
        }
        catch (JSONException e) {
            Log.e("JSONException", e.getMessage(), e);
        }
    }
}
