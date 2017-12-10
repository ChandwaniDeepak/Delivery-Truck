/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Route;

import Main.City.City;
import Main.GeneticAlgorithm.GeneticAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author erdee
 */
public class Route {
    private ArrayList<City> cities = new ArrayList<>();
    private boolean isFitnessChanged = true;
    private double fitnessValue = 0;
    public Route (GeneticAlgorithm geneticAlgorithm){
        geneticAlgorithm.getInitialRoute().forEach(x -> cities.add(null));
    }
    public Route(ArrayList<City> cities) {
        this.cities.addAll(cities);
        Collections.shuffle(this.cities);
    }

    
    public ArrayList<City> getCities() {
        isFitnessChanged = true;
        return cities;
    }
    @Override
    public String toString()
    {
        return Arrays.toString(cities.toArray());
    }
    public double getFitness()
    {
        if(isFitnessChanged == true)
        {
            fitnessValue = (1/calculateTotalDistance()) * 10000;
            isFitnessChanged = false;
        }
        return fitnessValue;
    }
    
    public double calculateTotalDistance()
    {
        int citySize = this.cities.size();
        return (int) (this.cities.stream().mapToDouble(x ->{
        int cityIndex = this.cities.indexOf(x);
        double returnvalue = 0;
        if(cityIndex < citySize - 1) returnvalue = x.calculateDistance(this.cities.get(cityIndex + 1));
        return returnvalue;
        }).sum() + this.cities.get(0).calculateDistance(this.cities.get(citySize - 1))); 
        
    }
    
}
