package com.aninterface.root.dam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.azhaguvel.dam.chitfund.HomeChitfund_Activity;

public class Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button New_cusotmer,customer_list,todayreport,overallreport,cus_profit,repayment,Fifty,Twoone,Twotwo,Overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        New_cusotmer=(Button)findViewById(R.id.new_customer) ;
        customer_list=(Button)findViewById(R.id.customer_list) ;
        todayreport=(Button)findViewById(R.id.today_report) ;
        overallreport=(Button)findViewById(R.id.overall_report) ;
        cus_profit=(Button)findViewById(R.id.profit) ;
        repayment=(Button)findViewById(R.id.reapayment) ;
//        Fifty=(Button)findViewById(R.id.fiftyk);
//        Twoone=(Button)findViewById(R.id.onek);
//        Twotwo=(Button)findViewById(R.id.onekk);
        Overview=(Button)findViewById(R.id.chit_fund);




        New_cusotmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newcustomer=new Intent(Home_Activity.this, New_Customer_Activity.class);
                startActivity(newcustomer);
            }
            });
        customer_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newcustomer=new Intent(Home_Activity.this, CustomerList_Activity.class);
                startActivity(newcustomer);
            }
        });
        todayreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayresport=new Intent(Home_Activity.this, Today_Report_Activity.class);
                startActivity(todayresport);
            }
        });
        overallreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayresport=new Intent(Home_Activity.this, Overall_Report_Activity.class);
                startActivity(todayresport);
            }
        });
        cus_profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayresport=new Intent(Home_Activity.this, Profit_Activity.class);
                startActivity(todayresport);
            }
        });
        repayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayresport=new Intent(Home_Activity.this, Repayment_Actvity.class);
                startActivity(todayresport);
            }
        });
//        Fifty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent todayresport=new Intent(Home_Activity.this, Fiftykentry_Activity.class);
//                startActivity(todayresport);
//            }
//        });
//        Twoone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent todayresport=new Intent(Home_Activity.this, Twohunderedkone.class);
//                startActivity(todayresport);
//            }
//        });
//        Twotwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent todayresport=new Intent(Home_Activity.this, twohundredktwo.class);
//                startActivity(todayresport);
//            }
//        });
        Overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayresport=new Intent(Home_Activity.this, HomeChitfund_Activity.class);
                startActivity(todayresport);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
