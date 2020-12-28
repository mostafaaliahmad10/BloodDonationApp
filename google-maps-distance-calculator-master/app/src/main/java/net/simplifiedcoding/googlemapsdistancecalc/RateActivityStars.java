package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RateActivityStars extends AppCompatActivity{
    String name;
    double rate1;
    RatingBar ratingbar1;
    Button button;
    private static final String TAG_RATE = "rate";
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/rate.php";
    private static final String REGISTER_URL3 = "https://donationbloodapp.000webhostapp.com/updaterate.php";
    String urlSuffix = null;
    String urlSuffix2 = null;
    private static final String TAG_RESULTS="result";
    String myJSON;
    String myJSON2;
    JSONArray peoples = null;
    String rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_activity_stars);



        Intent intent = getIntent();
        name = intent.getStringExtra(RateActivity.USER_NAME);


        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
        addListenerOnButtonClick();


        GetRate();
    }

    public void addListenerOnButtonClick(){
        ratingbar1=(RatingBar)findViewById(R.id.ratingBar1);
        button=(Button)findViewById(R.id.rate_button);
        //Performing action on Button Click
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                rate = String.valueOf(ratingbar1.getRating());
                Toast.makeText(getApplicationContext(), rate, Toast.LENGTH_LONG).show();
                UpdateRate();

            }

        });
    }



    private void GetRate() {
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
                ShowRate();

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







    protected void ShowRate(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                rate1 = c.getDouble(TAG_RATE);

                ratingbar1.setRating((float) rate1);


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    private void UpdateRate() {
        urlSuffix2 = "?name=" + name +"&rate="+rate;
        class RegisterUser extends AsyncTask<String, Void, String> {

             ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                 loading = ProgressDialog.show(RateActivityStars.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                myJSON2=s;

                 loading.dismiss();
                Intent in = new Intent(RateActivityStars.this,RateActivity.class);
                startActivity(in);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

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
        ru.execute(urlSuffix2);
    }


}
