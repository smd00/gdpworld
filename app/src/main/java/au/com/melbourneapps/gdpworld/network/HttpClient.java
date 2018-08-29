package au.com.melbourneapps.gdpworld.network;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import au.com.melbourneapps.gdpworld.utilities.Common;
import au.com.melbourneapps.gdpworld.utilities.Constants;

public class HttpClient {

    String TAG = getClass().getSimpleName();

    public static String getReponseStringFromUrl(String url){
        String result = null;
        InputStream inputStream = null;
        HttpURLConnection connection = null;

        try {
            connection = getConnection(url);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) { // if (response < 200 || response >= 300) {
                throw new IOException("Unexpected HTTP response [" + responseCode + "] at URL: " + url); //IllegalArgumentException
            }

            inputStream = connection.getInputStream();
            if (inputStream != null) {
                result = Common.readStream(inputStream);//, 1000000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close Stream and disconnect HTTP connection.
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    public static HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        try {
            connection.setConnectTimeout(1000 * 30);
            connection.setReadTimeout(1000 * 30);
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void getData_CountriesGDP(Context context, String year){
        try {
            if(Common.isNetworkAvailable(context))
            {
                if (!TextUtils.isDigitsOnly(year)) year = String.valueOf(Constants.lastYear);

                String url = Constants.GetApiGDPUrl(Constants.api_Format, Constants.api_PerPage, year);

                new GetCountriesGDPTask().execute(url);
            }
            else{
                Toast.makeText(context, "No Network Available", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
