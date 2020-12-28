package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener {

    String myJSON;
    String username;
    Intent intent;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_XCOOR = "xcoor";
    private static final String TAG_NAME = "name";
    private static final String TAG_YCOOR = "ycoor";
    private static final String TAG_NAME_BLOOD = "bloodname";
    double[] xcoor;
    double[] ycoor;
    String[] name;
    String []bloodname;
    String bname;
    String name1;
    JSONArray peoples = null;
    public static final String USER_NAME = "";

    private static final String TAG_RESULTS2 = "result2";
    private static final String TAG_XCOOR_COMPANY = "xcoor";
    private static final String TAG_NAME_COMPANY = "name";
    private static final String TAG_YCOOR_COMPANY = "ycoor";
    double[] xcoorcompany;
    double[] ycoorcompany;
    String[] namecompany;
    String name1company;
    JSONArray peoplescompany = null;

    ArrayList<HashMap<String, String>> personList;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/getcompanycoor.php";
    String urlSuffix = null;
    String myJSON2;

    //Our Map
    private GoogleMap mMap;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;

    //From -> the first coordinate from where we need to calculate the distance
    private double fromLongitude;
    private double fromLatitude;

    //To -> the second coordinate to where we need to calculate the distance
    private double toLongitude;
    private double toLatitude;

    //Google ApiClient
    private GoogleApiClient googleApiClient;

    //Our buttons
    private Button buttonSetTo;
    private Button buttonSetFrom;
    private Button buttonCalcDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing googleapi client
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        buttonSetTo = (Button) findViewById(R.id.buttonSetTo);
        buttonSetFrom = (Button) findViewById(R.id.buttonSetFrom);
        buttonCalcDistance = (Button) findViewById(R.id.buttonCalcDistance);

        buttonSetTo.setOnClickListener(this);
        buttonSetFrom.setOnClickListener(this);
        buttonCalcDistance.setOnClickListener(this);


       // getData();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MapsActivity.this);
        username = settings.getString("name", "defaultName");
        Toast.makeText(this, username, Toast.LENGTH_LONG).show();
        //SendStaffName();


    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.simplifiedcoding.googlemapsdistancecalc/http/host/path")
        );
        AppIndex.AppIndexApi.start(googleApiClient, viewAction);
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://net.simplifiedcoding.googlemapsdistancecalc/http/host/path")
        );
        AppIndex.AppIndexApi.end(googleApiClient, viewAction);
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }

    //Function to move the map
    private void moveMap() {
        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);
        //  LatLng latLng1 = new LatLng(33.8938, 35.5018);


        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location").snippet("Current Location"));


//        mMap.addMarker(new MarkerOptions()
//                .position(latLng1) //setting position
//                .draggable(true) //Making the marker draggable
//                .title("ahmad"));//Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyCJVpM7-ayGMraxFRzq4U8Dt1uRNsmiaws");
        return urlString.toString();
    }

    private void getDirection() {
        //Getting the URL
        String url = makeURL(fromLatitude, fromLongitude, toLatitude, toLongitude);

        //Showing a dialog till we get the route
        final ProgressDialog loading = ProgressDialog.show(this, "Getting Route", "Please wait...", false, false);

        //Creating a string request
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        //Calling the method drawPath to draw the path
                        drawPath(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                    }
                });

        //Adding the request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //The parameter is the server response
    public void drawPath(String result) {
        //Getting both the coordinates
        LatLng from = new LatLng(fromLatitude, fromLongitude);
        LatLng to = new LatLng(toLatitude, toLongitude);

        //Calculating the distance in meters
        Double distance = SphericalUtil.computeDistanceBetween(from, to);

        //Displaying the distance
        Toast.makeText(this, String.valueOf(distance + " Meters"), Toast.LENGTH_SHORT).show();


        try {
            //Parsing json
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                            .addAll(list)
                            .width(20)
                            .color(Color.RED)
                            .geodesic(true)
            );


        } catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
        getData();
        SendStaffName();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        // mMap.clear();
        //Adding a new marker to the current pressed position
