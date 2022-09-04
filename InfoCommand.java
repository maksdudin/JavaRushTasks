package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

class InfoCommand implements Command{

    public void execute() {
        if(CurrencyManipulatorFactory.getAllCurrencyManipulators().size()==0)ConsoleHelper.writeMessage("No money available.");
        for(CurrencyManipulator c: CurrencyManipulatorFactory.getAllCurrencyManipulators()){
        if(c.hasMoney()){
            ConsoleHelper.writeMessage(c.getCurrencyCode()+" - "+c.getTotalAmount());
        }else{
            ConsoleHelper.writeMessage("No money available.");
        }
    }
    }
}
