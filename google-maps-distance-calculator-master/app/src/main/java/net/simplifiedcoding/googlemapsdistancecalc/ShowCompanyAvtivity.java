package net.simplifiedcoding.googlemapsdistancecalc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RadioButton;
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

public class ShowCompanyAvtivity extends BaseActivity {
    ListView listview;

   Button bt1,bt2;
    Context context;
    private static final String REGISTER_URL2 = "https://donationbloodapp.000webhostapp.com/deletecompany.php";
    String urlSuffix = null;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    public static final String USER_NAME="";
   Intent intent;
    JSONArray peoples = null;
    String companyName;
    String name [];
    String myJSON;
    String myJSON2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_show_company_avtivity);


        getLayoutInflater().inflate(R.layout.activity_show_company_avtivity, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);










        context = this;
        listview = (ListView)findViewById(R.id.listView1);
        //string array

        // set adapter for listview
        getData();
         bt1=(Button)findViewById(R.id.btdelete);
        bt2=(Button)findViewById(R.id.btupdate);

listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        companyName=listview.getItemAtPosition(i).toString();

      //  Toast.makeText(ShowCompanyAvtivity.this,companyName, Toast.LENGTH_SHORT).show();

      bt1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            //  Toast.makeText(ShowCompanyAvtivity.this, "good", Toast.LENGTH_SHORT).show();
              DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      switch (which){
                          case DialogInterface.BUTTON_POSITIVE:
                              //Do your Yes progress
                              SendCompanyName();
                              Intent refresh = new Intent(ShowCompanyAvtivity.this, ShowCompanyAvtivity.class);
                              startActivity(refresh);
                              break;

                          case DialogInterface.BUTTON_NEGATIVE:
                              //Do your No progress
                              dialog.dismiss();
                              break;
                      }
                  }
              };
              AlertDialog.Builder ab = new AlertDialog.Builder(ShowCompanyAvtivity.this);
              ab.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                      .setNegativeButton("No", dialogClickListener).show();







          }
      });






        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ShowCompanyAvtivity.this, "good", Toast.LENGTH_SHORT).show();
                intent = new Intent(ShowCompanyAvtivity.this, UpdateCompanyActivity.class);
                intent.putExtra(USER_NAME, companyName);
                //  intent.putExtra(USER_ID, userId);
                startActivity(intent);
            }
        });


    }
});
    }

//    public class CheckBoxClick implements AdapterView.OnItemClickListener {
//
//        @Override
//        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
//            // TODO Auto-generated method stub
//           // CheckedTextView ctv = (CheckedTextView)arg1;
//           // RadioButton ctv=(RadioButton)arg1;
//
//            if(ctv.isChecked()){
//
//                bt1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Toast.makeText(ShowCompanyAvtivity.this, "delete", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//                bt2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(ShowCompanyAvtivity.this, "update", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }else{
//                Toast.makeText(ShowCompanyAvtivity.this, "now it is checked", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/showcompany.php");

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
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;

                // Toast.makeText(getApplicationContext(),myJSON, Toast.LENGTH_LONG).show();
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }





    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            name=new String[peoples.length()];

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);

                name[i] = c.getString(TAG_NAME);

                //  Toast.makeText(getApplicationContext(),name[i], Toast.LENGTH_LONG).show();

            }
            printName();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void printName(){
        for (int i=0;i<name.length;i++){
          //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.checkbox, name);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,name);
            listview.setAdapter(adapter);
//            listview.setItemsCanFocus(false);
//            // we want multiple clicks
//            listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
         //   listview.setOnItemClickListener(new CheckBoxClick());



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
                  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();


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
