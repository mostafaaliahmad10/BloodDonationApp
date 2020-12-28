package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Random;

public class ForgotPasswordEmailActivity extends AppCompatActivity  implements View.OnClickListener  {

    //Declaring EditText
    private EditText editEmail;
    //Send button
    private Button buttonSend;
    String code;
    JSONArray peoples = null;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/forgotpasswordgetemail.php";
    String urlSuffix = null;
    String myJSON;
    private static final String TAG_RESULTS="result";
    private static final String TAG_EMAIL ="email";
    private static final String TAG_USERNAME ="username";
    String email;
    String useremail,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_email);

        buttonSend = (Button) findViewById(R.id.send_email);

        editEmail = (EditText) findViewById(R.id.editTextEmail);

        buttonSend.setOnClickListener(this);


}
    private void sendEmail() {
        //Getting content for email
        // email = editTextEmail.getText().toString().trim();


        //Creating SendMail object
        SendMail sm = new SendMail(this, email, "change password code", code);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        email = editEmail.getText().toString();
        Random random = new Random();
        code  = String.format("%04d", random.nextInt(10000));
      //  Toast.makeText(this,code,Toast.LENGTH_LONG).show();
        GetEmail();


    }


    private void GetEmail() {
        urlSuffix ="?email=" + email;
        class RegisterUser extends AsyncTask<String, Void, String> {

              ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                 loading = ProgressDialog.show(ForgotPasswordEmailActivity.this, "Please Wait",  "Loading...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                myJSON=s;

                  loading.dismiss();
                  Toast.makeText(getApplicationContext(), myJSON, Toast.LENGTH_LONG).show();
                ShowEmail();

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







    protected void ShowEmail(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                useremail = c.getString(TAG_EMAIL);
                username=c.getString(TAG_USERNAME);

            }

    sendEmail();

    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ForgotPasswordEmailActivity.this);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString("name", username);
    editor.putString("code", code);
    editor.commit();
    Intent  intent = new Intent(ForgotPasswordEmailActivity.this, CodeActivity.class);
    startActivity(intent);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
