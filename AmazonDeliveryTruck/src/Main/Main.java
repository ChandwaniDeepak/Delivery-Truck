/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.City.City;
import Main.GeneticAlgorithm.GeneticAlgorithm;
import Main.Route.Route;
import Main.Population.Population;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author erdee
 */
public class Main {

    JFrame frame;
    Graphics2D g2;
    JComponent jComponent;
    Population population;

    public Main() {
        frame = new JFrame("Amazon Delivery Truck");
    }

    public ArrayList<City> initializeRoute = new ArrayList<City>(Arrays.asList(
            //new City("Boston", 42.3342, -71.0589),
            new City("Dallas", 32.8203, -97.0124),
            new City("Montreal", 45.5579, -73.8707),
            //new City("Houston", 29.8168, -95.6821),
            new City("Minnepolis", 44.9706, -93.4019),
            new City("Miami", 25.7823, -80.2996),
            //new City("Kansas City", 39.0921151,-94.8566009),
            //new City("Washington", 38.8935, -77.1550),
            new City("Portland", 45.5435,-122.7948),
            new City("Atlanta", 33.7676, -97.0124),
            new City("Sacremento", 38.5614, -121.5833),
            new City("Chicago", 41.8333, -88.0128),
            new City("Los Angles", 34.6201, -118.6926),
            //new City("New York", 40.6971, -74.2605),
            new City("Phoenix", 33.6050, -112.4059),
            new City("Seattle", 47.6062, -122.3321),            
            new City("Manhattan", 40.783, -73.966)
            //new City("San Antonino", 29.424, -98.494)
    ));

    public static void main(String[] args) {
        // TODO code application logic here
        Main main = new Main();
        Population pop = new Population(GeneticAlgorithm.POPULATION_SIZE, main.initializeRoute);
        pop.sortROuteByFitnessValue();
        GeneticAlgorithm ga = new GeneticAlgorithm(main.initializeRoute);
        int generation = 1;
        main.printHeading(generation++);
        main.printPopulation(pop);
        while (generation < GeneticAlgorithm.GENERATIONS) {
            //main.printHeading(generation++);
            generation++;
            pop = ga.evolve(pop);
            pop.sortROuteByFitnessValue();
            //main.printPopulation(pop);
            //System.out.println("");
            //System.out.println("");
            //main.drawGUI(pop);
        }
        System.out.println("");
        System.out.println("");
        main.printHeading(generation);
        main.printPopulation(pop);
        main.drawGUI(pop);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("Best Route is : " + pop.getRoutes().get(0));// best route will be at 0th location after sorting by fitness
        System.out.println("distance : " + String.format("%.2f", pop.getRoutes().get(0).calculateTotalDistance()) + " miles");
    }

    public void printPopulation(Population pop) {
        pop.getRoutes().forEach(x -> {
            System.out.println(Arrays.toString(x.getCities().toArray()) + " | "
                    + String.format("%.4f", x.getFitness()) + "  | " + String.format("%.2f", x.calculateTotalDistance()));
        });

//        r.getCities().forEach(cty -> {
//            
//        });
    }

    public void drawGUI(Population pop) {
        jComponent = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2 = (Graphics2D) g.create();
                g2.translate(getWidth() + 1400, getHeight() - 1500);
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(5));
                Route r = pop.getRoutes().get(0);
                
                ArrayList<City> ctys = r.getCities();
                City c_last = ctys.get(0), c_0 = ctys.get(ctys.size()-1);
                int mf = 1400, af = 0;
                for (int x = 0; x < ctys.size() - 2; x++) {
                    //c_0 = ctys.get(0);
                    if(x % 2 == 0)
                        g2.setColor(Color.BLACK);
                    else
                        g2.setColor(Color.GREEN);
                    
                    
                    City c_cur = ctys.get(x);
                    City c_nxt = ctys.get(x + 1);
                    City c_nxt_nxt = ctys.get(x + 2);

                    int x1 = (int) (c_cur.getLat() * mf + af);
                    int y1 = (int) (c_cur.getLng() * mf + af);

                    int x2 = (int) (c_nxt.getLat() * mf + af);
                    int y2 = (int) (c_nxt.getLng() * mf + af);

                    int x3 = (int) (c_nxt_nxt.getLat() * mf + af);
                    int y3 = (int) (c_nxt_nxt.getLng() * mf + af);

                    g2.drawLine(x1, y1, x2, y2);
                    g2.drawLine(x2, y2, x3, y3);
                    //  g2.scale(5, 5);

                }
                int x1 = (int) (c_last.getLat() * mf + af);
                int y1 = (int) (c_last.getLng() * mf + af);

                int x2 = (int) (c_0.getLat() * mf + af);
                int y2 = (int) (c_0.getLng() * mf + af);
                g2.drawLine(x1, y1, x2, y2);
                g2.scale(1, 1);
                g2.dispose();
            }
        };

        frame.add(jComponent);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void printHeading(int generation) {

        System.out.println(" > generation # " + generation);
        String headColumn1 = "Route";
        String remainingHeadingColumn = "Fitness | Distance (in miles)";
        int cityNameLength = 0;
        for (int x = 0; x < initializeRoute.size(); x++) {
            cityNameLength += initializeRoute.get(x).getCityName().length();
        }
        int arrayLength = cityNameLength + initializeRoute.size() * 2;
        int partialLength = (arrayLength - headColumn1.length()) / 2;
        for (int x = 0; x < partialLength; x++) {
            System.out.print(" ");
        }
        System.out.print(headColumn1);
        for (int x = 0; x < partialLength; x++) {
            System.out.print(" ");
        }
        if ((arrayLength % 2) == 0) {
            System.out.print(" ");
        }
        System.out.println(" | " + remainingHeadingColumn);
        cityNameLength += remainingHeadingColumn.length() + 3;
        for (int x = 0; x < cityNameLength + initializeRoute.size() * 2; x++) {
            System.out.print("-");
        }
        System.out.println("");

    }
}
