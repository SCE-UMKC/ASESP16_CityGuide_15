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

import domain.muktevi.cityguide.beans.Schedule;
import domain.muktevi.cityguide.beans.Venue;

/**
 * Created by Vijay Kumar Tummala on 4/13/2016.
 */
public class ScheduleAdapter extends ArrayAdapter<Object> {

    public ScheduleAdapter(Context context, Schedule[] schedules) {
        super(context, R.layout.schedule_layout,schedules);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(R.layout.schedule_layout, parent, false);
        Schedule schedule= (Schedule) getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.textView_venue_name);
        textView.setText(schedule.getVenueName());

        return view;
    }
}
