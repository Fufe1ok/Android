package com.example.mobilelab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private List<Sensor> sensorList;
    private Context context;

    public SensorAdapter(FragmentActivity activity, List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @NonNull
    @Override
    public SensorAdapter.SensorViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                             final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor, parent, false);
        return new SensorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SensorViewHolder holder,
                                 final int position) {
        Picasso.get().load(sensorList.get(position).getPhotoUrl()).into(holder.photoUrl);
        holder.name.setText(sensorList.get(position).getName());
        holder.year.setText(sensorList.get(position).getYear());
        holder.purpose.setText(sensorList.get(position).getPurpose());
        holder.manufacturer.setText(sensorList.get(position).getManufacturer());
        holder.price.setText(sensorList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    class SensorViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView year;
        private TextView purpose;
        private TextView manufacturer;
        private TextView price;
        private ImageView photoUrl;

        private SensorViewHolder(final View itemView) {
            super(itemView);

            photoUrl = itemView.findViewById(R.id.item_sensor_image_view);
            name = itemView.findViewById(R.id.item_sensor_name);
            year = itemView.findViewById(R.id.item_sensor_year);
            purpose = itemView.findViewById(R.id.item_sensor_purpose);
            manufacturer = itemView.findViewById(R.id.item_sensor_manufacturer);
            price = itemView.findViewById(R.id.item_sensor_price);
        }
    }

    private void openItemDetails(int position) {
        Intent intent = new Intent(context, SensorDetails.class);
        intent.putExtra("sensor_name", sensorList.get(position).getName());
        intent.putExtra("year", sensorList.get(position).getYear());
        intent.putExtra("purpose", sensorList.get(position).getPurpose());
        intent.putExtra("manufacturer", sensorList.get(position).getManufacturer());
        intent.putExtra("price", sensorList.get(position).getPrice());
        intent.putExtra("image", sensorList.get(position).getPhotoUrl());

        context.startActivity(intent);
    }

}
