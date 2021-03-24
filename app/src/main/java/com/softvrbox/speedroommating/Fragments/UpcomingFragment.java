package com.softvrbox.speedroommating.Fragments;

import android.Manifest;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.softvrbox.speedroommating.EventModel.Event;
import com.softvrbox.speedroommating.EventModel.EventAdapter;
import com.softvrbox.speedroommating.Network.ApiService;
import com.softvrbox.speedroommating.Network.NetworkClient;
import com.softvrbox.speedroommating.R;
import com.softvrbox.speedroommating.Utils.InternetCheck;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UpcomingFragment extends Fragment {


    ApiService apiService;
    RecyclerView eventRecyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    LinearLayout loadingCardsLayout, noEventLayout, errorLayout, noInternetLayout;
    TextView internetRetryButton, errorRetryButton;
    CardView loadingCard;

    View view;

    // List<Event> eventsListRecycler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_upcoming, container, false);


        //Init API
        initializeAPI();

        //init UI Views
        initViews();

        //Check Calling Permission
        CheckPermission();

        //check Internet connectivity and Fetch Data
        checkInternet();


        return view;
    }

    public void retryLoading() {
        loadingCard.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        new Handler().postDelayed(this::checkInternet, 2000);
    }

    private void checkInternet() {
        if (InternetCheck.isConnected(getActivity())) {

            //loading data card set visible
            loadingCardsLayout.setVisibility(View.VISIBLE);

            //Fetch Data From Api
            fetchData();


        } else {
            loadingCard.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
    }


    private void initViews() {
        loadingCard = view.findViewById(R.id.loadingCard);
        loadingCardsLayout = view.findViewById(R.id.loadingCardsLayout);
        noEventLayout = view.findViewById(R.id.noEventLayout);
        errorLayout = view.findViewById(R.id.errorLayout);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);
        errorRetryButton = view.findViewById(R.id.errorRetryButton);
        internetRetryButton = view.findViewById(R.id.internetRetryButton);

        internetRetryButton.setOnClickListener(view -> retryLoading());

        errorRetryButton.setOnClickListener(view -> retryLoading());


        eventRecyclerView = view.findViewById(R.id.eventRecycler);
        eventRecyclerView.setHasFixedSize(true);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initializeAPI() {

        Retrofit retrofit = NetworkClient.getInstance();
        apiService = retrofit.create(ApiService.class);
    }

    private void CheckPermission() {

        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        CheckPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    private void fetchData() {


        compositeDisposable.add(apiService.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {

                    // checking for any empty events, if there is any empty event removing it from list
                    for (int i = 0; i < events.size(); i++) {
                        Event currentEvent = events.get(i);
                        if (currentEvent.cost != null && !currentEvent.cost.equals("") && !currentEvent.cost.equals(" ")) {
                            //nothing
                        } else {
                            events.remove(i);
                        }
                    }

                    //checking if events list has events or is empty
                    if (events.size() > 0) {

                        //Load events into recycler
                        loadRecycler(events);

                        //hide progress bar after two seconds
                        new Handler().postDelayed(() -> loadingCard.setVisibility(View.GONE), 2000);

                    } else {
                        //showing no event message
                        noEvent();
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    //showing error message
                    onError();

                }));


    }

    private void onError() {
        errorLayout.setVisibility(View.VISIBLE);
        loadingCardsLayout.setVisibility(View.GONE);
        loadingCard.setVisibility(View.GONE);
    }

    private void noEvent() {
        noEventLayout.setVisibility(View.VISIBLE);
        loadingCardsLayout.setVisibility(View.GONE);
        loadingCard.setVisibility(View.GONE);
    }

    private void loadRecycler(List<Event> events) {


        new Handler().postDelayed(() -> {

            EventAdapter adapter = new EventAdapter(getActivity(), events);
            eventRecyclerView.setAdapter(adapter);

            loadingCardsLayout.setVisibility(View.GONE);
            eventRecyclerView.setVisibility(View.VISIBLE);
        }, 1000);


    }


    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();

    }

}