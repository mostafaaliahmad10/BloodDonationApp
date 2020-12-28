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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class CodeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSend;
    private EditText editCode,editPass;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/changepassword.php";
    String urlSuffix = null;
    String myJSON;
    String username,code,usercode,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(CodeActivity.this);
        username = settings.getString("name", "defaultName");
        code = settings.getString("code", "defaultName");
        Toast.makeText(this, username+ " "+ code, Toast.LENGTH_SHORT).show();


        buttonSend = (Button) findViewById(R.id.send_email);

        editCode = (EditText) findViewById(R.id.editTextCode);
        editPass = (EditText) findViewById(R.id.editTextPass);
        buttonSend.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        usercode = editCode.getText().toString();
        if(code.equals(usercode)){
            password = editPass.getText().toString();
            ChangePassword();
           Intent intent = new Intent(CodeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this,"invalid code", Toast.LENGTH_SHORT).show();
        }


    }


    private void ChangePassword() {
        urlSuffix ="?username=" + username +"&password="+password;
        class RegisterUser extends AsyncTask<String, Void, String> {

           //  ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
              //   loading = ProgressDialog.show(CodeActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                myJSON=s;

                // loading.dismiss();
                Toast.makeText(getApplicationContext(), myJSON, Toast.LENGTH_LONG).show();


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
