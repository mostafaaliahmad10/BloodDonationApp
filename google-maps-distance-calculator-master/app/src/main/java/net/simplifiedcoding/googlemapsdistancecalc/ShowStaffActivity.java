package net.simplifiedcoding.googlemapsdistancecalc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.ArrayList;
import java.util.HashMap;

public class ShowStaffActivity extends BaseActivity{

    String myJSON;

    private static final String TAG_RESULTS="result";
    // private static final String TAG_ID = "userId";
    private static final String TAG_NAME_StAFF = "username";
    private static final String TAG_NAME_COMPANY ="name";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_show_staff);



        getLayoutInflater().inflate(R.layout.activity_show_staff, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
















        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();


        getData();
    }


    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                // String id = c.getString(TAG_ID);
                String StaffName = c.getString(TAG_NAME_StAFF);
                String CompanyName = c.getString(TAG_NAME_COMPANY);

                HashMap<String,String> persons = new HashMap<String,String>();

                //persons.put(TAG_ID,id);
                persons.put(TAG_NAME_StAFF,StaffName);
                persons.put(TAG_NAME_COMPANY,CompanyName);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ShowStaffActivity.this, personList, R.layout.list_item2,
                    new String[]{TAG_NAME_StAFF,TAG_NAME_COMPANY},
                    new int[]{R.id.staffname, R.id.companyname}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }












    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://donationbloodapp.000webhostapp.com/showstaff.php");

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


}


