package domain.muktevi.cityguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Muktevi on 2/15/2016.
 */
public class MyAdapter extends ArrayAdapter<String> {

    public static String imageUrl = "https://foursquare.com/img/categories/food/default_64.png";

    public MyAdapter(Context context, String[] values) {
        super(context, R.layout.row_layout,values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.row_layout, parent, false);
        String hotelName = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.textView_hotel_name);
        textView.setText(hotelName);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_icon);
        Picasso.with(getContext()).load(imageUrl).into(imageView);
        return view;
    }
}
