package au.com.melbourneapps.gdpworld.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import au.com.melbourneapps.gdpworld.R;
import au.com.melbourneapps.gdpworld.fragments.CountryGDPFragment;
import au.com.melbourneapps.gdpworld.models.CountryGDP;
import au.com.melbourneapps.gdpworld.network.HttpClient;
import au.com.melbourneapps.gdpworld.utilities.Common;
import au.com.melbourneapps.gdpworld.utilities.Constants;

public class CountryGDPActivity extends AppCompatActivity
    implements CountryGDPFragment.OnListFragmentInteractionListener {

    public static TextView titleTextView; //todo: replace static
    private Spinner yearSpinner;
    private Spinner orderBySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_countrygdp);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            titleTextView = findViewById(R.id.titleTextView);

            yearSpinner = findViewById(R.id.yearSpinner);
            yearSpinner.setOnItemSelectedListener(new yearSpinnerItemSelectedListener());

            orderBySpinner = findViewById(R.id.orderBySpinner);
            orderBySpinner.setOnItemSelectedListener(new orderBySpinnerItemSelectedListener());

            refreshData(String.valueOf(Constants.lastYear), Constants.defaultOrderBy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class yearSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {
                String selectedYear = String.valueOf(yearSpinner.getSelectedItem());
                refreshData(selectedYear, String.valueOf(orderBySpinner.getSelectedItem()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {
        }
    }

    public class orderBySpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {
                String selectedOrderBy = String.valueOf(orderBySpinner.getSelectedItem());
                refreshData(String.valueOf(yearSpinner.getSelectedItem()), selectedOrderBy);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg) {
        }
    }

    public void refreshData(String yearSelected, String orderBySelected){
        try {
            Common.data_Year = String.valueOf(Constants.lastYear);
            if (yearSelected != null){
                if (TextUtils.isDigitsOnly(yearSelected)) Common.data_Year = yearSelected;
            }

            Common.data_OrderBy = String.valueOf(Constants.defaultOrderBy);
            if(orderBySelected != null){
                Common.data_OrderBy = orderBySelected;
            }

            HttpClient.getData_CountriesGDP(getApplicationContext(), Common.data_Year);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshTitleTextView(String text){
        try {
            if(CountryGDPActivity.titleTextView != null){
                CountryGDPActivity.titleTextView.setText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onListFragmentInteraction(CountryGDP item) {

    }

}
