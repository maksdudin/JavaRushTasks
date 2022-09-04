package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try {
            String text = bis.readLine();
            if(text.toUpperCase().equals("EXIT")){
                throw new InterruptOperationException();
            }
            return text;
        } catch (IOException ignored) {
            /* NOP */
        }
        return null;
    }

    public static Operation askOperation() throws InterruptOperationException{//просим пользователя ввести номер операции, если ввод не корректный, обрабатываем исключение
        // исключение и просим повторить ввод
        while (true){
            try{
                ConsoleHelper.writeMessage("введите номер операции 1- INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT");
                String operationNumber = ConsoleHelper.readString();
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(operationNumber.trim()));


            }catch (IllegalArgumentException e){
                ConsoleHelper.writeMessage("неверный номер операции");
            }
        }

    }

    public static String askCurrencyCode() throws InterruptOperationException{
        while (true) {
            ConsoleHelper.writeMessage("Please choose a currency code, for example USD");
            String currencyCode = ConsoleHelper.readString();
            if (currencyCode == null || currencyCode.trim().length() != 3) {
                ConsoleHelper.writeMessage("Please specify valid data.");
                continue;
            }
            return currencyCode.trim().toUpperCase();
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException{
        while (true) {
            ConsoleHelper.writeMessage(String.format("Please specify integer denomination and integer count." +
                    " For example '10 3' means 30 %s", currencyCode));
            String s = ConsoleHelper.readString();
            String[] split = null;
            if (s == null || (split = s.split(" ")).length != 2) {
                ConsoleHelper.writeMessage("Please specify valid data.");

            } else {
                try {
                    if (Integer.parseInt(split[0]) <= 0 || Integer.parseInt(split[1]) <= 0)
                    {
                        ConsoleHelper.writeMessage("Please specify valid data.");
                    }else {return split;}
                } catch (NumberFormatException e) {
                    ConsoleHelper.writeMessage("Please specify valid data.");
                    continue;
                }

            }
        }
    }

}
