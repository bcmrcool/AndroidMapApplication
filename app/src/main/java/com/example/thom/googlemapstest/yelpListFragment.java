package com.example.thom.googlemapstest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.LayoutInflater;


import java.util.ArrayList;

/**
 * Created by Thom on 4/20/2015.
 */
public class yelpListFragment extends Fragment{

    View rootView;

    private ArrayList<String> yelpNames;
    private ArrayAdapter<String> yelpNamesAdapter;
    private ListView lvYelpList;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.yelp_list_fragment_layout, container, false);
        return rootView;
    }
}
