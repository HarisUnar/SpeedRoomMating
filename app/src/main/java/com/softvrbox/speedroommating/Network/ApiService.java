package com.softvrbox.speedroommating.Network;

import android.content.res.Resources;

import com.softvrbox.speedroommating.EventModel.Event;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {

    //*********************

    // API Service for fetching json data
    // along with secret-key authorization

    //*********************

    @Headers({
            "Content-Type: application/json",
            "secret-key: $2b$10$76APFiNwr0YXKLX6FDCGiuks/TPFnSKkJleMY2uz1AR1EqTK9IODC"
    })
     @GET("605479c67ffeba41c07de021")
  //  @GET("605479c67ffeba41c07de02")
    Observable<List<Event>> getEvents();

}
