package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements DropOff State of a bike
 * REFERENCES: 
 */

public class DropOffState implements State 
{
    private final BikeInventory inventory;
    private final String email;
    //Failure
    private String failureReason;

    //Default Constructor (with Dependancy Injection)
    /* default */ DropOffState(BikeInventory inventory, String email) 
    {
        this.inventory = inventory;
        this.email = email;
    }

    //PURPOSE: Handle the dropoff messages
    @Override
    public boolean processMessage(String message) 
    {
        if (inventory.hasSpaceForBikes(1)) 
        {
            inventory.addServicedBike(email);
            return true;
        }
        //FAILURE
        else 
        {
            failureReason = "Not enough space for new bikes";
            return false;
        }
        //FAILURE
        //return false;
    }

    @Override
    public String getFailureReason() 
    {
        return failureReason;
    }
}
