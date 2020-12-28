package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateStaffActivity extends BaseActivityStaff implements View.OnClickListener {

    String username;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/selectstaffinfo.php";
    private static final String UPDATE_URL = "https://donationbloodapp.000webhostapp.com/updatestaffinfo.php";
    String urlSuffix = null;
    String myJSON2;
    private static final String TAG_RESULTS="result";
    JSONArray peoples = null;

    String name ,phone1,phone2,address,xcoor,ycoor,email,password;



    private static final String TAG_NAME = "name";
    private static final String TAG_PHONE1 = "phone1";
    private static final String TAG_PHONE2 ="phone2";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_XCOOR ="xcoor";
    private static final String TAG_YCOOR = "ycoor";
    private static final String TAG_EMAIL ="email";
    // private static final String TAG_USERNAME = "username";
    private static final String TAG_PASS ="password";

    private EditText editTextName;
    private EditText editTextPhone1;
    private EditText editTextPhone2;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private EditText editTextXcoor;
    private EditText editTextYcoor;
    private EditText editTextPassword;
    // private EditText editTextUsername;

    private Button buttonUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_update_staff, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(UpdateStaffActivity.this);
        username = settings.getString("name", "defaultName");
        Toast.makeText(this, username, Toast.LENGTH_LONG).show();




        editTextName = (EditText) findViewById(R.id.tvNamecompany);
        editTextPhone1 = (EditText) findViewById(R.id.tvNumbercompany1);
        editTextPhone2 = (EditText) findViewById(R.id.tvNumbercompany2);
        editTextEmail = (EditText) findViewById(R.id.tvEmailcompany);
        editTextAddress = (EditText) findViewById(R.id.tvAddresscompany);
        editTextXcoor=(EditText) findViewById(R.id.tvLatitude);
        editTextYcoor=(EditText) findViewById(R.id.tvlogititude);
        editTextPassword = (EditText) findViewById(R.id.tvPassword);
        // editTextUsername = (EditText) findViewById(R.id.username);

        buttonUpdate = (Button) findViewById(R.id.update_button_staff);
        buttonUpdate.setOnClickListener(this);
        GetStaffInfo();

    }


    @Override
    public void onClick(View v) {
        if (v == buttonUpdate) {
            UpdateStaffinfo();

        }
    }










    private void GetStaffInfo() {
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
                myJSON2=s;

                //  loading.dismiss();
                  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                showStaffInfo();
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


    protected void showStaffInfo(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON2);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                name = c.getString(TAG_NAME);
                phone1=c.getString(TAG_PHONE1);
                phone2=c.getString(TAG_PHONE2);
                address=c.getString(TAG_ADDRESS);
                xcoor=c.getString(TAG_XCOOR);
                ycoor=c.getString(TAG_YCOOR);
                email=c.getString(TAG_EMAIL);
                // username=c.getString(TAG_USERNAME);
                password = c.getString(TAG_PASS);

            }

            editTextName.setText(name);
            editTextPhone1.setText(phone1);
            editTextPhone2.setText(phone2);
            editTextEmail.setText(email);
            editTextAddress.setText(address);
            editTextXcoor.setText(xcoor);
            editTextYcoor.setText(ycoor);
            editTextPassword.setText(password);
            // editTextUsername.setText(username);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    private void UpdateStaffinfo() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String phone1 = editTextPhone1.getText().toString().trim().toLowerCase();
        String phone2 = editTextPhone2.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String address = editTextAddress.getText().toString().trim().toLowerCase();
        String xcoor = editTextXcoor.getText().toString().trim().toLowerCase();
        String ycoor = editTextYcoor.getText().toString().trim().toLowerCase();
        // String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();


        register(name, phone1, phone2, email, address, xcoor, ycoor, password);
    }




    private void register(String name, String phone1, String phone2, String email, String address, String xcoor,String ycoor,String password) {
        urlSuffix = "?name="+name+"&phone1="+phone1+"&phone2="+phone2+"&email="+email+"&address="+address+"&xcoor="+xcoor+"&ycoor="+ycoor
                +"&password="+password+"&username="+username;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateStaffActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent I=new Intent(UpdateStaffActivity.this,MainStaffActivity.class);
                startActivity(I);
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(UPDATE_URL + s);
                    //Toast.makeText(getApplicationContext(),urlSuffix, Toast.LENGTH_LONG).show();
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





}
