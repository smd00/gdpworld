package au.com.melbourneapps.gdpworld.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import au.com.melbourneapps.gdpworld.models.CountryGDP;

public class Common {

//    private String TAG = getClass().getSimpleName();

    public static ArrayList<CountryGDP> countryGDPArrayList = null;

    public static String data_OrderBy = "";
    public static String data_Year = "";

    public static String readStream(InputStream stream) {
        try {
            if (stream == null) {
                return "";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String newLine;
            do {
                newLine = reader.readLine();
                if (newLine != null) {
                    builder.append(newLine).append('\n');
                }
            } while (newLine != null);
            if (builder.length() > 0) {
                builder.setLength(builder.length() - 1);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static JSONArray getJsonArrayFromString(String inputStr){
        JSONArray jsonArray = null;
        try {
            if(inputStr != null){
                jsonArray = new JSONArray(inputStr);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public static ArrayList<CountryGDP> getCountryGDPsFromJSONArray(JSONArray jsonArray){
        ArrayList<CountryGDP> countryGDPArrayList = null;
        try {
            countryGDPArrayList = new ArrayList<>();
            for(int i=0; i < jsonArray.length(); i++) {
                if(i == 0){
                    //todo: can be deleted, not needed
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String page = jsonObject.getString("page");
                        String pages = jsonObject.getString("pages");
                        String per_page = jsonObject.getString("per_page");
                        String lastupdated = jsonObject.getString("lastupdated");
                        String total = jsonObject.getString("total");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    JSONArray indicatorsJSONArray = jsonArray.getJSONArray(i);

                    for(int j=0; j < indicatorsJSONArray.length(); j++) {
                        JSONObject jsonObject = indicatorsJSONArray.getJSONObject(j); // CountryGDP
                        CountryGDP countryGDP = new CountryGDP();
                        countryGDP = new Gson().fromJson(String.valueOf(jsonObject), CountryGDP.class);
                        countryGDPArrayList.add(countryGDP);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countryGDPArrayList;
    }

    public static double round(double value, int places) {
        try {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return value;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            } else if(Build.VERSION.SDK_INT >= 21){
                Network[] info = cm.getAllNetworks();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i] != null && cm.getNetworkInfo(info[i]).isConnected()) {
                            return true;
                        }
                    }
                }
            } else {
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
                final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}