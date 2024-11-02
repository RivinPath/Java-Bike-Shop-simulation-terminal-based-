package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements Delivery State of a bike
 * REFERENCES: 
 */

public class DeliveryState implements State 
{
    private final BankAccount bankAccount;
    private final BikeInventory inventory;

    //Failures
    private String failureReason;

    //Default Constructor (with Dependancy Injection)
    /* default */ DeliveryState(BankAccount bankAccount, BikeInventory inventory) 
    {
        this.bankAccount = bankAccount;
        this.inventory = inventory;
    }

    

    //Failure
    //PURPOSE: Handle the delivery messages
    @Override
    public boolean processMessage(String message) 
    {
        if (bankAccount.getBalance() >= 5000 && inventory.hasSpaceForBikes(10)) 
        {
            bankAccount.withdraw(5000);
            inventory.addAvailableBikes(10);
            return true;
        } 
        else 
        {
            if (bankAccount.getBalance() < 5000) 
            {
                failureReason = "Not enough cash to buy new bikes";
            } 
            else 
            {
                failureReason = "Not enough space for new bikes";
            }
            return false;
        }
    }



    @Override
    public String getFailureReason() 
    {
        return failureReason;
    }
}
