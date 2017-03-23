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
import android.widget.Spinner;

import com.aninterface.root.dam.Home_Activity;
import com.aninterface.root.dam.R;
import com.aninterface.root.dam.jsonparser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fiftykentry_Activity extends AppCompatActivity {
    EditText username,mobile,depositamount,dueamount,address;
    Spinner spinnervalue;
    Button submit;
    private static String url_create_product = "http://unitate.in/dam/api/insertcustomer.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "sucess";
    private ProgressDialog pDialog;
    jsonparser jsonParser = new jsonparser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiftykentry_);

        username=(EditText)findViewById(R.id.new_username);
        mobile=(EditText)findViewById(R.id.new_mobile);
        depositamount=(EditText)findViewById(R.id.new_depositamt);
        dueamount=(EditText)findViewById(R.id.new_dueamt);
        address=(EditText)findViewById(R.id.new_address);
        submit=(Button)findViewById(R.id.new_submit);
        spinnervalue=(Spinner)findViewById(R.id.spinner_value);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateNewCustomer().execute();
            }
        });
    }

    class CreateNewCustomer extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Fiftykentry_Activity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String Username = username.getText().toString();
            String Mobile = mobile.getText().toString();
            String Depsoitamount = depositamount.getText().toString();
            String Dueamount = dueamount.getText().toString();
            String Address = address.getText().toString();
            String Sp=spinnervalue.getSelectedItem().toString();
            Log.d(String.valueOf(spinnervalue),"this is spinner value");

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", Username));
            params.add(new BasicNameValuePair("mobile", Mobile));
            params.add(new BasicNameValuePair("email", Address));
            params.add(new BasicNameValuePair("depositamount", Depsoitamount));
            params.add(new BasicNameValuePair("dueamount", Dueamount));
            params.add(new BasicNameValuePair("view", Sp));


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
