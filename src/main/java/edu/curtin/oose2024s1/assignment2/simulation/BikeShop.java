package edu.curtin.oose2024s1.assignment2.simulation;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.state.*;

import java.io.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Entry point for the program
 * REFERENCES: 
 */
public class BikeShop
{
    //The main method
    public static void main(String[] args)
    {
        //BikeShopInput inp = new BikeShopInput();
        // BikeShopInput inp = new BikeShopInput(123);  // Seed for the random number generator

        try
        {
            while(System.in.available() == 0)
            {
                BikeShopInput input = new BikeShopInput();
                BankAccount bankAccount = new BankAccount(15000);
                BikeInventory inventory = new BikeInventory(50, 100);
                EmployeeManager employeeManager = new EmployeeManager();

                MessageProcessor processor = new MessageProcessor(bankAccount, inventory, employeeManager);
                BikeShopSimulation simulation = new BikeShopSimulation(input, processor, bankAccount, inventory, employeeManager);


                
                simulation.startSimulation();

                // For illustration purposes -- this just prints out the messages as they come in.
                /*System.out.println("---");
                String msg = inp.nextMessage();
                while(msg != null)
                {
                    System.out.println(msg);
                    msg = inp.nextMessage();
                }*/

                // Wait 1 second
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    throw new AssertionError(e);
                }


                
            }
        }
        catch(IOException e)
        {
            System.out.println("Error reading user input");
        }
    }

    //PURPOSE: Display the messages in the terminal
    public static void processMessagesAndPrint(BikeShopInput input, BikeShopSimulation simulation) 
    {
        System.out.println("---");
        String message;
        while ((message = input.nextMessage()) != null) 
        {
            System.out.println(message);
            simulation.processMessage(message);
        }
    }
}
