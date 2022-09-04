package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

import static com.javarush.task.task26.task2613.Operation.EXIT;

public class CashMachine {

    public static void main(String[] args) {
        try {
            Operation operation;
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != EXIT);
        } catch (InterruptOperationException ignored) {
            ConsoleHelper.writeMessage("bye, see you later");
        }
    }

}
