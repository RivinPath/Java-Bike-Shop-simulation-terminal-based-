package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements Purchase In Store State of a bike
 * REFERENCES: 
 */

public class PurchaseInStoreState implements State 
{
    private final BankAccount bankAccount;
    private final BikeInventory inventory;

    //Failure
    private String failureReason;

    //Default Constructor (with Dependancy Injection)
    /* default */ PurchaseInStoreState(BankAccount bankAccount, BikeInventory inventory) 
    {
        this.bankAccount = bankAccount;
        this.inventory = inventory;
    }

    

    //Failure
    //PURPOSE: Handle the purchase in store messages
    @Override
    public boolean processMessage(String message) 
    {
        if (inventory.getAvailableBikesCount() > 0) 
        {
            if (inventory.removeAvailableBike()) 
            {
                bankAccount.deposit(1000);
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
