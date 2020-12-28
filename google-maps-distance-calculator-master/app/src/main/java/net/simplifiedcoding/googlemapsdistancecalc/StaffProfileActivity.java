package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

public class StaffProfileActivity extends BaseActivityStaff {
    String username;
    String myJSON;

    private static final String TAG_RESULTS="result2";
    private static final String TAG_NAME1 = "name";
    private static final String TAG_PHONE1 = "phone1";
    private static final String TAG_PHONE2 ="phone2";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_XCOOR ="xcoor";
    private static final String TAG_YCOOR ="ycoor";
    private static final String TAG_USERNAME ="username";
    private static final String TAG_EMAIL ="email";

    String name ,phone1,phone2,address,xcoor,ycoor,user,email;
    JSONArray peoples = null;

    private TextView TextName;
    private TextView TextPhone1;
    private TextView TextPhone2;
    private TextView TextAddress;
    private TextView TextXCOOR;
    private TextView TextYCOOR;
    private TextView TextUsername;
    private TextView TextEmail;

    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/staffdetail.php";
    String urlSuffix = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_staff_profile, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(StaffProfileActivity.this);
        username = settings.getString("name", "defaultName");
        Toast.makeText(this,username,Toast.LENGTH_LONG).show();



        TextName = (TextView) findViewById(R.id.tvNamecompany);
        TextPhone1 = (TextView) findViewById(R.id.tvNumbercompany1);
        TextPhone2= (TextView) findViewById(R.id.tvNumbercompany2);
        TextAddress = (TextView) findViewById(R.id.tvAddresscompany);
        TextXCOOR= (TextView) findViewById(R.id.tvLatitude);
        TextYCOOR= (TextView) findViewById(R.id.tvlogititude);
        TextUsername = (TextView) findViewById(R.id.tvUsername);
        TextEmail = (TextView) findViewById(R.id.tvEmailcompany);
        GetStaffDetail();

    }


    private void GetStaffDetail() {
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
                myJSON=s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                ShowStaffDetail();

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







    protected void ShowStaffDetail(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                name = c.getString(TAG_NAME1);
                phone1=c.getString(TAG_PHONE1);
                phone2=c.getString(TAG_PHONE2);
                address=c.getString(TAG_ADDRESS);
                xcoor=c.getString(TAG_XCOOR);
                ycoor=c.getString(TAG_YCOOR);
                user=c.getString(TAG_USERNAME);
                email=c.getString(TAG_EMAIL);


            }
            TextName.setText(name);
            TextPhone1.setText(phone1);
            TextPhone2.setText(phone2);
            TextAddress.setText(address);
            TextXCOOR.setText(xcoor);
            TextYCOOR.setText(ycoor);
            TextUsername.setText(user);
            TextEmail.setText(email);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
