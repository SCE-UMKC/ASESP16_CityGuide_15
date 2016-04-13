package domain.muktevi.cityguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    public static String USER = "domain.muktevi.cityguide.USER";
    public static String CATEGORY = "domain.muktevi.cityguide.CATEGORY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        USER = intent.getStringExtra(Login.USER);
        TextView textView = (TextView) findViewById(R.id.textView_user);
        textView.setText(USER);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getEvents(View view) {

        Intent intent = new Intent(this,RestaurantSRP.class);
        intent.putExtra(CATEGORY, "EVENTS");
        intent.putExtra(USER, USER);
        startActivity(intent);
    }

    public void getFood(View view) {

        Intent intent = new Intent(this,RestaurantSRP.class);
        intent.putExtra(CATEGORY,"FOOD");
        intent.putExtra(USER, USER);
        startActivity(intent);
    }

    public void getPlaces(View view){
        Intent intent = new Intent(this,RestaurantSRP.class);
        intent.putExtra(CATEGORY,"PLACES");
        intent.putExtra(USER, USER);
        startActivity(intent);
    }
    public void getTransport(View view){
        Intent intent = new Intent(this,RestaurantSRP.class);
        intent.putExtra(CATEGORY,"TRANSPORT");
        intent.putExtra(USER, USER);
        startActivity(intent);
    }
    public void gotoSchedule(View view){
        Intent intent = new Intent(this,MySchedule.class);
        intent.putExtra(USER, USER);
        startActivity(intent);
    }
}
