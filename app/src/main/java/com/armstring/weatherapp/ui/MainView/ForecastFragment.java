package com.armstring.weatherapp.ui.MainView;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.armstring.weatherapp.R;
import com.armstring.weatherapp.bean.Forecast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {


    public ForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_forcast, container, false);
        TextView txtForecastDate = (TextView)mView.findViewById(R.id.txtForecastDate);
        TextView txtForecastHigh = (TextView)mView.findViewById(R.id.txtForecastHigh);
        TextView txtForecastLow = (TextView)mView.findViewById(R.id.txtForecastLow);
        TextView txtForecastDescription = (TextView)mView.findViewById(R.id.txtForecastDescription);
        //note that method newInstance returns ForecastFragment attached with it a bundle
        //this bundle containing Forecast object coming from the main activity.
        Forecast forecast = (Forecast) getArguments().getSerializable("forecast");
        txtForecastDate.setText(forecast.getForecastDate());
        txtForecastHigh.setText(forecast.getForecastHighTemp());
        txtForecastLow.setText(forecast.getForecastLowTemp());
        txtForecastDescription.setText(forecast.getWeatherDescription());

        return mView;
    }

    public static final ForecastFragment newInstance(Forecast forecast){
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("forecast", forecast);

        forecastFragment.setArguments(bundle);
        return forecastFragment;
    }

}
