package edu.curtin.oose2024s1.assignment2.cash;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Implements the observer for the BankAccount class
 * REFERENCES: 
 */

public interface BankAccountObserver 
{
    void balanceChanged(int newBalance);
}