//        mMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .draggable(true));

        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSetFrom) {
            fromLatitude = latitude;
            fromLongitude = longitude;
            Toast.makeText(this, "From set", Toast.LENGTH_SHORT).show();
        }

        if (v == buttonSetTo) {
            toLatitude = latitude;
            toLongitude = longitude;
            Toast.makeText(this, "To set", Toast.LENGTH_SHORT).show();
        }

        if (v == buttonCalcDistance) {
            getDirection();
        }
    }


    public void getData() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/getcoor.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                    } catch (Exception squish) {
                    }
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                // Toast.makeText(this,myJSON.toString(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), myJSON, Toast.LENGTH_LONG).show();

                showicon();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }





    protected void showicon() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            name = new String[peoples.length()];
            xcoor = new double[peoples.length()];
            ycoor = new double[peoples.length()];
            bloodname=new String[peoples.length()];
            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);
                name[i] = c.getString(TAG_NAME);
                xcoor[i] = c.getDouble(TAG_XCOOR);
                ycoor[i] = c.getDouble(TAG_YCOOR);
                bloodname[i]=c.getString(TAG_NAME_BLOOD);

                // HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                //  persons.put(TAG_NAME,name);
                //  persons.put(TAG_XCOOR,xcoor);
                //  persons.put(TAG_YCOOR,ycoor);

                // personList.add(persons);
            }

            plot();
            set();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void plot() {
        for (int i = 0; i < xcoor.length; i++) {
            name1 = name[i].toString();
            bname=bloodname[i].toString();
            MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.pic))
                    .position(new LatLng(xcoor[i], ycoor[i])).title(name1).snippet(bname);
            // MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));
            mMap.addMarker(marker);


        }
    }


    private void SendStaffName() {
        urlSuffix = "?username=" + username;
        class RegisterUser extends AsyncTask<String, Void, String> {

            //  ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(register.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                myJSON2 = s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                showiconcompany();

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL2 + s);
                    // Toast.makeText(getApplicationContext(),urlSuffix, Toast.LENGTH_LONG).show();
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String result;

                    result = bufferedReader.readLine();

                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(urlSuffix);
    }


    protected void showiconcompany() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON2);
            peoplescompany = jsonObj.getJSONArray(TAG_RESULTS2);
            namecompany = new String[peoplescompany.length()];
            xcoorcompany = new double[peoplescompany.length()];
            ycoorcompany = new double[peoplescompany.length()];

            for (int i = 0; i < peoplescompany.length(); i++) {
                JSONObject c = peoplescompany.getJSONObject(i);
                // String id = c.getString(TAG_ID);
                namecompany[i] = c.getString(TAG_NAME_COMPANY);
                xcoorcompany[i] = c.getDouble(TAG_XCOOR_COMPANY);
                ycoorcompany[i] = c.getDouble(TAG_YCOOR_COMPANY);


                // HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                //  persons.put(TAG_NAME,name);
                //  persons.put(TAG_XCOOR,xcoor);
                //  persons.put(TAG_YCOOR,ycoor);

                // personList.add(persons);
            }

            plotcompany();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void plotcompany() {
        for (int i = 0; i < xcoorcompany.length; i++) {
            name1company = namecompany[i].toString();
            MarkerOptions marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.pic1))
                    .position(new LatLng(xcoorcompany[i], ycoorcompany[i])).title(name1company).snippet("my company");
            // MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));
            mMap.addMarker(marker);


        }
    }

public void set() {

    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

        @Override
        public void onInfoWindowClick(Marker marker) {
            // TODO Auto-generated method stub
           // String m = marker.getId();
            if(marker.getSnippet().equals("my company") || (marker.getSnippet().equals("Current Location"))){
//                String m=marker.getSnippet();
//                Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();

            }
            else {
                String name=marker.getTitle();
                intent = new Intent(MapsActivity.this,ProfileDonorOnMaps.class);
                intent.putExtra(USER_NAME, name);
                //  intent.putExtra(USER_ID, userId);
                startActivity(intent);

            }

          //  Toast.makeText(getApplicationContext(),mm,Toast.LENGTH_LONG).show();

        }
    });
}


}