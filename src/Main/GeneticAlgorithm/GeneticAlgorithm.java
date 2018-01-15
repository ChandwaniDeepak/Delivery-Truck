/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.GeneticAlgorithm;

import Main.City.City;
import Main.Route.Route;
import Main.Population.Population;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 *
 * @author erdee
 */
public class GeneticAlgorithm {

    public static final int POPULATION_SIZE = 10;
    public static final double MUTATION = (0.5/100);
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int ELITE_ROUTES = 1;
    public static final int GENERATIONS = 750;
    public ArrayList<City> initialRoute = null;

    public GeneticAlgorithm(ArrayList<City> initialRoute) {
        this.initialRoute = initialRoute;
    }

    public ArrayList<City> getInitialRoute() {
        return initialRoute;
    }

    public Population evolve(Population population) {
        return mutatePopulation(crossoverPopulation(population));
    }

    public Population crossoverPopulation(Population population) {
        Population crossoverPopulation = new Population(population.getRoutes().size(), this);
        IntStream.range(0, ELITE_ROUTES).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
        IntStream.range(ELITE_ROUTES, crossoverPopulation.getRoutes().size()).forEach(x -> {
            Route r1 = selectTournamentPopulation(population).getRoutes().get(0);
            Route r2 = selectTournamentPopulation(population).getRoutes().get(0);
            crossoverPopulation.getRoutes().set(x, crossoverRoute(r1, r2));
        });
        return crossoverPopulation;
    }

    public Population mutatePopulation(Population population) {
        population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= ELITE_ROUTES).forEach(x -> mutateRoute(x));
        return population;
    }

    Route crossoverRoute(Route r1, Route r2) {
        Route crossoverRoute = new Route(this);
        Route tempR1 = r1;
        Route tempR2 = r2;
        if (Math.random() < 0.5) {
            tempR1 = r2;
            tempR2 = r1;
        }
        for (int x = 0; x < crossoverRoute.getCities().size()/2; x++) {
            crossoverRoute.getCities().set(x, tempR1.getCities().get(x));
        }
        return fillNullInCrossoverRoute(crossoverRoute, r2);
    }

    private Route fillNullInCrossoverRoute(Route crossoverRoute, Route route) {
        route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(cityX -> {
            for (int y = 0; y < route.getCities().size(); y++) {
                if (crossoverRoute.getCities().get(y) == null) {
                    crossoverRoute.getCities().set(y, cityX);
                    break;
                }
            }
        });
        return crossoverRoute;
    }

// Original route will be mutated to get better results
    Route mutateRoute(Route r) {
        r.getCities().stream().filter(x -> Math.random() < MUTATION).forEach(cityX -> {
            int y = (int) (r.getCities().size() * Math.random());
            City cityY = r.getCities().get(y);
            r.getCities().set(r.getCities().indexOf(cityX), cityY);
            r.getCities().set(y, cityX);
        });
        return r;
    }

    Population selectTournamentPopulation(Population population) {
        Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);
        IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach(x -> tournamentPopulation.getRoutes()
                .set(x, population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))));
        tournamentPopulation.sortROuteByFitnessValue();
        return tournamentPopulation;
    }

}
