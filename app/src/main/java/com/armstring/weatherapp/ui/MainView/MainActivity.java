package com.armstring.weatherapp.ui.MainView;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.armstring.weatherapp.R;
import com.armstring.weatherapp.contracts.MainContracts;
import com.armstring.weatherapp.ui.ForecastFragment;
import com.armstring.weatherapp.ui.MainView.adapter.ForecastViewPagerAdapter2;
import com.armstring.weatherapp.bean.Forecast;
import com.armstring.weatherapp.utils.network.NetworkHelper;
import com.armstring.weatherapp.utils.Preferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContracts.MainView{
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
    private MainContracts.MainPresenter presenter;
    private ArrayList<Forecast> forecastArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Preferences preferences = new Preferences(MainActivity.this);
        final String lastLocation = preferences.getLocation();
        final ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.constraintLayoutId);
        presenter = new MainPresenter(this);

        if(!NetworkHelper.hasNetworkStatus(MainActivity.this)){
            layout.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        handleNoInternetCase(lastLocation, layout);
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

    @Override
    public void getWeather(String currentLocation){
        presenter.getForecastArrayList(currentLocation);
    }


    @Override
    public void applyData(ArrayList<Forecast> data) {
        //getting date from model via presenter.
        forecastArrayList = data;
        //one time date filing
        txtLocation.setText(forecastArrayList.get(0).getCity() + "\n" + forecastArrayList.get(0).getRegion());
        txtCurrentTemp.setText(forecastArrayList.get(0).getCurrentTemp() + "C");
        txtCurrentDate.setText(forecastArrayList.get(0).getDate());
        String html = forecastArrayList.get(0).getHtmlDescription();
        Picasso.with(MainActivity.this).load(presenter.getImageUrl(html)).into(imgWeatherIcon);


        //viewPager date
        forecastViewPagerAdapter = new ForecastViewPagerAdapter2(getSupportFragmentManager(), getFragments());
        mViewPager.setAdapter(forecastViewPagerAdapter);
    }



    //helper method1
    private List<Fragment> getFragments(){
        final List<Fragment> fragments = new ArrayList<>();
        for(int i = 0; i < forecastArrayList.size(); i++){
            Forecast newForecast = forecastArrayList.get(i);
            ForecastFragment fragment = ForecastFragment.newInstance(newForecast);
            fragments.add(fragment);
        }
        return fragments;
    }

    //helper method2
    public void handleNoInternetCase(final String lastLocation, final ConstraintLayout layout) {
        ConstraintLayout layoutMain = (ConstraintLayout)findViewById(R.id.constraintLayoutId2);
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
    }
}