package com.example.mobilelab;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SensorDetails extends AppCompatActivity {

    private TextView sensorName;
    private TextView year;
    private TextView purpose;
    private TextView manufacturer;
    private TextView price;
    private ImageView imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_details_activity);

        getIncomingIntent();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("sensor_name") &&
                getIntent().hasExtra("year") &&
                getIntent().hasExtra("purpose") &&
                getIntent().hasExtra("manufacturer") &&
                getIntent().hasExtra("price")) {
            String sensorNameInfo = getIntent().getStringExtra("sensor_name");
            String yearInfo = getIntent().getStringExtra("year");
            String purposeInfo = getIntent().getStringExtra("purpose");
            String manufacturerInfo = getIntent().getStringExtra("manufacturer");
            String priceInfo = getIntent().getStringExtra("price");
            String imageUrlInfo = getIntent().getStringExtra("image");

            setInfo(sensorNameInfo, yearInfo, purposeInfo, manufacturerInfo, priceInfo, imageUrlInfo);
        }
    }

    private void initViews() {
        sensorName = findViewById(R.id.sensor_details_name);
        year = findViewById(R.id.sensor_details_year);
        purpose = findViewById(R.id.sensor_details_purpose);
        manufacturer = findViewById(R.id.sensor_details_manufacturer);
        price = findViewById(R.id.sensor_details_price);
        imageUrl = findViewById(R.id.sensor_details_image_view);
    }

    private void setInfo(String sensorNameInfo, String yearInfo,
                         String purposeInfo, String manufacturerInfo,
                         String priceInfo, String imageUrlInfo) {
        initViews();
        sensorName.setText(sensorNameInfo);
        year.setText(yearInfo);
        purpose.setText(purposeInfo);
        manufacturer.setText(manufacturerInfo);
        price.setText(priceInfo);
        Picasso.get().load(imageUrlInfo).into(imageUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SensorDetails.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}