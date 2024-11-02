package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Factory Pattern Implementation for creating relevant objects (States)
 * REFERENCES: 
 */

@SuppressWarnings("UnusedPrivateField") //Unused Fields are neccasary for Dependancy Injection
public class StateFactory 
{
    private final BankAccount bankAccount;
    private final BikeInventory inventory;
    private final EmployeeManager employeeManager;

    //Default Constructor (with Dependancy Injection)
    public StateFactory(BankAccount bankAccount, BikeInventory inventory, EmployeeManager employeeManager) 
    {
        this.bankAccount = bankAccount;
        this.inventory = inventory;
        this.employeeManager = employeeManager;
    }

    //PURPOSE: Creating neccassary states(Objects) according to the messages
    public State createState(String message) 
    {
        if (message.startsWith("DELIVERY")) 
        {
            return new DeliveryState(bankAccount, inventory);
        } 
        else if (message.startsWith("DROP-OFF")) 
        {
            String email = message.substring(9);
            return new DropOffState(inventory, email);
        } 
        else if (message.startsWith("PURCHASE-ONLINE")) 
        {
            String email = message.substring(16);
            return new PurchaseOnlineState(bankAccount, inventory,email);
        } 
        else if (message.equals("PURCHASE-IN-STORE")) 
        {
            return new PurchaseInStoreState(bankAccount, inventory);
        } 
        else if (message.startsWith("PICK-UP")) 
        {
            String email = message.substring(8);
            return new PickUpState(bankAccount, inventory, email);
        }
        return null;
    }
}
