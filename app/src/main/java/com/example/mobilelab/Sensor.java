package com.example.mobilelab;

public class Sensor {

    private final String name;
    private final String year;
    private final String purpose;
    private final String manufacturer;
    private final String price;
    private final String photoUrl;

    public Sensor(final String name, final String year, final String purpose,
                  final String manufacturer, final String price, final String photoUrl) {
        this.name = name;
        this.year = year;
        this.purpose = purpose;
        this.manufacturer = manufacturer;
        this.price = price;
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    String getYear() {
        return year;
    }

    String getPurpose() {
        return purpose;
    }

    String getManufacturer() {
        return manufacturer;
    }

    String getPrice() {
        return price;
    }

    String getPhotoUrl() {
        return photoUrl;
    }
}