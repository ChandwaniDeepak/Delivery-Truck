/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.City;

/**
 *
 * @author erdee
 */
public class City {
    private static final double EARTH_RADIUS = 6378.1370;
    private static final double D_TO_R = Math.PI/180;
    private static final double KM_TO_MILES = 0.621371;
    
    private double lng;
    private double lat;
    private String cityName;

    public City(String cityName, double lng, double lat) {
        this.lng = lng * D_TO_R;
        this.lat = lat * D_TO_R;
        this.cityName = cityName;
    }

    
    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getCityName() {
        return cityName;
    }
    
    
    @Override
    public String toString()
    {
        return cityName;
    }
    public double calculateDistance(City city)
    {
        double diffLat = city.getLat() - this.getLat();
        double diffLng = city.getLng() - this.getLng();
        double a = Math.pow(Math.sin(diffLat/2D), 2D) + 
                Math.cos(this.getLat()) * Math.cos(city.getLat()) * Math.pow(Math.sin(diffLng / 2D), 2D);
        
        return KM_TO_MILES * EARTH_RADIUS * 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
    }
    
}
