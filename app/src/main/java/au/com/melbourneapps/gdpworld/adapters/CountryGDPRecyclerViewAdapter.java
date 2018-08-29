package au.com.melbourneapps.gdpworld.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import au.com.melbourneapps.gdpworld.R;
import au.com.melbourneapps.gdpworld.fragments.CountryGDPFragment.OnListFragmentInteractionListener;
import au.com.melbourneapps.gdpworld.models.CountryGDP;
import au.com.melbourneapps.gdpworld.utilities.Common;

public class CountryGDPRecyclerViewAdapter extends RecyclerView.Adapter<CountryGDPRecyclerViewAdapter.ViewHolder> {

    private final List<CountryGDP> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CountryGDPRecyclerViewAdapter(List<CountryGDP> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_countrygdp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            if(mValues.get(position) == null) return;

            CountryGDP countryGDP = mValues.get(position);

            double value = 0;
            if(countryGDP.value != null){
                value = Common.round(countryGDP.value/1000000, 2); // display value in USD millions
            }

            holder.mItem = countryGDP;
            holder.countryTextView.setText(countryGDP.country.getValue());
            holder.countryValueTextView.setText(String.format(Locale.getDefault(), "%.2f", value));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            if (mValues == null) return 0;
            return mValues.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView countryTextView;
        public final TextView countryValueTextView;
        public CountryGDP mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            countryTextView = view.findViewById(R.id.countryTextView);
            countryValueTextView = view.findViewById(R.id.countryValueTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + countryValueTextView.getText() + "'";
        }
    }
}
