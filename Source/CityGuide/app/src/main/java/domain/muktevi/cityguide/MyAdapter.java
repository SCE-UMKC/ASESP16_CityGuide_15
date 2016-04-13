package domain.muktevi.cityguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import domain.muktevi.cityguide.beans.Venue;

/**
 * Created by Muktevi on 2/15/2016.
 */
public class MyAdapter extends ArrayAdapter<Object> {

    public static String imageUrl = "https://foursquare.com/img/categories/food/default_64.png";

    public MyAdapter(Context context, Venue[] venues) {
        super(context, R.layout.row_layout,venues);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.row_layout, parent, false);
        Venue venue = (Venue) getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.textView_hotel_name);
        textView.setText(venue.getName());
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_icon);

        Picasso.with(getContext()).load(imageUrl).into(imageView);
        return view;
    }
}
