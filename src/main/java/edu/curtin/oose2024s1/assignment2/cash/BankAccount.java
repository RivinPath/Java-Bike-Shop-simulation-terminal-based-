package edu.curtin.oose2024s1.assignment2.cash;


import java.util.ArrayList;
import java.util.List;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Managing the Cash Flow
 * REFERENCES: 
 */

public class BankAccount 
{
    private int balance;
    private final List<BankAccountObserver> observers = new ArrayList<>();

    //Default Constructor
    public BankAccount(int initialBalance) 
    {
        this.balance = initialBalance;
    }

    public int getBalance() 
    {
        return balance;
    }

    //Deposite money in the bank
    public void deposit(int amount) 
    {
        balance += amount;
        notifyObservers();
    }

    //Withdraw Money from the bank
    public void withdraw(int amount) 
    {
        if (balance >= amount) 
        {
            balance -= amount;
            notifyObservers();
        }
    }

    //PURPOSE: Add an observer to the list
    /* default */ void addObserver(BankAccountObserver observer) 
    {
        observers.add(observer);
    }

    //PURPOSE: Remove an observer from the list
    /* default */ void removeObserver(BankAccountObserver observer) 
    {
        observers.remove(observer);
    }

    //PURPOSE: Notify all observer of a state change
    private void notifyObservers() 
    {
        for (BankAccountObserver observer : observers) 
        {
            observer.balanceChanged(balance);
        }
    }
}

/*public interface BankAccountObserver 
{
    void balanceChanged(int newBalance);
}*/
