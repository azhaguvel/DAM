package com.aninterface.root.dam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DueRepayment_Activity extends AppCompatActivity {
    EditText username,dueamount,uniqueid;
    Button Repayment;
    // url to create new product
    private static String url_create_product = "http://unitate.in/dam/api/insertdue.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "sucess";
    private ProgressDialog pDialog;

    jsonparser jsonParser = new jsonparser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_repayment_);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String uniqids = bundle.getString("Uniq_Id");
        String User_Name = bundle.getString("User_Name");
        Log.d(uniqids,"this next screen");
        username=(EditText)findViewById(R.id.user_name);
        username.setText(User_Name);
        dueamount=(EditText)findViewById(R.id.amount);
        uniqueid=(EditText)findViewById(R.id.unid);
        uniqueid.setText(uniqids);
        Repayment=(Button)findViewById(R.id.repayment);
        Repayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Repaymentpay().execute();


            }
        });
    }

    class Repaymentpay extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DueRepayment_Activity.this);
            pDialog.setMessage("Please Wait Repayment..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String Repay_username = username.getText().toString();
            String Repay_amount = dueamount.getText().toString();
            String Repay_cid = uniqueid.getText().toString();


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("payamount", Repay_amount));
            params.add(new BasicNameValuePair("cuniqueid", Repay_cid));



            // getting JSON ObjectJSONParser
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), Repayment_Actvity.class);
                    startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }


}
