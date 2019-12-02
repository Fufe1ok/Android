package com.example.mobilelab;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linearLayout;
    private SensorAdapter adapter;

    public DataListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_data_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(Objects.requireNonNull(getView()));
        registerNetworkMonitoring();
        loadSensors();
    }

    private void registerNetworkMonitoring() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkChangeReceiver receiver = new NetworkChangeReceiver(linearLayout);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
    }

    private void initViews(View root) {
        recyclerView = root.findViewById(R.id.data_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        linearLayout = root.findViewById(R.id.linearLayout);
        swipeRefreshLayout = root.findViewById(R.id.data_list_swipe_refresh);
        setupSwipeToRefresh();
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(
                () -> {
                    loadSensors();
                    swipeRefreshLayout.setRefreshing(false);
                }
        );
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void loadSensors() {
        swipeRefreshLayout.setRefreshing(true);
        final ApiService apiService = getApplicationEx().getApiService();
        final Call<List<Sensor>> call = apiService.getSensors();

        call.enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(final Call<List<Sensor>> call,
                                   final Response<List<Sensor>> response) {
                adapter = new SensorAdapter(getActivity(), response.body());
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Snackbar.make(linearLayout, R.string.failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private ApplicationEx getApplicationEx() {
        return ((ApplicationEx) Objects.requireNonNull(getActivity()).getApplication());
    }
}