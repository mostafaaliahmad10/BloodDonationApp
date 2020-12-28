package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileDonorActivity extends AppCompatActivity {
    String name;

    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE1 = "phone1";
    private static final String TAG_PHONE2 ="phone2";
    private static final String TAG_BLOOD = "bloodname";
    private static final String TAG_EMAIL ="email";
    private static final String TAG_DISTRICT ="districtname";
    private static final String TAG_DATE ="lastdonation";
    String namedonor ,phone1,phone2,bloodname,email,districtname,lastdonation;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_donor);


        Intent intent = getIntent();
         name = intent.getStringExtra(ShowDonorActivity.USER_NAME);


        Toast.makeText(this,name,Toast.LENGTH_LONG).show();


        TextName = (TextView) findViewById(R.id.tvName);
        TextPhone1 = (TextView) findViewById(R.id.tvNumber1);
        TextPhone2= (TextView) findViewById(R.id.tvNumber2);
        TextBlood = (TextView) findViewById(R.id.tvblood);
        TextEmail = (TextView) findViewById(R.id.tvemail);
        TextDistrict= (TextView) findViewById(R.id.tvDistrict);
        TextDate= (TextView) findViewById(R.id.tvdate);;

        getDonorDetail();
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
                myJSON=s;

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







    protected void showdonordetail(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                namedonor = c.getString(TAG_NAME);
                phone1=c.getString(TAG_PHONE1);
                phone2=c.getString(TAG_PHONE2);
                bloodname=c.getString(TAG_BLOOD);
                email=c.getString(TAG_EMAIL);
                districtname=c.getString(TAG_DISTRICT);
                lastdonation=c.getString(TAG_DATE);

            }
            TextName.setText(namedonor);
            TextPhone1.setText(phone1);
            TextPhone2.setText(phone2);
            TextBlood.setText(bloodname);
            TextEmail.setText(email);
            TextDistrict.setText(districtname);
            TextDate.setText(lastdonation);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
