package com.armstring.weatherapp.ui.MainView;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.armstring.weatherapp.R;
import com.armstring.weatherapp.model.ForecastData;
import com.armstring.weatherapp.callback.ForecastListAsyncResponse;
import com.armstring.weatherapp.ui.adapter.ForecastViewPagerAdapter2;
import com.armstring.weatherapp.bean.Forecast;
import com.armstring.weatherapp.utils.network.NetworkHelper;
import com.armstring.weatherapp.utils.Preferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Armstring";
    private ForecastViewPagerAdapter2 forecastViewPagerAdapter;

    private ViewPager mViewPager;
    private ImageView imgWeatherIcon;
    private TextView txtLocation;
    private TextView txtCurrentTemp;
    private TextView txtCurrentDate;
    private EditText edtLocation;
    private String enteredLocation;
    private boolean returned = false;
    private boolean firstTime = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Preferences preferences = new Preferences(MainActivity.this);
        final String lastLocation = preferences.getLocation();
        final ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.constraintLayoutId);
        ConstraintLayout layoutMain = (ConstraintLayout)findViewById(R.id.constraintLayoutId2);
        if(!NetworkHelper.hasNetworkStatus(MainActivity.this)){
            layout.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        layoutMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(layout.getVisibility() == View.INVISIBLE){
                    if(NetworkHelper.hasNetworkStatus(MainActivity.this)){
                        layout.setVisibility(View.VISIBLE);
                        getWeather(lastLocation);
                    }else{
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });
        txtCurrentTemp = (TextView) findViewById(R.id.txtTemp);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtCurrentDate = (TextView) findViewById(R.id.txtCurrentDateId);
        edtLocation = (EditText) findViewById(R.id.edtLocation);
        imgWeatherIcon = (ImageView) findViewById(R.id.imgWeatherIcon);
        mViewPager = findViewById(R.id.viewPagerId);



        getWeather(lastLocation);
        edtLocation.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyEvent.getAction() == keyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    enteredLocation = edtLocation.getText().toString();
                    edtLocation.setText("");
                    preferences.setLocation(enteredLocation);
                    getWeather(enteredLocation);
                    if(NetworkHelper.hasNetworkStatus(MainActivity.this)){

                    }else{
                        Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void getWeather(String currentLocation) {
        forecastViewPagerAdapter = new ForecastViewPagerAdapter2(getSupportFragmentManager(), getFragments(currentLocation));
    }

    private List<Fragment> getFragments(String loc){
        final List<Fragment> fragments = new ArrayList<>();
        new ForecastData().getForecast(new ForecastListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Forecast> forecastArrayList) {
                txtLocation.setText(forecastArrayList.get(0).getCity() + "\n" + forecastArrayList.get(0).getRegion());
                txtCurrentTemp.setText(forecastArrayList.get(0).getCurrentTemp() + "C");
                txtCurrentDate.setText(forecastArrayList.get(0).getDate());
                String html = forecastArrayList.get(0).getHtmlDescription();
                Picasso.with(MainActivity.this).load(getImageUrl(html)).into(imgWeatherIcon);
                for(int i = 0; i < forecastArrayList.size(); i++){
                    Forecast newForecast = forecastArrayList.get(i);
                    Log.d(TAG, "processFinished: " + newForecast.getCity() + fragments.size());
                    ForecastFragment fragment = ForecastFragment.newInstance(newForecast);
                    fragments.add(fragment);
                }

//                Log.d(TAG, "processFinished: fragments.size()" + fragments.size());
            }

            @Override
            public void setTheAdapter() {
//                Log.d(TAG, "setTheAdapter: 1111111111");
//                Log.d(TAG, "setTheAdapter: 1111111111 fragments.size(): " + fragments.size());

                mViewPager.setAdapter(forecastViewPagerAdapter);


            }
        }, loc, getApplicationContext());
        return fragments;
    }

    private String getImageUrl(String html){
        String imgRegex = "(?i)<img[^>]+?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
        String imgSource = null;


        Pattern p = Pattern.compile(imgRegex);
        Matcher m = p.matcher(html);
        while (m.find()){
            Log.d(TAG, "getImageUrl: found");
            imgSource = m.group(1);
        }
        return imgSource;
    }
}
