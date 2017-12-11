package DeliveryTruckTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Main.City.City;
import Main.GeneticAlgorithm.GeneticAlgorithm;
import static Main.GeneticAlgorithm.GeneticAlgorithm.ELITE_ROUTES;
import Main.Population.Population;
import Main.Route.Route;
import java.util.ArrayList;
import java.util.stream.IntStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;



/**
 *
 * @author Amish
 */
public class RoutingTest {
    
    City city1 = new City("Dallas", 32.7767, -96.7970);
        City city2 = new City("Los Angles", 34.6201, -118.6926);
        City city3 = new City("Seattle", 47.6062, -122.3321);
        City city4 = new City("Manhattan", 40.783, -73.966);
    
    @Test
    public void testCalculateRoutes()
    {
        assertEquals(1514.846539580022, city1.calculateDistance(city2),0);
        assertEquals(3339.96584754508, city3.calculateDistance(city4),0);
     }
    
    @Test
    public void testCalculateTotalRoute(){
        
        ArrayList<City> cities = new ArrayList();
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);
        cities.add(city4);
        
        
        Route r = new Route(cities);
        
        assertEquals(9729,r.calculateTotalDistance(),3000);
        
       
    }
    
    @Test
    public void testFitness(){
        
        ArrayList<City> cities = new ArrayList();
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);
        cities.add(city4);
        
        
        Route r = new Route(cities);
        
        assertEquals(1.4388489208633093,r.getFitness(),0.5);
                
    }
    
    @Test
    public void testCrossover(){
        ArrayList<City> cities = new ArrayList();
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);
        cities.add(city4);
        
        Population tempPop = new Population(4, cities);
        Population crossOver = null;
        GeneticAlgorithm ga = new GeneticAlgorithm(cities);
        crossOver = ga.crossoverPopulation(tempPop);
        
        assertEquals(crossOver.hashCode(), tempPop.hashCode(),0.1E10);
        
    }
    
    
    
}