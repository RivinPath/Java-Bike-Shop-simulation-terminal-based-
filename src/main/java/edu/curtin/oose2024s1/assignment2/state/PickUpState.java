package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements Pick Up State of a bike
 * REFERENCES: 
 */

public class PickUpState implements State 
{
    private final BankAccount bankAccount;
    private final BikeInventory inventory;

    private final String email;

    //Failure
    private String failureReason;

    //Default Constructor (with Dependancy Injection)
    /* default */ PickUpState(BankAccount bankAccount, BikeInventory inventory, String email) 
    {
        this.bankAccount = bankAccount;
        this.inventory = inventory;
        this.email = email;
    }

    

    

    

    //PURPOSE: Handle the pickup messages
    @Override
    public boolean processMessage(String message) 
    {
        if (inventory.awaitingPickup.containsKey(email) && inventory.awaitingPickup.get(email) > 0) 
        {
            if (/*inventory.removeAvailableBike() &&*/ inventory.removeAwaitingPickup(email)) 
            {
                //bankAccount.deposit(1000);
                return true;
            }
        } 
        else if (inventory.removeServicedBike(email)) 
        {
            bankAccount.deposit(100);
            return true;
        } 
        else 
        {
            failureReason = "No bikes ready for pickup for this customer";
            return false;
        }
        return false;
    }

    @Override
    public String getFailureReason() 
    {
        return failureReason;
    }

}