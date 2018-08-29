package au.com.melbourneapps.gdpworld.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import au.com.melbourneapps.gdpworld.R;
import au.com.melbourneapps.gdpworld.adapters.CountryGDPRecyclerViewAdapter;
import au.com.melbourneapps.gdpworld.models.CountryGDP;
import au.com.melbourneapps.gdpworld.utilities.Common;

public class CountryGDPFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    public static OnListFragmentInteractionListener mListener;

    public static CountryGDPRecyclerViewAdapter countryGDPRecyclerViewAdapter;
    public static RecyclerView recyclerView;

    public CountryGDPFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CountryGDPFragment newInstance(int columnCount) {
        CountryGDPFragment fragment = new CountryGDPFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countrygdp_list, container, false);

        try {
            // Set the adapter
            if (view instanceof RecyclerView) {
                Context context = view.getContext();
                recyclerView = (RecyclerView) view;
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));

                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                countryGDPRecyclerViewAdapter = new CountryGDPRecyclerViewAdapter(Common.countryGDPArrayList, mListener);
                recyclerView.setAdapter(countryGDPRecyclerViewAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public static void refreshCountryGDPRecyclerViewAdapterData(){
        try {
            countryGDPRecyclerViewAdapter = new CountryGDPRecyclerViewAdapter(Common.countryGDPArrayList, mListener);
            if(recyclerView != null) recyclerView.setAdapter(countryGDPRecyclerViewAdapter);
//            countryGDPRecyclerViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(CountryGDP item);
    }
}
