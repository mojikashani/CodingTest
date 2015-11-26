package moji.test.westpac.restful.worker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import moji.test.westpac.restful.request.WeatherRequest;
import moji.test.westpac.restful.response.WeatherResponse;
import moji.test.westpac.util.AsyncCallback;
import moji.test.westpac.util.WestPacError;

/**
 * Created by mojtaba on 11/26/2015.
 */
public class WeatherWorker extends AsyncTask<AsyncCallback<WeatherResponse>, Void, WeatherResponse> {
    private WeatherRequest request;
    AsyncCallback<WeatherResponse> callback = null;

    public WeatherWorker(WeatherRequest request) {
        this.request = request;
    }

    @Override
    protected WeatherResponse doInBackground(AsyncCallback<WeatherResponse>... params) {
        if (params.length > 0)
            callback = params[0];

        WeatherResponse weatherResponse = new WeatherResponse();

        HttpURLConnection conn = null;
        try {
            URL url = request.getFullUrlForGetMethod();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            JSONObject jsonObject;

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                jsonObject = new JSONObject(getResponseText(in));
                in.close();
                JSONObject currently = jsonObject.getJSONObject("currently");
                weatherResponse.summary = currently.get("summary").toString();
                weatherResponse.temperature = currently.get("temperature").toString();
            } else {
                weatherResponse.error = new WestPacError(1,"connection failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            weatherResponse.error = new WestPacError(1,"connection failed");
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return weatherResponse;
    }

    private static String getResponseText(InputStream inStream) {
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    @Override
    protected void onPostExecute(WeatherResponse weatherResponse) {
        if(callback != null) {
            if (weatherResponse.hasError())
                callback.onError(weatherResponse.error);
            else
                callback.onSuccess(weatherResponse);
        }
    }
}

