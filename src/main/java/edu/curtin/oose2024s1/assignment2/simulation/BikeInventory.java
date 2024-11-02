package edu.curtin.oose2024s1.assignment2.simulation;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.state.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Keeps track of the bike counts and they're states
 * REFERENCES: 
 */

public class BikeInventory 
{
    private int availableBikes;
    private int servicedBikes;
    //private int awaitingPickup;
    public final Map<String, Integer> awaitingPickup;
    private final Map<String, Long> servicedBikes2;
    private final int maxBikes;
    private final List<BikeInventoryObserver> observers = new ArrayList<>();

    //Default Constructor
    /* default */ BikeInventory(int initialBikes, int maxBikes) 
    {
        this.availableBikes = initialBikes;
        this.maxBikes = maxBikes;
        this.awaitingPickup = new HashMap<>();
        this.servicedBikes2 = new HashMap<>();
    }

    public int getAvailableBikesCount() 
    {
        return availableBikes;
    }

    /* default */ int getServicedBikesCount() 
    {
        return servicedBikes;
    }

    

    /* default */ int getAwaitingPickupCount(String email) 
    {
        return awaitingPickup.getOrDefault(email, 0);
    }

    //PURPOSE: Check for space for a new bike
    public boolean hasSpaceForBikes(int count) 
    {
        return availableBikes + servicedBikes + awaitingPickup.values().stream().mapToInt(Integer::intValue).sum() + count <= maxBikes;
    }

    //PURPOSE: Increment the Available bike count
    public void addAvailableBikes(int count) 
    {
        availableBikes += count;
        notifyObservers();
    }

    //PURPOSE: Decrement the Available bike count
    public boolean removeAvailableBike() 
    {
        if (availableBikes > 0) 
        {
            availableBikes--;
            notifyObservers();
            return true;
        }
        return false;
    }

    

    //New with email
    //PURPOSE: Increment the Serviced bike count
    public void addServicedBike(String email) 
    {
        if (hasSpaceForBikes(1)) 
        {
            servicedBikes++;
            servicedBikes2.put(email, System.currentTimeMillis());
            notifyObservers();
        }
    }

    

    //New with email
    //PURPOSE: Decrement the services bike count
    public boolean removeServicedBike(String email) 
    {
        if (servicedBikes2.containsKey(email) && System.currentTimeMillis() - servicedBikes2.get(email) >= 2000) 
        {
            servicedBikes--;
            servicedBikes2.remove(email);
            notifyObservers();
            return true;
        }
        return false;
    }

    

    //PURPOSE: Increment the Awating pickup bike count
    public void addAwaitingPickup(String email) 
    {
        if (hasSpaceForBikes(1)) {
            awaitingPickup.put(email, awaitingPickup.getOrDefault(email, 0) + 1);
            notifyObservers();
        }
    }

    

    //PURPOSE: Decrement the available pickup bike count
    public boolean removeAwaitingPickup(String email) 
    {
        if (awaitingPickup.containsKey(email) && awaitingPickup.get(email) > 0) 
        {
            awaitingPickup.put(email, awaitingPickup.get(email) - 1);
            notifyObservers();
            return true;
        }
        return false;
    }

    //PURPOSE: Add an observer to the list
    /* default */ void addObserver(BikeInventoryObserver observer) 
    {
        observers.add(observer);
    }

    //PURPOSE: remove an observer from the list
    /* default */ void removeObserver(BikeInventoryObserver observer) 
    {
        observers.remove(observer);
    }

    //PURPOSE: Notify all observer of a state change
    private void notifyObservers() 
    {
        for (BikeInventoryObserver observer : observers) 
        {
            observer.inventoryChanged(availableBikes, servicedBikes, awaitingPickup.values().stream().mapToInt(Integer::intValue).sum());
        }
    }
}

interface BikeInventoryObserver 
{
    void inventoryChanged(int availableBikes, int servicedBikes, int awaitingPickup);
}
