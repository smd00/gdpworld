package au.com.melbourneapps.gdpworld.models;

import android.support.annotation.NonNull;

public class CountryGDP implements Comparable {

    private Indicator indicator;
    public Country country;
    private String countryiso3code;
    private String date;
    public Double value;
    private String unit;
    private String obsStatus;
    private Integer decimal;

    public CountryGDP() {
    }

    public CountryGDP(String country, Double value) {
        this.country.setValue(country);
        this.value = value;
    }

    public CountryGDP(Indicator indicator, Country country, String countryiso3code, String date, Double value, String unit, String obsStatus, Integer decimal) {
        super();
        this.indicator = indicator;
        this.country = country;
        this.countryiso3code = countryiso3code;
        this.date = date;
        this.value = value;
        this.unit = unit;
        this.obsStatus = obsStatus;
        this.decimal = decimal;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCountryiso3code() {
        return countryiso3code;
    }

    public void setCountryiso3code(String countryiso3code) {
        this.countryiso3code = countryiso3code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getObsStatus() {
        return obsStatus;
    }

    public void setObsStatus(String obsStatus) {
        this.obsStatus = obsStatus;
    }

    public Integer getDecimal() {
        return decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    @Override
    public String toString() {
        return "[ country=" + countryiso3code + ", name=" + country.getValue() + ", value=" + value + "]";
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Double compareValue = Double.valueOf(0);
        Double myValue = Double.valueOf(0);
        try {
            if(((CountryGDP)o).getValue() != null) {
                compareValue = ((CountryGDP) o).getValue();
            }
            if(this.value != null) {
                myValue = this.value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ASC
        // return (int) (myValue - compareValue);

        // DESC
        return (int) (compareValue - myValue);
    }
}