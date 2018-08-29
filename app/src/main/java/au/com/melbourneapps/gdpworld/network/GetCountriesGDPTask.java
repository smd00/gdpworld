package au.com.melbourneapps.gdpworld.network;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.util.Collections;

import au.com.melbourneapps.gdpworld.activities.CountryGDPActivity;
import au.com.melbourneapps.gdpworld.fragments.CountryGDPFragment;
import au.com.melbourneapps.gdpworld.utilities.Common;

public class GetCountriesGDPTask extends AsyncTask<String, Integer, String>{

//    String TAG = getClass().getSimpleName();

    protected void onPreExecute (){
        try {
            super.onPreExecute();

            CountryGDPActivity.refreshTitleTextView("Loading ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String url = "", result = "";

        try {
            if(params.length > 0) url = params[0];
            result = HttpClient.getReponseStringFromUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try{
            JSONArray dataJSONArray = Common.getJsonArrayFromString(result);
            Common.countryGDPArrayList = Common.getCountryGDPsFromJSONArray(dataJSONArray);

            if(Common.countryGDPArrayList != null){
                if (Common.data_OrderBy.equals("GDP")) {
                    Collections.sort(Common.countryGDPArrayList);
                }
                CountryGDPFragment.refreshCountryGDPRecyclerViewAdapterData();
                CountryGDPActivity.refreshTitleTextView(String.format("Data for year %s", Common.data_Year));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(result);
    }
}