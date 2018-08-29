package au.com.melbourneapps.gdpworld.utilities;

import java.util.Calendar;

public class Constants {
      // API
    private static final int year = Calendar.getInstance().get(Calendar.YEAR);
    public static final int lastYear = year - 1;
    public static String api_Format = "json";
    public static String api_PerPage = "500";

    public static String GetApiGDPUrl(String format, String per_page, String date){
        String api_Base_GDP_Url = "https://api.worldbank.org/v2/countries/all/indicators/NY.GDP.MKTP.CD";
        return String.format(api_Base_GDP_Url + "?format=%s&per_page=%s&date=%s", format, per_page, date);
    }

    // Data
    public static final String defaultOrderBy = "Name";
}

