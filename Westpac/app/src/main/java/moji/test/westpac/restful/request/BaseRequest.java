package moji.test.westpac.restful.request;

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by mojtaba on 11/26/2015.
 */
public class BaseRequest {

    public enum HttpMethod {
        GET,
        POST,
        UNKNOWN
    }

    public HashMap<String, String> params;
    public String path;
    public HttpMethod method = HttpMethod.UNKNOWN;

    public BaseRequest() {
        params = new HashMap<>();

    }

    public AsyncTask produceWorker() {
        return null;
    }

}
