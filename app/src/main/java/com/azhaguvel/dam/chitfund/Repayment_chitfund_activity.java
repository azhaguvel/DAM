package com.azhaguvel.dam.chitfund;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aninterface.root.dam.DueRepayment_Activity;
import com.aninterface.root.dam.R;
import com.aninterface.root.dam.Repayment_Actvity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Repayment_chitfund_activity extends AppCompatActivity {

    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    ArrayList<HashMap<String, Object>> MyArrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment__actvity);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //download josn values
        new DownloadJSONFileAsync().execute();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_JSON_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading.....");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    // Show All Content
    public void ShowAllContent()
    {
        // listView1
        final ListView lstView1 = (ListView)findViewById(R.id.listView1);
        lstView1.setAdapter(new ImageAdapter(Repayment_chitfund_activity.this,MyArrList));

    }



    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        private ArrayList<HashMap<String, Object>> MyArr = new ArrayList<HashMap<String, Object>>();

        public ImageAdapter(Context c, ArrayList<HashMap<String, Object>> myArrList)
        {
            // TODO Auto-generated method stub
            context = c;
            MyArr = myArrList;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArr.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.repa, null);
            }


            // username
            final TextView txtImgID = (TextView) convertView.findViewById(R.id.customer_name);
            txtImgID.setText("" + MyArr.get(position).get("name").toString());

            // address
            TextView txtPicName = (TextView) convertView.findViewById(R.id.payment);
            txtPicName.setText(" " + MyArr.get(position).get("email").toString());

            final    TextView Uniqid = (TextView) convertView.findViewById(R.id.uniqid);
            Uniqid.setText(" " + MyArr.get(position).get("cuniqueid").toString());
            Button repaument =(Button)convertView.findViewById(R.id.repay_add);

            repaument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Uniq_Id = Uniqid.getText().toString();
                    Log.d(Uniq_Id,"this is uniq id value here");
                    String User_Name = txtImgID.getText().toString();
                    Log.d(User_Name,"this is username value here");
                    Intent todayresport=new Intent(Repayment_chitfund_activity.this, DueRepayment_Activity.class);
                    //Create the bundle
                    Bundle bundle = new Bundle();
                    //Add your data to bundle
                    bundle.putString("Uniq_Id", Uniq_Id);
                    bundle.putString("User_Name", User_Name);
                    //Add the bundle to the intent
                    todayresport.putExtras(bundle);
                    startActivity(todayresport);
                }
            });

            return convertView;

        }

    }



    // Download JSON in Background
    public class DownloadJSONFileAsync extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub

            String url = "http://unitate.in/dam/api/getcustomerdetail.php";

            JSONArray data;
            try {
                data = new JSONArray(getJSONUrl(url));

                MyArrList = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> map;

                for(int i = 0; i < data.length(); i++){
                    JSONObject c = data.getJSONObject(i);
                    map = new HashMap<String, Object>();
                    map.put("name", (String)c.getString("name"));
                    map.put("email", (String)c.getString("email"));
                    map.put("cuniqueid", (String)c.getString("cuniqueid"));
                    MyArrList.add(map);
                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            ShowAllContent(); // When Finish Show Content
            dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }


    }


    /*** Get JSON Code from URL ***/
    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download file..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


    /***** Get Image Resource from URL (Start) *****/
    private static final String TAG = "Image";
    private static final int IO_BUFFER_SIZE = 4 * 1024;
    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                android.util.Log.e(TAG, "Could not close stream", e);
            }
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }
    /***** Get Image Resource from URL (End) *****/

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }

}
