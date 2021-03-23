package com.softvrbox.speedroommating.EventModel;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    //*********************

    //POJO of event objects

    //*********************


    public String cost;
    public String end_time;
    public String image_url;
    public String location;
    public String phone_number;
    public String start_time;
    public String venue;

    public Event() {
    }

    public Event(String cost, String end_time, String image_url, String location, String phone_number, String start_time, String venue) {
        this.cost = cost;
        this.end_time = end_time;
        this.image_url = image_url;
        this.location = location;
        this.phone_number = phone_number;
        this.start_time = start_time;
        this.venue = venue;
    }


    public String getCost() {
        return cost;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getVenue() {
        return venue;
    }
}
