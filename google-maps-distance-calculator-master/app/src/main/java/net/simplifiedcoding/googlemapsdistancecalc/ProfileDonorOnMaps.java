package net.simplifiedcoding.googlemapsdistancecalc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class ProfileDonorOnMaps extends AppCompatActivity implements View.OnClickListener {

    Button btCall;
    String myJSON;

    double rate1;
    RatingBar ratingbar1;
    Button button;
    private static final String TAG_RATE = "rate";
//    private static final String REGISTER_URL3 = "https://donationbloodapp.000webhostapp.com/rate.php";
//
//    String urlSuffix3 = null;
//    private static final String TAG_RESULTS2 = "result";
//    String myJSON3;


    private static final String TAG_RESULTS = "result";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE1 = "phone1";
    private static final String TAG_PHONE2 = "phone2";
    private static final String TAG_BLOOD = "bloodname";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_DISTRICT = "districtname";
    private static final String TAG_DATE = "lastdonation";
    String namedonor, phone1, phone2, bloodname, email, districtname, lastdonation;
    JSONArray peoples = null;

    private TextView TextName;
    private TextView TextPhone1;
    private TextView TextPhone2;
    private TextView TextBlood;
    private TextView TextEmail;
    private TextView TextDistrict;
    private TextView TextDate;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/showdonordetail.php";
    String urlSuffix = null;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_donor_on_maps);


        Intent intent = getIntent();
        name = intent.getStringExtra(MapsActivity.USER_NAME);


        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        TextName = (TextView) findViewById(R.id.tvName);
        TextPhone1 = (TextView) findViewById(R.id.tvNumber1);
        TextPhone2 = (TextView) findViewById(R.id.tvNumber2);
        TextBlood = (TextView) findViewById(R.id.tvblood);
        TextEmail = (TextView) findViewById(R.id.tvemail);
        TextDistrict = (TextView) findViewById(R.id.tvDistrict);
        TextDate = (TextView) findViewById(R.id.tvdate);
        ratingbar1=(RatingBar)findViewById(R.id.tvrate);
        getDonorDetail();

        btCall = (Button) findViewById(R.id.Calldonor);
        btCall.setOnClickListener(this);

       // GetRate();
    }

    @Override
    public void onClick(View v) {
        if (v == btCall) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone1));

            if (ActivityCompat.checkSelfPermission(ProfileDonorOnMaps.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
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


    private void getDonorDetail() {
        urlSuffix = "?name=" + name;
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
                myJSON = s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                showdonordetail();

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


    protected void showdonordetail() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                namedonor = c.getString(TAG_NAME);
                phone1 = c.getString(TAG_PHONE1);
                phone2 = c.getString(TAG_PHONE2);
                bloodname = c.getString(TAG_BLOOD);
                email = c.getString(TAG_EMAIL);
                districtname = c.getString(TAG_DISTRICT);
                lastdonation = c.getString(TAG_DATE);
                rate1 = c.getDouble(TAG_RATE);


            }
            TextName.setText(namedonor);
            TextPhone1.setText(phone1);
            TextPhone2.setText(phone2);
            TextBlood.setText(bloodname);
            TextEmail.setText(email);
            TextDistrict.setText(districtname);
            TextDate.setText(lastdonation);
            ratingbar1.setRating((float) rate1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


//    private void GetRate() {
//        urlSuffix3 = "?name=" + name;
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
//                myJSON3 = s;
//
//                //  loading.dismiss();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
//                ShowRate();
//
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                String s = params[0];
//                BufferedReader bufferedReader = null;
//                try {
//                    URL url = new URL(REGISTER_URL3 + s);
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
//        ru.execute(urlSuffix3);
//    }
//
//
//    protected void ShowRate() {
//        try {
//            JSONObject jsonObj = new JSONObject(myJSON3);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS2);
//
//            for (int i = 0; i < peoples.length(); i++) {
//                JSONObject c = peoples.getJSONObject(i);
//                // String id = c.getString(TAG_ID);
//
//                rate1 = c.getDouble(TAG_RATE);
//
//                ratingbar1.setRating((float) rate1);
//
//
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}

