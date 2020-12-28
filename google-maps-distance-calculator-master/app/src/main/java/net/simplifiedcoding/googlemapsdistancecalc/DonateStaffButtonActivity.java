package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DonateStaffButtonActivity extends AppCompatActivity {

    String name;
    private TextView switchStatus;
    private Switch mySwitch;
    String urlSuffix2;
    String myJSON2;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/updatestatezero.php";

    String urlSuffix3;
    String myJSON3;
    private static final String REGISTER_URL3 = "https://donationbloodapp.000webhostapp.com/updatestateone.php";

    private static final String TAG_STATUS ="status";
    private static final String TAG_RESULTS="result";
    JSONArray peoples = null;
    String urlSuffix4;
    String myJSON4;
    private static final String REGISTER_URL4 = "https://donationbloodapp.000webhostapp.com/selectstate.php";
    String status;
    String lastDonationDate;

    String urlSuffix5;
    String myJSON5;
    private static final String REGISTER_URL5 = "https://donationbloodapp.000webhostapp.com/getlastdonation.php";


    String urlSuffix6;
    String myJSON6;
    private static final String REGISTER_URL6 = "https://donationbloodapp.000webhostapp.com/updateseconddate.php";


    String seconddonationdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_staff_button);


        Intent intent = getIntent();
        name = intent.getStringExtra(DonateStaffActivity.USER_NAME);


        Toast.makeText(this, name, Toast.LENGTH_LONG).show();



        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        //  mySwitch.setChecked(true);


        //set the switch to ON
        //  mySwitch.setChecked(true);
        //attach a listener to check for changes in state
//
//        Boolean b = AppTypeDetails.getInstance(MainDonorActivity.this).getToggleStatus();
//        mySwitch.setChecked(b);
        getstate();


        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    // switchStatus.setText("Switch is currently ON");
                    UpdateButtonStateToOne();
//                    SharedPreferences.Editor editor = getSharedPreferences("com.example.xyz", MODE_PRIVATE).edit();
//                    editor.putBoolean("NameOfThingToSave", true);
//                    editor.commit();
                    // AppTypeDetails.getInstance(MainDonorActivity.this).setToggleStatus(true);
                }else {
                    // switchStatus.setText("Switch is currently OFF");
                    UpdateButtonStateToZero();
                    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    lastDonationDate = format.format(new Date());




                    Calendar ce = Calendar.getInstance();
                    try {
                        ce.setTime(format.parse(String.valueOf(lastDonationDate)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ce.add(Calendar.MONTH, 6);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    seconddonationdate = sdf1.format(ce.getTime());
                    Toast.makeText(DonateStaffButtonActivity.this,lastDonationDate,Toast.LENGTH_LONG).show();
                    Toast.makeText(DonateStaffButtonActivity.this,seconddonationdate,Toast.LENGTH_LONG).show();

                    // lastDonationDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    SendDate();
                    UpdateSecondDate();

//                    SharedPreferences.Editor editor = getSharedPreferences("com.example.xyz", MODE_PRIVATE).edit();
//                    editor.putBoolean("NameOfThingToSave", false);
//                    editor.commit();
                    // AppTypeDetails.getInstance(MainDonorActivity.this).setToggleStatus(false);
                 //   Toast.makeText(DonateStaffButtonActivity.this, lastDonationDate, Toast.LENGTH_LONG).show();
                }

            }
        });

//        //check the current state before we display the screen
//        if(mySwitch.isChecked()){
//            switchStatus.setText("Switch is currently ON");
//        }
//        else {
//            switchStatus.setText("Switch is currently OFF");
//        }
//
    }




    private void UpdateButtonStateToZero() {
        urlSuffix2 = "?name=" + name;
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
                Toast.makeText(getApplicationContext(), myJSON2, Toast.LENGTH_LONG).show();


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
        ru.execute(urlSuffix2);
    }








    private void UpdateButtonStateToOne() {
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





    private void getstate() {
        urlSuffix4 = "?name=" + name;
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
                myJSON4=s;

                //  loading.dismiss();
                //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                showstate();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL4 + s);
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
        ru.execute(urlSuffix4);
    }







    protected void showstate(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON4);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);

                status = c.getString(TAG_STATUS);


            }
            if(status.equals("1")) {
                //mySwitch.setChecked(true);
                // Toast.makeText(this,status,Toast.LENGTH_LONG).show();
                mySwitch.setChecked(true);
            }
            else
            {
                //  mySwitch.setChecked(false);
                // Toast.makeText(this,status,Toast.LENGTH_LONG).show();
                mySwitch.setChecked(false);




            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }






    private void SendDate() {
        urlSuffix5 = "?name=" + name+"&lastDonationDate="+lastDonationDate;
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
                myJSON5=s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), myJSON5, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL5 + s);
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
        ru.execute(urlSuffix5);
    }







    private void UpdateSecondDate() {
        urlSuffix6 = "?name=" + name+"&seconddonationdate="+seconddonationdate;
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
                myJSON6=s;

                //  loading.dismiss();
                Toast.makeText(getApplicationContext(), myJSON6, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL6 + s);
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
        ru.execute(urlSuffix6);
    }


}
