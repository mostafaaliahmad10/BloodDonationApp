package net.simplifiedcoding.googlemapsdistancecalc;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {
    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    protected String[] listArray = { "Home", "Add Company", "Show company", "Show Staff", "Show Donor","Logout" };
    protected static int position;
    private static boolean isLaunch = true;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.list_itemmm, R.id.name, listArray));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
//        getActionBar().setDisplayHomeAsUpEnabled(true);
  //      getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,      /* host Activity */
                mDrawerLayout,     /* DrawerLayout object */
               // R.mipmap.ic_launcher,     /* nav drawer image to replace 'Up' caret */
                R.string.title1,       /* "open drawer" description for accessibility */
                R.string.titl)      /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {
              //  getActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            //    getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        /**
         * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
         * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
         * */
        if(isLaunch){
            /**
             *Setting this flag false so that next time it will not open our first activity.
             *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
             *In this case this base activity will always be call when any child activity will launch.
             */
            isLaunch = false;
            openActivity(0);
        }
    }

    /**
     * @param position
     *
     * Launching activity when any list item is clicked.
     */
    protected void openActivity(int position) {

        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */
//  mDrawerList.setItemChecked(position, true);
//  setTitle(listArray[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, MainAdminActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, AddCompanyActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ShowCompanyAvtivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ShowStaffActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, ShowDonorActivity.class));
                break;
            case 5:
                Intent objsignOut = new Intent(getBaseContext(),MainActivity.class);
                objsignOut.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(objsignOut);
                break;

            default:
                break;
        }

       // Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        // The action bar home/up action should open or close the drawer.
//        // ActionBarDrawerToggle will take care of this.
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
//    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        }else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }
}