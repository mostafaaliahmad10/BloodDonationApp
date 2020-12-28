package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class UpdateCompanyActivity extends AppCompatActivity  implements View.OnClickListener{

    String companyName;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/selectcompanyinfo.php";
    private static final String UPDATE_URL = "https://donationbloodapp.000webhostapp.com/updatecompany.php";
    String urlSuffix = null;
    String myJSON2;
    private static final String TAG_RESULTS="result";
    JSONArray peoples = null;

    String name ,phone1,phone2,address,xcoor,ycoor,email,username,password;



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
        setContentView(R.layout.activity_update_company);

        editTextName = (EditText) findViewById(R.id.companyname);
        editTextPhone1 = (EditText) findViewById(R.id.companyphone1);
        editTextPhone2 = (EditText) findViewById(R.id.companyphone2);
        editTextEmail = (EditText) findViewById(R.id.companyemail);
        editTextAddress = (EditText) findViewById(R.id.compantaddress);
        editTextXcoor=(EditText) findViewById(R.id.companyxcoor);
        editTextYcoor=(EditText) findViewById(R.id.companyycoor);
        editTextPassword = (EditText) findViewById(R.id.password_register);
       // editTextUsername = (EditText) findViewById(R.id.username);

        buttonUpdate = (Button) findViewById(R.id.update_button);
        buttonUpdate.setOnClickListener(this);

        Intent intent = getIntent();
        companyName = intent.getStringExtra(ShowCompanyAvtivity.USER_NAME);
      //  Toast.makeText(getApplicationContext(), companyName, Toast.LENGTH_LONG).show();

        SendCompanyName();
    }


    @Override
    public void onClick(View v) {
        if (v == buttonUpdate) {
            UpdateCompany();

        }
    }






    private void SendCompanyName() {
        urlSuffix = "?companyName=" + companyName;
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
              //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                showCompanyInfo();
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


    protected void showCompanyInfo(){
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








    private void UpdateCompany() {
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
                +"&password="+password+"&companyName="+companyName;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateCompanyActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent I=new Intent(UpdateCompanyActivity.this,MainAdminActivity.class);
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
