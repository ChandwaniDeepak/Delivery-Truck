/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Population;

import Main.City.City;
import Main.GeneticAlgorithm.GeneticAlgorithm;
import Main.Route.Route;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author erdee
 */
public class Population {
    private ArrayList<Route> routes = new ArrayList<Route>(GeneticAlgorithm.POPULATION_SIZE);
    public Population (int popSize, GeneticAlgorithm geneticAlgorithm){
        IntStream.range(0, popSize).forEach(x -> routes.add(new Route(geneticAlgorithm.getInitialRoute())));
    }
    public Population(int popSize, ArrayList<City> city) {
        IntStream.range(0, popSize).forEach(x -> routes.add(new Route(city)));
    }
    
    

    public ArrayList<Route> getRoutes() {
        return routes;
    }
    
    public void sortROuteByFitnessValue()
    {
        routes.sort((route1, route2) -> {
            int flag = 0;
            if(route1.getFitness() > route2.getFitness())
                flag = -1;
            else if(route1.getFitness() < route2.getFitness())
                flag = 1;
            return flag;
        });
    }
    
    
    
}
