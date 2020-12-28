package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class register extends AppCompatActivity  implements View.OnClickListener{

    String myJSON;
    String myJSON2;
    private static final String TAG_RESULTS_BLOOD = "result";
    private static final String TAG_NAME_BLOOD = "bloodname";
    private static final String TAG_ID_BLOOD = "bloodtypeId";

    private static final String TAG_RESULTS_GOVERNORATE = "result2";
    private static final String TAG_NAME_GOVERNORATE = "name";
    private static final String TAG_ID_GOVERNORATE = "govId";
    private static String bloodID = "";
    private static String governorateID = "";
    String districtID = "";


    private static final String TAG_RESULTS_DISTRICT = "result3";
    private static final String TAG_NAME_DISTRICT = "districtname";
    private static final String TAG_ID_DISTRICT = "districtId";

    JSONArray peoples = null;
    JSONArray peoples2 = null;
    // ArrayList<HashMap<String, String>> personList;
    final ArrayList<SpinnerClass> bloodList = new ArrayList<SpinnerClass>();
    final ArrayList<String> bloodListAttributes = new ArrayList<String>();

    final ArrayList<SpinnerClass> governorateList = new ArrayList<SpinnerClass>();
    final ArrayList<String> governorateListAttributes = new ArrayList<String>();

    final ArrayList<SpinnerClass> districtList = new ArrayList<SpinnerClass>();
    final ArrayList<String> districtListAttributes = new ArrayList<String>();


    private EditText editTextName;
    private EditText editTextPhone1;
    private EditText editTextPhone2;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private Spinner spinnerBlood;
    private Spinner spinnerGovernorate;
    private Spinner spinnerDistrict;

    private Button buttonRegister;

    private static final String REGISTER_URL = "https://donationbloodapp.000webhostapp.com/register.php";
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/spinnerdistrict.php";
    String urlSuffix = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        editTextName = (EditText) findViewById(R.id.fullname_register);
        editTextPhone1 = (EditText) findViewById(R.id.phone1);
        editTextPhone2 = (EditText) findViewById(R.id.phone2);
        editTextPassword = (EditText) findViewById(R.id.password_register);
        editTextEmail = (EditText) findViewById(R.id.email_register);
        editTextUsername = (EditText) findViewById(R.id.username);
        spinnerBlood = (Spinner) findViewById(R.id.spinner);
        spinnerGovernorate = (Spinner) findViewById(R.id.spinner2);
        spinnerDistrict = (Spinner) findViewById(R.id.spinner3);

        buttonRegister = (Button) findViewById(R.id.register_button);

        buttonRegister.setOnClickListener(this);
        getDataBlood();
        getDataGovernorate();


        spinnerBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodID = bloodList.get(i).getId();
                // Toast.makeText(getApplicationContext(), bloodList.get(i).getId(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtList.clear();
                governorateID = governorateList.get(i).getId();
                //Toast.makeText(getApplicationContext(), governorateList.get(i).getId(), Toast.LENGTH_LONG).show();
                getDataDistrict();
                districtListAttributes.clear();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtID = districtList.get(i).getId();
                // Toast.makeText(getApplicationContext(),districtID, Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }




    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            String email = editTextEmail.getText().toString();
            if (email.contains("@"))
                registerUser();
            else
                Toast.makeText(getApplicationContext(), "email must contain @", Toast.LENGTH_LONG).show();

        }
    }


    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String phone1 = editTextPhone1.getText().toString().trim().toLowerCase();
        String phone2 = editTextPhone2.getText().toString().trim().toLowerCase();


        register(name, username, password, phone1, phone2, email);
    }

    private void register(String name, String username, String password, String phone1, String phone2, String email) {
        urlSuffix = "?name=" + name + "&username=" + username + "&password=" + password + "&phone1=" + phone1 + "&phone2=" + phone2 + "&email=" + email + "&bloodID=" + bloodID +
                "&districtID="+districtID;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(register.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                finish();
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


    protected void showSpinnerBlood() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS_BLOOD);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String name = c.getString(TAG_NAME_BLOOD);


                // HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                // persons.put(TAG_NAME,name);
                bloodListAttributes.add(name);
                SpinnerClass sp = new SpinnerClass();
                sp.setId(c.getString(TAG_ID_BLOOD));
                sp.setAttribute(c.getString(TAG_NAME_BLOOD));
                bloodList.add(sp);
            }
            ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodListAttributes);
            spinnerBlood.setAdapter(l);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getDataBlood() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/spinnerblood.php");


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
                showSpinnerBlood();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


    protected void showSpinnerGovernorate() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS_GOVERNORATE);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String name = c.getString(TAG_NAME_GOVERNORATE);


                // HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                // persons.put(TAG_NAME,name);
                governorateListAttributes.add(name);
                SpinnerClass sp = new SpinnerClass();
                sp.setId(c.getString(TAG_ID_GOVERNORATE));
                sp.setAttribute(c.getString(TAG_NAME_GOVERNORATE));
                governorateList.add(sp);
            }
            ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, governorateListAttributes);
            spinnerGovernorate.setAdapter(l);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getDataGovernorate() {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/spinnergovernorate.php");


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
                showSpinnerGovernorate();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

//
//    public void getDataDistrict() {
//        class GetDataJSON extends AsyncTask<String, Void, String> {
//
//            @Override
//            protected String doInBackground(String... params) {
//                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
//                HttpPost httppost = new HttpPost("http://192.168.1.64:81/Bloodapp/spinnerdistrict.php");
//
//
//                // Depends on your web service
//                httppost.setHeader("Content-type", "application/json");
//
//                InputStream inputStream = null;
//                String result = null;
//                try {
//
//                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//                    nameValuePairs.add(new BasicNameValuePair("governorateID",governorateID ));
//                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                    httpclient.execute(httppost);
//
//
//                    HttpResponse response = httpclient.execute(httppost);
//                    HttpEntity entity = response.getEntity();
//
//                    inputStream = entity.getContent();
//                    // json is UTF-8 by default
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
//                    StringBuilder sb = new StringBuilder();
//
//                    String line = null;
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line + "\n");
//                    }
//                    result = sb.toString();
//                } catch (Exception e) {
//                    // Oops
//                } finally {
//                    try {
//                        if (inputStream != null) inputStream.close();
//                    } catch (Exception squish) {
//                    }
//                }
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                myJSON = result;
//                showSpinnerDistrict();
//            }
//        }
//        GetDataJSON g = new GetDataJSON();
//        g.execute();
//
//    }


    protected void showSpinnerDistrict() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON2);
            peoples2 = jsonObj.getJSONArray(TAG_RESULTS_DISTRICT);

            for (int i = 0; i < peoples2.length(); i++) {
                JSONObject c = peoples2.getJSONObject(i);

                String name = c.getString(TAG_NAME_DISTRICT);


                // HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                // persons.put(TAG_NAME,name);
                districtListAttributes.add(name);
                SpinnerClass sp = new SpinnerClass();
                sp.setId(c.getString(TAG_ID_DISTRICT));
                sp.setAttribute(c.getString(TAG_NAME_DISTRICT));
                districtList.add(sp);
            }
            ArrayAdapter<String> l = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districtListAttributes);
            spinnerDistrict.setAdapter(l);





        } catch (JSONException e) {
            e.printStackTrace();
        }

    }










    private void getDataDistrict() {
        urlSuffix = "?governorateID=" + governorateID;
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
                showSpinnerDistrict();

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

}
