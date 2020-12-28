package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainDonorActivity extends BaseActivityDonor {

    private static final String REGISTER_URL = "https://donationbloodapp.000webhostapp.com/insertcoor.php";
    String urlSuffix;
    private String lng;
    private String lat;
    String username;
    //private static final String TAG_RESULTS = "result";
    //String urlSuffix4;
    //String myJSON4;
    //private static final String REGISTER_URL4 = "https://donationbloodapp.000webhostapp.com/getdate.php";
    //String lastDonationDate;
   // private static final String TAG_DATE ="lastdonation";

    //Date datee;
    JSONArray peoples = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main_donor, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainDonorActivity.this);
        username = settings.getString("name", "defaultName");

//        Intent intent = getIntent();
//        username = intent.getStringExtra(MainActivity.USER_NAME);
        // String userid = intent.getStringExtra(MainActivity.USER_ID);

        //  TextView textView = (TextView) findViewById(R.id.textView3);

        //  textView.setText("Welcome " + username);

        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {

            lat = Double.toString(gps.getLatitude());
            lng = Double.toString(gps.getLongitude());
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lng, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        //   Toast.makeText(this,location.toString(),Toast.LENGTH_LONG).show();
//
//        if (location != null) {
//            //Getting longitude and latitude
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//
//        }
            //  LatLng latlng = new LatLng(latitude,longitude);
//
//     sendToServer(latlng);

        }
        SendCoordinateToServer();
       // getDate();
       // NotfiyMe();

    }


    private void SendCoordinateToServer() {
        urlSuffix = "?lat=" + lat + "&lng=" + lng + "&username=" + username;
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
                //  myJSON2=s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                //  showSpinnerDistrict();

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL + s);
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


//    public void notifyMe() {
//        Notification notification = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String day = sdf.format(date);
//        Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
//        Calendar c = Calendar.getInstance();
//        try {
//            c.setTime(sdf.parse(day));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        c.add(Calendar.MONTH, 6);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        String output = sdf1.format(c.getTime());
//        Toast.makeText(this,output,Toast.LENGTH_LONG).show();
////        String kawl = ""
////        String imam = "";
////        String[] imams;
////        int index = -1;
////        Random r = new Random();
//
//        if (day.equals(output)) {
//            notification = new Notification.Builder(this).setContentTitle("YOU CAN DONATE ").setContentText("after 6 month you can donate again").setSmallIcon(R.drawable.ic_email_black).build();
//        }
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0, notification);
//    }


//    private void getDate() {
//        urlSuffix4 = "?username=" + username;
//        class RegisterUser extends AsyncTask<String, Void, String> {
//
//            //  ProgressDialog loading;
//
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                // loading = ProgressDialog.show(register.this, "Please Wait", null, true, true);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                myJSON4 = s;
//
//                //  loading.dismiss();
//                //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
//
//                NotfiyMe();
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                String s = params[0];
//                BufferedReader bufferedReader = null;
//                try {
//                    URL url = new URL(REGISTER_URL4 + s);
//                    // Toast.makeText(getApplicationContext(),urlSuffix, Toast.LENGTH_LONG).show();
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//                    String result;
//
//                    result = bufferedReader.readLine();
//
//                    return result;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//        }
//
//        RegisterUser ru = new RegisterUser();
//        ru.execute(urlSuffix4);
//    }
//
//
//    protected void NotfiyMe() {
//        try {
//            JSONObject jsonObj = new JSONObject(myJSON4);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for (int i = 0; i < peoples.length(); i++) {
//                JSONObject c = peoples.getJSONObject(i);
//                lastDonationDate  = c.getString(TAG_DATE);
//                Notification notification = null;
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = new Date();
//                String day = sdf.format(date);
//                Toast.makeText(this, day, Toast.LENGTH_SHORT).show();
//
//
////                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////                try {
////                    datee = dateFormat.parse(lastDonationDate);
////                } catch (ParseException e) {
////                    e.printStackTrace();
////                }
////                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
////               // Date dateee=dateFormat.format(datee);
////                Toast.makeText(this,dateFormat.format(datee),Toast.LENGTH_LONG).show();
////
//
//
//
//
//                Calendar ce = Calendar.getInstance();
//        try {
//            ce.setTime(sdf.parse(String.valueOf(lastDonationDate)));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        ce.add(Calendar.MONTH, 6);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        String output = sdf1.format(ce.getTime());
//                Toast.makeText(this,lastDonationDate,Toast.LENGTH_LONG).show();
//        Toast.makeText(this,output,Toast.LENGTH_LONG).show();
//
//
//
////
////
////                if (day.equals(output)) {
////            notification = new Notification.Builder(this).setContentTitle("YOU CAN DONATE ").setContentText("after 6 month you can donate again").setSmallIcon(R.drawable.ic_email_black).build();
////        }
////                else{
////
////        }
////
////
////        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
////        notificationManager.notify(0, notification);
////
////
//       }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
