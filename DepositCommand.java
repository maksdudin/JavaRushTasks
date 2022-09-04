package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

class DepositCommand implements Command{

    @Override
    public void execute() throws InterruptOperationException {
        Locale.setDefault(Locale.ENGLISH);
        String currencyCode = ConsoleHelper.askCurrencyCode();// запрашиваем код валюты
        String[] digits = ConsoleHelper.getValidTwoDigits(currencyCode);// запрашиваем  номинал и кол-во купюр
        int denomination = Integer.parseInt(digits[0]);
        int count = Integer.parseInt(digits[1]);
        CurrencyManipulator manipulatorByCurrencyCode = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        manipulatorByCurrencyCode.addAmount(denomination, count);// вносим деньги в банкомат
    }
}
