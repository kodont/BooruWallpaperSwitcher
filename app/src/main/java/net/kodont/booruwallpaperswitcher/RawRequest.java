package net.kodont.booruwallpaperswitcher;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

public class RawRequest extends Request<byte[]> {

    private final Response.Listener<byte[]> listener;

    public RawRequest(
            int method,
            String url,
            Response.Listener<byte[]> conListener,
            Response.ErrorListener errorListener
    ) {
        super(method, url, errorListener);

        listener = conListener;
    }

    public RawRequest(
            String url,
            Response.Listener<byte[]> conListener,
            Response.ErrorListener errorListener
    ) {
        this(Method.GET, url, conListener, errorListener);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        if (response.data == null) {
            return Response.error(new VolleyError(response));
        }

        HttpHeaderParser.parseCharset(response.headers);
        return Response.success(response.data,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(byte[] response) {

        listener.onResponse(response);
    }
}
