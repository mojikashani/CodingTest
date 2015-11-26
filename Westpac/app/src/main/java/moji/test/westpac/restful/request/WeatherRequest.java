package moji.test.westpac.restful.request;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import moji.test.westpac.restful.worker.WeatherWorker;

/**
 * Created by mojtaba on 11/26/2015.
 */
public class WeatherRequest extends BaseRequest {
    private static String LATITUDE = "latitude";
    private static String LONGITUDE = "longitude";
    private static String URL = "https://api.forecast.io/forecast/528c4d77825034af4696a2bc515a9f23/";

    public WeatherRequest(double latitude, double longitude) {
        path = URL;
        method = HttpMethod.GET;
        params.put(LATITUDE, String.valueOf(latitude));
        params.put(LONGITUDE, String.valueOf(longitude));
    }

    public URL getFullUrlForGetMethod() {
        URL url;
        try {
            url = new URL(path + params.get(LATITUDE) + "," + params.get(LONGITUDE));
        }catch (MalformedURLException ex)
        {
            url = null;
        }
        return url;
    }

    @Override
    public WeatherWorker produceWorker() {
        return new WeatherWorker(this);
    }

}

