package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdminActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_main_admin, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

      //  ((ImageView)findViewById(R.id.image_view)).setBackgroundResource(R.drawable.image1);




Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.USER_NAME);

       // TextView textView = (TextView) findViewById(R.id.textView3);

       // textView.setText("Welcome " + username);



    }





}
