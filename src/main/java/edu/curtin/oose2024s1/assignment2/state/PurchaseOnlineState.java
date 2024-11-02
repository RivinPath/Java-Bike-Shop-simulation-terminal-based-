package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements Purchase Online State of a bike
 * REFERENCES: 
 */

class PurchaseOnlineState implements State 
{
    private final BankAccount bankAccount;
    private final BikeInventory inventory;

    private final String email;
    

    //Failure
    private String failureReason;

    //Default Constructor (with Dependancy Injection)
    /* default */ PurchaseOnlineState(BankAccount bankAccount, BikeInventory inventory, String email) 
    {
        this.bankAccount = bankAccount;
        this.inventory = inventory;
        this.email = email;
    }

    

    //Failure
    //PURPOSE: Handle the purchase online messages
    @Override
    public boolean processMessage(String message) 
    {
        if (inventory.getAvailableBikesCount() > 0) 
        {
            if (inventory.removeAvailableBike()) 
            {
                bankAccount.deposit(1000);
                inventory.addAwaitingPickup(email); //Remove email if shit happens
                return true;
            }
        } 
        else 
        {
            failureReason = "No bikes available for purchase";
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
