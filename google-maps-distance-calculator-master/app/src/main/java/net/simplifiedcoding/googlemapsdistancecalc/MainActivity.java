package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText editTextUserName;
    private EditText editTextPassword;

    public static final String USER_NAME = "Mypref";
    // public static final String USER_ID = "USERID";



    String myJSON;
    private static final String TAG_RESULTS = "result";
    JSONArray peoples = null;
    private static final String TAG_ID_PERMESSION = "permId";
    // private static final String TAG_ID_USER = "userId";


    //String userId;
    String username;
    String password;
    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName = (EditText) findViewById(R.id.email_to_login);
        editTextPassword = (EditText) findViewById(R.id.password_to_login);

        forgot = (TextView)findViewById(R.id.forgot);
    }


    public void forgot(View view) {
        Intent intent = new Intent(MainActivity.this, ForgotPasswordEmailActivity.class);
        startActivity(intent);
    }


    public void register(View view) {
        Intent intent = new Intent(MainActivity.this,
                register.class);
        startActivity(intent);
    }



    public void invokeLogin(View view) {
        username = editTextUserName.getText().toString();
        password = editTextPassword.getText().toString();

        login(username, password);
    }

    private void login(final String username, String password) {

        class LoginAsync extends AsyncTask<String, Void, String> {

            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[1];


                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "https://donationbloodapp.000webhostapp.com/testlogin.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                //  Toast.makeText(getApplicationContext(),myJSON, Toast.LENGTH_LONG).show();

                getPermId();
                loadingDialog.dismiss();

            }
        }

        LoginAsync la = new LoginAsync();
        la.execute(username, password);

    }



    protected void getPermId() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);

                String id = c.getString(TAG_ID_PERMESSION);
                // String userId=c.getString(TAG_ID_USER);


                Intent intent;
                if (id.equals("1")) {
                    //  Toast.makeText(getApplicationContext(),userId, Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, MainAdminActivity.class);
                    intent.putExtra(USER_NAME, username);
                    //  intent.putExtra(USER_ID, userId);
                    startActivity(intent);
                }
                else
                if(id.equals("2")) {

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("name", username);
                    editor.commit();




                    // Toast.makeText(getApplicationContext(),userId, Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, MainStaffActivity.class);
                  //  intent.putExtra(USER_NAME, username);
                    //  intent.putExtra(USER_ID, userId);
                    startActivity(intent);
                }

                else
                if(id.equals("3")){
                    // Toast.makeText(getApplicationContext(),userId, Toast.LENGTH_LONG).show();
//                    intent = new Intent(MainActivity.this, MainDonorActivity.class);
//                    intent.putExtra(USER_NAME, username);
//                    //  intent.putExtra(USER_ID, userId);
//                    startActivity(intent);


                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("name", username);
                    editor.commit();
                    intent = new Intent(MainActivity.this, MainDonorActivity.class);
                    startActivity(intent);
                }


                else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }





            }






        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
