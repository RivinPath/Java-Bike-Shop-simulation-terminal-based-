package edu.curtin.oose2024s1.assignment2.cash;

import edu.curtin.oose2024s1.assignment2.simulation.*;
import edu.curtin.oose2024s1.assignment2.state.*;

import java.util.ArrayList;
import java.util.List;
/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Pays the emplopee every week
 * REFERENCES: 
 */

public class EmployeeManager 
{
    private int daysWorked = 0;
    private final List<EmployeeManagerObserver> observers = new ArrayList<>();

    //Default Constructor
    public void payEmployeeIfRequired(int currentDay) 
    {
        daysWorked++;
        if (daysWorked % 7 == 0) 
        {
            notifyObservers();
        }
    }

    //PURPOSE: Add an observer to the list
    public void addObserver(EmployeeManagerObserver observer) 
    {
        observers.add(observer);
    }

    //PURPOSE: remove an observer from the list
    /* default */ void removeObserver(EmployeeManagerObserver observer) 
    {
        observers.remove(observer);
    }

    //PURPOSE: Notify all observer of a state change
    private void notifyObservers() 
    {
        for (EmployeeManagerObserver observer : observers) 
        {
            observer.employeePaid();
        }
    }
}

/*public interface EmployeeManagerObserver 
{
    void employeePaid();
}*/