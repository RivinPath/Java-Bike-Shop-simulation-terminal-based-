package edu.curtin.oose2024s1.assignment2.simulation;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.state.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements the simulation
 * REFERENCES: 
 */

public class BikeShopSimulation implements EmployeeManagerObserver
{
    private final BikeShopInput input;
    private final MessageProcessor processor;
    private final BankAccount bankAccount;
    private final BikeInventory inventory;
    private final EmployeeManager employeeManager;
    private int day = 0;
    private int totalMessages = 0;
    private int totalFailures = 0;

    private static final Logger logger = Logger.getLogger(BikeShopSimulation.class.getName());

    //Default Constructor (With Dependancy Injection)
    /* default */ BikeShopSimulation(BikeShopInput input, MessageProcessor processor, BankAccount bankAccount, BikeInventory inventory, EmployeeManager employeeManager) 
    {
        this.input = input;
        this.processor = processor;
        this.bankAccount = bankAccount;
        this.inventory = inventory;
        this.employeeManager = employeeManager;

        employeeManager.addObserver(this);
    }

    //PURPOSE: trigger the simulation
    /* default */ void startSimulation() 
    {
        try 
        {
            while (System.in.available() == 0) 
            {
                //processMessages();

                //This is the one with new error
                BikeShop.processMessagesAndPrint(input, this);
                endOfDay();
                Thread.sleep(1000);
            }
            writeResultsToFile();
        } 
        catch (IOException | InterruptedException e) 
        {
            //e.printStackTrace();
            logger.info(() ->"An error occured " +e.getMessage());
        }
    }

    

    //PURPOSE: Decide whether the incoming message is a success or failure using the processMessage() method in the MessageProcessor class
    /* default */ void processMessage(String message) 
    {
        totalMessages++;
        boolean success = processor.processMessage(message);
        if (!success) 
        {
            totalFailures++;
            System.out.println("FAILURE");
        }
    }

    

    //PURPOSE: The end of day logic
    private void endOfDay() 
    {
        employeeManager.payEmployeeIfRequired(day);
        day++;

        int availableBikes = inventory.getAvailableBikesCount();
        int servicedBikes = inventory.getServicedBikesCount();
        int awaitingPickupBikes = inventory.awaitingPickup.values().stream().mapToInt(Integer::intValue).sum();
        int cash = bankAccount.getBalance();

        String endOfDayMessage = "Day: " + day + ", Available Bikes: " + availableBikes + ", Serviced Bikes: " + servicedBikes+ ", Awaiting Pickup: " + awaitingPickupBikes + ", Cash: $" + cash;
        logger.info(() ->"d " + day+ " bikes " +availableBikes+ " service " +servicedBikes+ " pickup " +awaitingPickupBikes+ " cash " +cash );

        System.out.println(endOfDayMessage);
        writeResultToFile(endOfDayMessage);
    }

    //PURPOSE: create the file and write the result
    private void writeResultToFile(String result) 
    {
        try (FileWriter writer = new FileWriter("sims_results.txt", true)) 
        {
            writer.write(result + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            //e.printStackTrace();
            logger.info(() ->"An error occured " +e.getMessage());
        }

        
    }

    private void writeResultsToFile() 
    {
        try (FileWriter writer = new FileWriter("sims_results.txt", true)) 
        {
            writer.write("Total Messages: " + totalMessages + System.lineSeparator());
            writer.write("Total Failures: " + totalFailures + System.lineSeparator());
        } 
        catch (IOException e) 
        {
            //e.printStackTrace();
            logger.info(() ->"An error occured " +e.getMessage());
        }
    }

    //PURPOSE: Decrement the employee salary from the bank
    @Override
    public void employeePaid() 
    {
        bankAccount.withdraw(1000);
    }
}
