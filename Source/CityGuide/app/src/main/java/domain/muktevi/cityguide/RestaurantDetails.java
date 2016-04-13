package domain.muktevi.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import domain.muktevi.cityguide.beans.Constants;
import domain.muktevi.cityguide.beans.Schedule;
import domain.muktevi.cityguide.beans.Venue;
import domain.muktevi.cityguide.domain.muktevi.cityguide.photos.beans.PhotoResult;

public class RestaurantDetails extends AppCompatActivity {

    private Gson gson;
    private ImageView imageView;
    private TextView name;
    private TextView address;
    private TextView contact;
    public static String respJson = "";
    public static String picUrl="https://foursquare.com/img/categories/food/default_64.png";
    public static String venueName;
    public static String USER = "domain.muktevi.cityguide.USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
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
        Bundle bundle = intent.getExtras();
        USER = intent.getStringExtra(RestaurantSRP.USER);
        final Venue venue = (Venue) bundle.get(Constants.SERIAL_KEY);
        venueName = venue.getName();
        imageView = (ImageView)findViewById(R.id.imageView_hotel_image);
        name = (TextView) findViewById(R.id.textView_name);
        address = (TextView) findViewById(R.id.textView_address);
        contact = (TextView) findViewById(R.id.textView_contact);
        if (venue == null){
            Log.e("Oops!!","");
        }else{
            /*String resp= getImages(venue.getId());
            try {
                JSONObject jsonObject = new JSONObject(resp);
                JSONArray pics = jsonObject.getJSONObject("response").getJSONObject("photos").getJSONArray("items");
                picUrl =pics.getJSONObject(0).getString("prefix")+"300x150"+pics.getJSONObject(0).getString("suffix");

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Picasso.with(RestaurantDetails.this).load(picUrl).into(imageView);
                    name.setText(venue.getName());
                    String addressString = "";
                    for (String s: venue.getLocation().getFormattedAddress()){
                        addressString = addressString+s;
                    }
                    address.setText(addressString);
                    contact.setText(venue.getContact().getFormattedPhone());
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String getImages(String id) {
        String url = Constants.PHOTO_URL+id+"/photos?client_id="+Constants.CLIENT_ID+"&client_secret="+Constants.CLIENT_SECRET+"&v2=20160219";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("exception form Server!", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                respJson = response.body().string();
                Log.i("Json String: ", respJson);
            }
        });
        return respJson;
    }

    public void schedule (View view){

        Intent intent = new Intent(this, MySchedule.class);
        final Intent mainIntent = new Intent(this,Home.class);
        Schedule schedule = new Schedule();
        Gson gson = new Gson();
        schedule.setUser(USER);
        schedule.setVenueName(venueName);
        String scheduleJSON = gson.toJson(schedule);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, scheduleJSON);
        String requestUrl = "https://api.mlab.com/api/1/databases/ase_assignment/collections/schedule?apiKey=T4RmCJ4GWaqs1nRLHnFoA--K8wrdzly4";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(requestUrl).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Error from the service",e.getMessage());
                mainIntent.putExtra(USER, USER);
                startActivity(mainIntent);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String respJson = response.body().string();
                Log.i("Json String: ", respJson);
                if (respJson.isEmpty() ){
                    Log.e("response json is empty",respJson);
                }

            }
        });

        intent.putExtra(USER,USER);
        startActivity(intent);
    }
}
