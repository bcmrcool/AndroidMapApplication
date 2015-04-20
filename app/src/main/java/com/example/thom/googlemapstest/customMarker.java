package com.example.thom.googlemapstest;

/**
 * Created by Thom on 4/19/2015.
 */
public class customMarker {

    private String name;
    private String rating;
    private String photo;
    private String blurb;
    private String category;
    private Double lat;
    private Double lng;

    public customMarker(String name, String rating,
                        String photo, String blurb,
                        String category, Double lat, Double lng) {
        this.name = name;
        this.rating = rating;
        this.photo = photo;
        this.blurb = blurb;
        this.category = category;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBlurb() {
        return blurb;
    }

    public String getCategory() {
        return category;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

}
