package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

public class ShowDonorActivity extends BaseActivity {

    private ListView list;
    ArrayAdapter<String> adapter;
    EditText inputSearch;
    String myJSON;
    String name [];
    String DonorName;


    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    public static final String USER_NAME="";

    JSONArray peoples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_show_donor);

    //    super.onCreate(savedInstanceState);
        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_show_donor, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);











        getData();
        list = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);



        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                ShowDonorActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DonorName = list.getItemAtPosition(i).toString().trim();
                Intent intent = new Intent(ShowDonorActivity.this, ProfileDonorActivity.class);
                intent.putExtra(USER_NAME,DonorName);
                startActivity(intent);

            }
        });

    }



    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/showdonor.php");

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

                // Toast.makeText(getApplicationContext(), myJSON, Toast.LENGTH_LONG).show();
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
            adapter = new ArrayAdapter<String>(this, R.layout.list_item,R.id.name,name);
            list.setAdapter(adapter);



        }
    }


}
