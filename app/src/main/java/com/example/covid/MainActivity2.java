package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {
    private String TAG = MainActivity2.class.getSimpleName();

    private ProgressDialog pDialog;

    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://data.covid19.go.id/public/api/prov.json";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity2.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray provinsis = jsonObj.getJSONArray("list_data");

                    // looping through All Contacts
                    for (int i = 0; i < provinsis.length(); i++) {
                        JSONObject c = provinsis.getJSONObject(i);

                        String key = c.getString("key");
                        String jumka = c.getString("jumlah_kasus");
                        String jumse = c.getString("jumlah_sembuh");
                        String jumme = c.getString("jumlah_meninggal");
                        String jumdi = c.getString("jumlah_dirawat");

                        // tmp hash map for single contact
                        HashMap<String, String> data = new HashMap<>();

                        // adding each child node to HashMap key => value
                        data.put("key", key);
                        data.put("jumlah_kasus", jumka);
                        data.put("jumlah_sembuh", jumse);
                        data.put("jumlah_meninggal", jumme);
                        data.put("jumlah_dirawat", jumdi);

                        // adding contact to contact list
                        contactList.add(data);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity2.this, contactList,
                    R.layout.list_item, new String[]{"key"}, new int[]{R.id.key});

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String jumka = contactList.get(position).get("jumlah_kasus");
                    String jumse = contactList.get(position).get("jumlah_sembuh");
                    String jumme = contactList.get(position).get("jumlah_meninggal");
                    String jumdi = contactList.get(position).get("jumlah_dirawat");
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("jumkaa", jumka);
                    intent.putExtra("jumsee", jumse);
                    intent.putExtra("jummee", jumme);
                    intent.putExtra("jumdii", jumdi);
                    startActivity(intent);
                }
            });
        }

    }
}