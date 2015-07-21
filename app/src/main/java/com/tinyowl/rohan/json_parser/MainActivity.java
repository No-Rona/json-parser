package com.tinyowl.rohan.json_parser;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {


    @Bind(R.id.md5_original)
    EditText stringOriginal;
    @Bind(R.id.json_value)
    TextView jsonLabel;
    private String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @OnClick({R.id.get_md5, R.id.get_date, R.id.get_http_header})
    public void onClick(View view) {

        String json = "";
        URL url;
        ParseJsonTask parseJsonTask = new ParseJsonTask();


            switch(view.getId()) {

                case R.id.get_http_header:
                    //url = new URL("http://headers.jsontest.com/");
                    parseJsonTask.execute("http://headers.jsontest.com/");
                    break;

                case R.id.get_md5:
                    //url = new URL("http://md5.jsontest.com/?text=" + stringOriginal.getText().toString());
                    parseJsonTask.execute("http://md5.jsontest.com/?text=" + stringOriginal.getText().toString());
                    break;

                case R.id.get_date:
                    //url = new URL("http://date.jsontest.com/");
                    parseJsonTask.execute("http://date.jsontest.com/");
                    break;

                default:
                    //url = new URL("http://headers.jsontest.com/");
                    parseJsonTask.execute("http://headers.jsontest.com/");
            }
            /*
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String s;
            while((s = bufferedReader.readLine()) != null) {
                json = json + s;
            }
            */

        //parseJson(json);

    }

    public void parseJson(String json) {

        String json_label_value = "";

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iterator = jsonObject.keys();

            while(iterator.hasNext()) {
                String key = iterator.next();
                String value = jsonObject.getString(key);

                json_label_value = json_label_value + key + " : " + value + "\n";

            }
        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        jsonLabel.setText(json_label_value);
    }

    private class ParseJsonTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String json) {

            String json_label_value = "";

            try {
                JSONObject jsonObject = new JSONObject(json);
                Iterator<String> iterator = jsonObject.keys();

                while(iterator.hasNext()) {
                    String key = iterator.next();
                    String value = jsonObject.getString(key);

                    json_label_value = json_label_value + key + " : " + value + "\n";

                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            jsonLabel.setText(json_label_value);

        }

        @Override
        protected String doInBackground(String... params) {

            String json = "";

                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String s = "";
                    while((s = br.readLine()) != null)
                        json = json + s;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            return json;
        }
    }


}