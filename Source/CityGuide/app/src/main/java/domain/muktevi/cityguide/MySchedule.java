package domain.muktevi.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import domain.muktevi.cityguide.beans.Schedule;

public class MySchedule extends AppCompatActivity {

    public static String USER = "domain.muktevi.cityguide.USER";
    public static ListView listView;
    public static Schedule[] scheduleArray;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        USER = intent.getStringExtra(Home.USER);
        String urlEncodedName = URLEncoder.encode(USER);
        String requestUrl = "https://api.mlab.com/api/1/databases/ase_assignment/collections/schedule?q=%7B%22user%22%3A%22" + urlEncodedName + "%22%7D&apiKey=T4RmCJ4GWaqs1nRLHnFoA--K8wrdzly4";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(requestUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Failure form server", e.getMessage());
            }

            public void onResponse(Response response) throws IOException {
                final String respJson = response.body().string();
                Log.i("Json String: ", respJson);
                if (respJson.isEmpty()) {
                    Log.e("response json is empty", respJson);
                }
                try {

                    Log.i("++++++++", "++++++++");
                    JSONArray responseJson = new JSONArray(respJson);
                    scheduleArray = new Schedule[responseJson.length()];
                    for (int i = 0; i < responseJson.length(); i++) {
                        Schedule schedule = new Schedule();
                        JSONObject obj = (JSONObject) responseJson.get(i);
                        schedule.setUser((String) obj.get("user"));
                        schedule.setVenueName((String) obj.get("venuename"));
                        scheduleArray[i] = schedule;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateSchedule(scheduleArray);
                        }
                    });

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }


        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void populateSchedule(Schedule[] scheduleArray) {
        final ArrayAdapter adapter = new ScheduleAdapter(MySchedule.this,scheduleArray);
        listView = (ListView) findViewById(R.id.theListView);
        listView.setAdapter(adapter);
    }
}
