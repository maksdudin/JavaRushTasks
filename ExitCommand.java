package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.Operation;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

class ExitCommand implements Command{
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage("Do you really want to exit? <y,n>");
        String result = ConsoleHelper.readString();
        if (result != null && "y".equals(result.toLowerCase())) {
            ConsoleHelper.writeMessage("Thank you for visiting JavaRush cash machine. Good luck.");
        } else {
            //TODO process NO
        }
    }
}
    /*public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage("do yuo realy want to exit?");
        while(true){
            Operation operation;
            ConsoleHelper.writeMessage("if \"yes\" choose \"y\"");
            ConsoleHelper.writeMessage("if \"no\" choose \"n\"");
            String str = ConsoleHelper.readString().trim();
            if (str.equals("y")){
                ConsoleHelper.writeMessage("bye, see you later");
                return;
            }else if (str.equals("n")){
                operation = ConsoleHelper.askOperation();
            }else{
                ConsoleHelper.writeMessage("wrong choose, please try again");
            }
        }
    }*/

