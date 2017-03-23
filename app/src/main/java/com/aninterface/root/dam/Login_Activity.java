package com.aninterface.root.dam;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Login_Activity extends AppCompatActivity {
    EditText username,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        username=(EditText)findViewById(R.id.user_name);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.log_in);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://unitate.in/dam/api/login.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", username.getText().toString()));
                params.add(new BasicNameValuePair("password", password.getText().toString()));

                /** Get result from Server (Return the JSON Code)
                 * StatusID = ? [0=Failed,1=Complete]
                 * MemberID = ? [Eg : 1]
                 * Error	= ?	[On case error return custom error message]
                 *
                 * Eg Login Failed = {"StatusID":"0","MemberID":"0","Error":"Incorrect Username and Password"}
                 * Eg Login Complete = {"StatusID":"1","MemberID":"2","Error":""}
                 */

                String resultServer  = getHttpPost(url,params);

                /*** Default Value ***/
                String strStatusID = "1";

                String strError = "Unknow Status!";

                JSONObject c;
                try {
                    c = new JSONObject(resultServer);
                    strStatusID = c.getString("statusid");
                    strError = c.getString("error");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



                // Prepare Login
                if(strStatusID.equals("0"))
                {
                    // Dialog
                    ad.setTitle("Error! ");
                    ad.setIcon(android.R.drawable.btn_star_big_on);
                    ad.setPositiveButton("Close", null);
                    ad.setMessage(strError);
                    ad.show();
                    username.setText("");
                    password.setText("");
                }
                else
                {
                    Toast.makeText(Login_Activity.this, "Login OK", Toast.LENGTH_SHORT).show();
                    Intent newActivity = new Intent(Login_Activity.this,Home_Activity.class);
                    startActivity(newActivity);
                }

            }
        });

    }


    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}
