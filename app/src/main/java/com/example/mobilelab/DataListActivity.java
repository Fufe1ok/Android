package com.example.mobilelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;
    private SensorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        initViews();
        registerNetworkMonitoring();
        loadSensor();

    }

    private void registerNetworkMonitoring() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkChangeReceiver receiver = new NetworkChangeReceiver(linearLayout);
        this.registerReceiver(receiver, filter);
    }

    private void initViews(){
        recyclerView = findViewById(R.id.data_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        linearLayout = findViewById(R.id.linearLayout);
        swipeRefreshLayout = findViewById(R.id.data_list_swipe_refresh);
        setupSwipeToRefresh();
    }

    private void loadSensor(){
        swipeRefreshLayout.setRefreshing(true);
        final ApiService apiService = getApplicationEx().getApiService();
        final Call<List<Sensor>> call = apiService.getSensors();

        call.enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(final Call<List<Sensor>> call,
                                   final Response<List<Sensor>> response) {
                adapter = new SensorAdapter(response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Snackbar.make(linearLayout, R.string.failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupSwipeToRefresh(){
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadSensor();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private ApplicationEx getApplicationEx(){
        return ((ApplicationEx) getApplication());
    }
}