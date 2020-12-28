package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainStaffActivity extends BaseActivityStaff {

   // public static final String USER_NAME="mypref";
    String username;
    String myJSON;
    JSONArray peoples = null;
    private static final String TAG_RESULTS = "result";
    String[] name;
    String[] date;
    private static final String TAG_NAME = "name";
    private static final String TAG_DATE = "seconddonationdate";
    String currentdate;
    String name1;


    String urlSuffix3;
    String myJSON3;
    private static final String REGISTER_URL3 = "https://donationbloodapp.000webhostapp.com/updatestateone.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_main_staff, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainStaffActivity.this);
       username = settings.getString("name", "defaultName");



//
//        Intent intent = getIntent();
//         username = intent.getStringExtra(MainActivity.USER_NAME);

        TextView textView = (TextView) findViewById(R.id.textView3);

        textView.setText("Welcome " + username);

        getDate();
    }





    public void getDate() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/selectalldate.php");

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
              //  Toast.makeText(getApplicationContext(), myJSON, Toast.LENGTH_LONG).show();

                GetDateAndName();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }





    protected void GetDateAndName() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            name = new String[peoples.length()];
           date= new String[peoples.length()];
            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);
                name[i] = c.getString(TAG_NAME);
                date[i] = c.getString(TAG_DATE);


                // HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                //  persons.put(TAG_NAME,name);
                //  persons.put(TAG_XCOOR,xcoor);
                //  persons.put(TAG_YCOOR,ycoor);

                // personList.add(persons);
            }

            CompreDateWithCurrentDate();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void CompreDateWithCurrentDate() {

        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        currentdate = format.format(new Date());
        for (int i = 0; i < date.length; i++) {
           name1=name[i].toString();
           if(currentdate.equals(date[i])){
               UpdateButtonStateToOne(name1);
           }

        }
    }



    private void UpdateButtonStateToOne(String name) {
        urlSuffix3 = "?name=" + name;
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
                myJSON3=s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), myJSON3, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL3 + s);
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
        ru.execute(urlSuffix3);
    }


}
