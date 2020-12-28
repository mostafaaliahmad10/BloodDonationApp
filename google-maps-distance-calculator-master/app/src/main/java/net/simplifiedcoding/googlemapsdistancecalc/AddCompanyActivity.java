package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

public class AddCompanyActivity extends BaseActivity  implements View.OnClickListener{
    private EditText editTextName;
    private EditText editTextPhone1;
    private EditText editTextPhone2;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private EditText editTextXcoor;
    private EditText editTextYcoor;
    private EditText editTextPassword;
    private EditText editTextUsername;

    private Button buttonRegister;

    private static final String REGISTER_URL = "https://donationbloodapp.000webhostapp.com/addcompany.php";
    String urlSuffix = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_add_company);


        getLayoutInflater().inflate(R.layout.activity_add_company, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);





        editTextName = (EditText) findViewById(R.id.companyname);
        editTextPhone1 = (EditText) findViewById(R.id.companyphone1);
        editTextPhone2 = (EditText) findViewById(R.id.companyphone2);
        editTextEmail = (EditText) findViewById(R.id.companyemail);
        editTextAddress = (EditText) findViewById(R.id.compantaddress);
        editTextXcoor=(EditText) findViewById(R.id.companyxcoor);
        editTextYcoor=(EditText) findViewById(R.id.companyycoor);
        editTextPassword = (EditText) findViewById(R.id.password_register);
        editTextUsername = (EditText) findViewById(R.id.username);

        buttonRegister = (Button) findViewById(R.id.register_button);
        buttonRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();

        }
    }



    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        String phone1 = editTextPhone1.getText().toString().trim().toLowerCase();
        String phone2 = editTextPhone2.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String address = editTextAddress.getText().toString().trim().toLowerCase();
        String xcoor = editTextXcoor.getText().toString().trim().toLowerCase();
        String ycoor = editTextYcoor.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();


        register(name,phone1,phone2,email,address,xcoor,ycoor,username,password);
    }




    private void register(String name, String phone1, String phone2, String email, String address, String xcoor,String ycoor,String username,String password) {
        urlSuffix = "?name="+name+"&phone1="+phone1+"&phone2="+phone2+"&email="+email+"&address="+address+"&xcoor="+xcoor+"&ycoor="+ycoor+"&username="+username
                +"&password="+password;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddCompanyActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                finish();
                Intent in = new Intent(AddCompanyActivity.this,MainAdminActivity.class);
                startActivity(in);

            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(REGISTER_URL + s);
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
