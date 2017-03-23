package com.azhaguvel.dam.chitfund;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aninterface.root.dam.DueRepayment_Activity;
import com.aninterface.root.dam.R;
import com.aninterface.root.dam.Repayment_Actvity;
import com.aninterface.root.dam.jsonparser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Chitfund_Repayment_Activity extends AppCompatActivity {
    EditText user_name,amount,unqueid;
    Button rapysubmit;
    private static String url_create_product = "http://unitate.in/dam/api/insertdue.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "sucess";
    private ProgressDialog pDialog;

    jsonparser jsonParser = new jsonparser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitfund__repayment_);
        Bundle bundle = getIntent().getExtras();
        //Extract the data… Cusuniqid
        String uniqids = bundle.getString("Uniq_Id");
        String User_Name = bundle.getString("User_Name");

        Log.d(uniqids,"this next screen");
        Log.d(User_Name,"this next screen1");
        user_name=(EditText)findViewById(R.id.repay_username);
        user_name.setText(User_Name);
        amount=(EditText)findViewById(R.id.repay_amount);
        unqueid=(EditText)findViewById(R.id.repay_uniqid);
        unqueid.setText(uniqids);
        rapysubmit=(Button)findViewById(R.id.repay_submit);
        rapysubmit.setOnClickListener(new View.OnClickListener() {
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
            pDialog = new ProgressDialog(Chitfund_Repayment_Activity.this);
            pDialog.setMessage("Please Wait Repayment..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String Repay_username = user_name.getText().toString();
            String Repay_amount = amount.getText().toString();
            String Repay_cid = unqueid.getText().toString();


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
                    Intent i = new Intent(getApplicationContext(), HomeChitfund_Activity.class);
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
