package edu.curtin.oose2024s1.assignment2.state;

import edu.curtin.oose2024s1.assignment2.cash.*;
import edu.curtin.oose2024s1.assignment2.simulation.*;

import java.util.*;

/*
 * AUTHOR: Rivin Pathirage
 * UNIT: Object Oriented Programming for Software Engineering
 * PURPOSE: Interface for the State Pattern
 * REFERENCES: 
 */

public interface State 
{
    boolean processMessage(String message);

    String getFailureReason();
}
