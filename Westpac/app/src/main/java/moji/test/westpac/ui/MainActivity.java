package moji.test.westpac.ui;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import moji.test.westpac.R;
import moji.test.westpac.restful.request.WeatherRequest;
import moji.test.westpac.restful.response.WeatherResponse;
import moji.test.westpac.util.AsyncCallback;
import moji.test.westpac.util.WestPacError;

public class MainActivity extends ActionBarActivity implements LocationListener {

    private static int LOCATION_REFRESH_TIME = 1;
    private static int LOCATION_REFRESH_DISTANCE = 1;

    private TextView tvLocation;
    private TextView tvSummary;
    private TextView tvTemperature;

    private LocationManager mLocationManager;
    private Location lastSuccessfulLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locateDevice();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_refresh) {

            if(lastSuccessfulLocation == null){
                locateDevice();
            }else{
                callWeatherAPI(lastSuccessfulLocation.getLatitude(), lastSuccessfulLocation.getLongitude());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(final Location location) {
        mLocationManager.removeUpdates(this);
        callWeatherAPI(location.getLatitude(), location.getLongitude());
        lastSuccessfulLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void setViews() {
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvSummary = (TextView) findViewById(R.id.tv_summary);
        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
    }

    private void refreshWeatherPanel(String summary, String temperature) {
        if(lastSuccessfulLocation != null) {
            tvLocation.setText(getString(R.string.latitude_longitude,
                    String.format("%.2f", lastSuccessfulLocation.getLatitude())
                    , String.format("%.2f", lastSuccessfulLocation.getLongitude())));
        }
        tvSummary.setText(getString(R.string.summary, summary));
        tvTemperature.setText(getString(R.string.temperature, temperature));
    }

    private void setWeatherPanelTitle(String title) {
        tvLocation.setText(title);
    }

    private void callWeatherAPI(double latitude, double longitude) {
        WeatherRequest weatherRequest = new WeatherRequest(latitude, longitude);
        setWeatherPanelTitle(getString(R.string.connecting_to_server));
        weatherRequest.produceWorker().execute(new AsyncCallback<WeatherResponse>() {
            @Override
            public void onSuccess(WeatherResponse result) {
                refreshWeatherPanel(result.summary, result.temperature);
            }

            @Override
            public void onError(WestPacError err) {
                setWeatherPanelTitle(err.getErrorMsg());
            }
        });
    }

    private void locateDevice() {
        setWeatherPanelTitle(getString(R.string.finding_location));
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, this);
    }
}
