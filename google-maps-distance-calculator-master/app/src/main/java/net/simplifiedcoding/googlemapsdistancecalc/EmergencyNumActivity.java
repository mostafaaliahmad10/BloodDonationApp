package net.simplifiedcoding.googlemapsdistancecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class EmergencyNumActivity extends AppCompatActivity {

    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_num);

        String number[] = {"American Uni of beirut (01 350000)","Auh Hospital (01 374374)","Al Khier Hospital (06 461444)","Bakhaazi Hospital (01 373327)","Beirut Eye Hospital (01 423111)","Civil Defense (125)","Fire Departement (175)","Jabal lebnan Hospital (05 957000)",
                "Hammoud Hospital (07 721021)","Hotel Dieu Hospital (01 615300)","Lebanese Canadian Hospital (01 511488)","Makassed Hospital (01 636000)","Najde Hospital (07 761945)"
                ,"Red Cross (140)", "Rafic Hariri Hospital (01 830000)","Rizk Hospital (01 324969)"
                ,"St.Charles Hospital (05 953444)" ,"Sacre Coeur Hosptal (05 457112)","Saint George Hospital (01 441000)","Serhal Hospital (04 417910)"};

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearchnum);


        adapter = new ArrayAdapter<String>(this, R.layout.list,R.id.name, number);
        lv.setAdapter(adapter);



        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                EmergencyNumActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

}

