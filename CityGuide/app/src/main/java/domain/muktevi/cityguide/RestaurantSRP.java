package domain.muktevi.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantSRP extends AppCompatActivity {

    public static String clientId = "P2IIU4YKUJ2ILBZMYLTDO42TUKACIOTJ5QVEYUK3F2ZAW0K3";
    public static String clientSecret = "3A3ZTVHPSK4VU2T2YJLY0SSD43IH1Z32JAQNAEV4DWMLOSPZ";
    public static String url = "https://api.foursquare.com/v2/venues/search?";
    public static String imageUrl = "https://foursquare.com/img/categories/food/default_64.png";
    public static String msg = "You have opted : ";
    public static String[] venuList = null;
    public static ListView listView;
    public static JSONObject json = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_srp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String foodType = intent.getStringExtra(Main.FOOD);

        /*ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(imageUrl).into(imageView);*/

       /* String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final List osList = new ArrayList<String>();
        for (int i=0;i<values.length;i++){
            osList.add(values[i]);
        }*/


      /*  TextView foodTextView = (TextView) findViewById(R.id.textView_foodItem);
        foodTextView.setText(foodType);*/


        String requestUrl = url+"near=Kansas City&"+"query="+foodType+"&"+"limit=5&"+"client_id="+clientId+"&"+"client_secret="+clientSecret+"&v=20160212";
        OkHttpClient client = new OkHttpClient();

       // Date date = new Date();
        Request request = new Request.Builder().url(requestUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Failure form server", e.getMessage());
            }

            public void onResponse(Response response) throws IOException {
                final String respJson = response.body().string();
                Log.i("Json String: ", respJson);
                try {
                    json = new JSONObject(respJson);
                    JSONArray venues = null;
                    venues = json.getJSONObject("response").getJSONArray("venues");
                    venuList = new String[venues.length()];
                    for (int i = 0; i < venues.length(); i++) {
                        venuList[i] = venues.getJSONObject(i).getString("name");
                    }
                    Log.i("venu array: ", String.valueOf(venuList));

                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            populateListView(venuList);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void populateListView(String[] venuList) {
        final ArrayAdapter adapter = new MyAdapter(RestaurantSRP.this, venuList);
        listView = (ListView) findViewById(R.id.theListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String optedMessage = msg + String.valueOf(adapterView.getItemAtPosition(position));
                Intent intent = new Intent(RestaurantSRP.this, RestaurantDetails.class);
                intent.putExtra(msg, optedMessage);
                startActivity(intent);
            }
        });
    }
}
