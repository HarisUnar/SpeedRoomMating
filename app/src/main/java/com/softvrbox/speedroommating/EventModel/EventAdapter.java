package com.softvrbox.speedroommating.EventModel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softvrbox.speedroommating.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {


    //*********************

    // Adapter of events for recyclerView
    // And custom EventHolder for Adapter

    //*********************


    Context context;
    List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_layout, parent, false);


        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {


        try {


            Event currentEvent = eventList.get(position);
            String cost = currentEvent.getCost();
            String venue = currentEvent.getVenue();
            String location = currentEvent.getLocation();
            String image = currentEvent.getImage_url();
            String phone = currentEvent.getPhone_number();

            String startTimeString = currentEvent.getStart_time();
            String endTimeString = currentEvent.getEnd_time();

            Date startTime = getDate(startTimeString);
            Date endTime = getDate(endTimeString);
            String month = (String) DateFormat.format("MMMM", startTime); // Jun
            String day = (String) DateFormat.format("dd", startTime); // 20
            String fromTime = ((String) DateFormat.format("h:mm AA", startTime)).replace("am", ""); // 20
            fromTime = fromTime.replace("pm", "");
            String toTime = ((String) DateFormat.format("h:mm AA", endTime)).replace("pm", "PM"); // 20
            toTime = toTime.replace("am", "AM");


            char day_first = day.charAt(0);
            if (day_first == '0')
                day = day.substring(1);


            if (cost.equals("free")) {
                holder.costView.setText("FREE");
            } else {
                holder.costView.setText("PAID");

            }

            //Setting Texts of card
            holder.venueView.setText(venue);
            holder.locationView.setText(location);
            holder.monthTopView.setText(month);
            holder.dateView.setText(day + "th " + month);
            holder.timingView.setText(fromTime + " - " + toTime);


            //loading image into imageView of card
            Glide.with(context)
                    .load(image)
                    .placeholder(R.drawable.ic_camera)
                    .into(holder.eventImageView);


            //Click on card dial number
            holder.eventImageView.setOnClickListener(view -> {
                if (phone != null && !phone.equals("")) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));//change the number
                    context.startActivity(callIntent);
                    //Toast.makeText(context, ""+phone, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No number available for this venue.", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    public Date getDate(String dateString){

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        try {
            return inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        TextView monthTopView;
        TextView costView;
        TextView venueView;
        TextView locationView;
        TextView dateView;
        TextView timingView;
        ImageView eventImageView;
        ProgressBar imageProgressBar;

        public EventHolder(@NonNull View itemView) {
            super(itemView);

            monthTopView = itemView.findViewById(R.id.monthTopView);
            costView = itemView.findViewById(R.id.costView);
            venueView = itemView.findViewById(R.id.venueView);
            locationView = itemView.findViewById(R.id.locationView);
            dateView = itemView.findViewById(R.id.dateView);
            timingView = itemView.findViewById(R.id.timingView);
            eventImageView = itemView.findViewById(R.id.eventImageView);
            imageProgressBar = itemView.findViewById(R.id.imageProgressBar);

        }
    }

}
