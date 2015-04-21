package com.example.thom.googlemapstest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.ImageButton;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.maps.GoogleMap;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    DrawerLayout mDrawerLayout;

    private ArrayList<customMarker> customMarkersArray = new ArrayList<customMarker>();
    private HashMap<Marker, customMarker> markersHashMap;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    GoogleMap googleMap;
    Marker marker;

    //Added for custom buttons
    ImageButton meetUpButton;
    ImageButton menuButton;
    ImageButton addButton;

    ImageButton searchBarButton;
    ImageButton addContactButton;

    SearchView searchView;
    boolean hide = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        searchView = (SearchView) findViewById(R.id.addressSearchView);
        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doLocationSearch(query);
        }
        searchView.setVisibility(View.GONE);

        markersHashMap = new HashMap<Marker, customMarker>();


        //customMarkersArray.add(new customMarker("Name", "rating",
        //        "photo", "Blurb", "Category", 0.0, 0.0));

        customMarkersArray.add(new customMarker("The Coffee Spot", "fourandahalf2",
                "cafe", "French cafe that serves specialty drinks",
                "Cafes, Coffee", 0.0, 0.0));

        createMapView();
        //addMarker();
        plotMarkers(customMarkersArray);

        //Added for custom buttons
        addListenerOnMeetUpButton();
        addListenerOnMenuButton();
        addListenerOnAddButton();

        addListenerOnSearchBarButton();
        addListenerOnAddContactButton();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                fragment = new yelpListFragment();
                break;
        }

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

    }

    /**
     * Initialises the mapview
     */
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.mapView)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
                else {
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
                            marker.showInfoWindow();
                            return true;
                        }
                    });
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }
    /**
     * Adds a marker to the map
     */
    private void addMarker() {
        /** Make sure that the map has been initialised **/
        if(null != googleMap)
        {
            marker = googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(0, 0))
                    //.title("Title")
                    //.snippet("some extra business information")
                    //.draggable(true)
            );

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return false;
                }
            });
        }
    }

    private void plotMarkers(ArrayList<customMarker> markers) {
        if (markers.size() > 0) {
            for (customMarker myCustomMarker : markers) {
                MarkerOptions markerOption = new MarkerOptions()
                        .position(new LatLng(myCustomMarker.getLat(), myCustomMarker.getLng()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = googleMap.addMarker(markerOption);
                markersHashMap.put(currentMarker, myCustomMarker);

                googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }

    private int manageMarkerPhoto(String photoName) {
        return R.drawable.cafe;
    }

    private int manageMarkerRating(String ratingName) {
        return R.drawable.fourandahalf2;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);

                //Toast.makeText(MainActivity.this, "Yelp List clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Added for custom button
    public void addListenerOnMeetUpButton() {

        meetUpButton = (ImageButton) findViewById(R.id.meetUpButton);

        meetUpButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(MainActivity.this, "MeetUpButton clicked!", Toast.LENGTH_SHORT).show();

            }

        });

    }

    public void addListenerOnMenuButton() {

        menuButton = (ImageButton) findViewById(R.id.menuButton);

        menuButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                mDrawerLayout.openDrawer(Gravity.LEFT);

            }

        });

    }

    public void addListenerOnAddButton() {

        addButton = (ImageButton) findViewById(R.id.addButton);

        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(searchBarButton.getVisibility() == View.VISIBLE
                        || addContactButton.getVisibility() == View.VISIBLE) {
                    searchBarButton.setVisibility(View.GONE);
                    addContactButton.setVisibility(View.GONE);
                }
                else {
                    searchBarButton.setVisibility(View.VISIBLE);
                    addContactButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

        public void addListenerOnSearchBarButton() {

            searchBarButton = (ImageButton) findViewById(R.id.searchBarButton);

            searchBarButton.setVisibility(View.GONE);

            searchBarButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    hide(arg0);
                }

            });

        }

        public void addListenerOnAddContactButton() {

            addContactButton = (ImageButton) findViewById(R.id.addContactButton);

            addContactButton.setVisibility(View.GONE);

            addContactButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    //Toast.makeText(MainActivity.this, "AddContact clicked!", Toast.LENGTH_SHORT).show();
                    //Intent showContactView = new Intent(this, Contact.class);
                    //startActivity(new Intent(getApplicationContext(), Contact.class));

                    addContactView(arg0);
                }

            });

        }

    public void addContactView(View v) {
        Intent showContactView = new Intent(this, Contact.class);
        startActivity(new Intent(getApplicationContext(), Contact.class));
    }

    public void doLocationSearch(String query) {

    }

    public void hide(View v) {
        if (hide) {
            searchView.setVisibility(View.VISIBLE);
            searchView.setQuery("", false);
            searchView.clearFocus();
            createSearchBarBackground();
            hide = false;
        }
        else {
            searchView.setVisibility(View.GONE);
            hide = true;
        }
    }

    public void createSearchBarBackground() {
        searchView.setQueryHint("Search for an Address");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);

        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.WHITE);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);

            if (searchText != null) {
                searchText.setTextColor(Color.DKGRAY);
                searchText.setHintTextColor(Color.DKGRAY);
            }
        }
    }




    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {


            View v  = getLayoutInflater().inflate(R.layout.infowindow_layout, null);

            customMarker myCustomMarker = markersHashMap.get(marker);

            TextView markerName = (TextView)v.findViewById(R.id.marker_name);

            ImageView markerRating = (ImageView) v.findViewById(R.id.marker_rating);

            ImageView markerPhoto = (ImageView) v.findViewById(R.id.marker_photo);

            TextView markerCategory = (TextView)v.findViewById(R.id.marker_category);

            TextView markerBlurb = (TextView)v.findViewById(R.id.marker_blurb);

            //TextView anotherLabel = (TextView)v.findViewById(R.id.another_label);

            markerRating.setImageResource(manageMarkerRating(myCustomMarker.getRating()));
            markerPhoto.setImageResource(manageMarkerPhoto(myCustomMarker.getPhoto()));

            markerName.setText(myCustomMarker.getName());
            markerBlurb.setText(myCustomMarker.getBlurb());
            markerCategory.setText(myCustomMarker.getCategory());
            //anotherLabel.setText("A custom text");

            return v;
        }
    }





    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}
