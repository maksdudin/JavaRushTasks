package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;


import java.util.ConcurrentModificationException;
import java.util.Map;

class WithdrawCommand implements Command {
    @Override
    public void execute() throws InterruptOperationException {
        String curencyCod = ConsoleHelper.askCurrencyCode();
        while (!CurrencyManipulatorFactory.isCurrencyCodeExist(curencyCod)){

            curencyCod=ConsoleHelper.askCurrencyCode();
        }
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(curencyCod);

        while (true) {
            ConsoleHelper.writeMessage("Введите сумму которую хотите снять");
            try {
                int summ = Integer.parseInt(ConsoleHelper.readString());
                if (summ == 0) {
                    ConsoleHelper.writeMessage("Неверный ввод, введите сумму которую хотите снять ");
                    continue;
                }
                if (manipulator.isAmountAvailable(summ)) {
                    Map<Integer, Integer> map = manipulator.withdrawAmount(summ);
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
                    }
                    ConsoleHelper.writeMessage("Транзакция прошла успешно");
                    return;
                } else {
                    ConsoleHelper.writeMessage("на ашем счету недостаточно денег");

                    throw new NotEnoughMoneyException();

                }
            } catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage("Неверный ввод, введите сумму которую хотите снять ");
            } catch (NotEnoughMoneyException  e) {
                ConsoleHelper.writeMessage("Невозможно выдать запрашиваемую сумму");
            } catch (ConcurrentModificationException e){
                e.printStackTrace();
            }

        }
    }
}
