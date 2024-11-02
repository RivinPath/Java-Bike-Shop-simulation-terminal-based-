package edu.curtin.oose2024s1.assignment2.simulation;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.state.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Process all the messages generated from the BikeShopInput class
 * REFERENCES: 
 */

@SuppressWarnings("UnusedPrivateField") //Unused Fields are neccasary for Dependancy Injection
public class MessageProcessor 
{
    private final BankAccount bankAccount;
    private final BikeInventory inventory;
    private final EmployeeManager employeeManager;
    private final StateFactory stateFactory;
    

    private static final Logger logger = Logger.getLogger(MessageProcessor.class.getName());

    //Default Constructor (with some Dependancy Injection)
    /* default */ MessageProcessor(BankAccount bankAccount, BikeInventory inventory, EmployeeManager employeeManager) 
    {
        this.bankAccount = bankAccount;
        this.inventory = inventory;
        this.employeeManager = employeeManager;
        this.stateFactory = new StateFactory(bankAccount, inventory, employeeManager);
    }

    

    //Failure
    //PURPOSE: Decide what to do according to the message using the processMessage() method in the State Interface (use State Pattern)
    /* default */ boolean processMessage(String message) 
    {
        State state = stateFactory.createState(message);
        if (state != null) 
        {
            boolean success = state.processMessage(message);
            if (!success) 
            {
                handleFailure(message, state.getFailureReason());
            }
            return success;
        } 
        else 
        {
            handleFailure(message, "Invalid message");
            return false;
        }
    }

    //PURPOSE: Create and write the failure message
    private void handleFailure(String message, String reason) 
    {
        System.out.println("FAILURE: " + message + " - " + reason);
        writeFailureToFile(message, reason);
    }

    //PURPOSE: Write the Failure message to the text file
    private void writeFailureToFile(String message, String reason) 
    {
        try (FileWriter writer = new FileWriter("sims_results.txt", true)) 
        {
            writer.write("FAILURE: " + message + " - " + reason + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            //e.printStackTrace();
            logger.info(() ->"An error occured " +e.getMessage());
        }
    }
}
