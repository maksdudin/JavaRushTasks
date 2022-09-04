package com.javarush.task.task26.task2613;


import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;


import java.util.Map;
import java.util.TreeMap;

public class CurrencyManipulator {
    private String currencyCode;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    private TreeMap<Integer, Integer> denominations = new TreeMap<>();// номинал и кол-во купюр (номинал отсортирован)
    private TreeMap<Integer, Map<Integer, Integer>> mapDenominations = new TreeMap<>();// здесь дложны быть возможные варианты
    // выдачи отсортированные по кол-ву купюр
    private Integer numberOfBanknotes = 0;// общее кол-во купюр


    public void addAmount(int denomination, int count) {// вносит в банкомат подаваемое на вход номинал и кол-во купюр
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }

    }

    public int getTotalAmount() {// Общее кол-во валюты
        int totalAmotion = 0;
        for (Integer denomination : denominations.keySet()) {
            totalAmotion += denomination * denominations.get(denomination);
        }
        return totalAmotion;
    }

    public boolean hasMoney() {// есть ли деньги вообще
        return getTotalAmount() != 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {//есть ли в банкомате запращиваемая сумма в даной валюте
        return getTotalAmount() >= expectedAmount;
    }

    public boolean isBalanceNull(Integer balance, Integer maxNominal) {//если остаток делится нацело
        return balance % maxNominal == 0;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {//пытаемся выдать нужную сумму минимальным кол-вом купюр
        TreeMap<Integer, Integer> map = new TreeMap<>();// номинал - количество новая карта для работы с валютой
        for(Map.Entry<Integer,Integer> entry:denominations.entrySet()){
            map.put(entry.getKey(),entry.getValue());
        }
         Integer devide = expectedAmount;// остаток приравниваем нужно к выдаче сумме

        while (map.size() != 0) {// если сошёлся
            // всё равно проверим на другие варианты
            extracted(map, devide);
            map.remove(map.lastKey());// удаляем из вспомогательной карты элемент
        }
        if (mapDenominations.size() == 0){
            throw new NotEnoughMoneyException();
        }else{

        }
        return mapDenominations.get(mapDenominations.firstKey());
    }


    private Integer extracted(TreeMap<Integer, Integer> map, Integer devide) {
        TreeMap<Integer, Integer> amountNominal = new TreeMap<>();// карта куда будет складываться удачная комбинация
        Integer[] banknotesList = new Integer[5001];

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {//пробегаем по карте имеющихся в банкомате купюр
            // ( номинал кол-во) начинаем с максимального ноимнала
            banknotesList[entry.getKey()] = entry.getValue();
        }
        for (int i = banknotesList.length - 1; i > 0; i--) {
            if (banknotesList[i] != null) {
                if (banknotesList[i] != 0 && i <= devide) {//если купюр нет или максимальный номинал больше суммы к выдаче
                    if (devide == 0) return devide;//проверка что сумма к выводу не нулевая
                    Integer maxNominal = i;// максимальный номинал купюры
                    Integer amount = banknotesList[i];// имеющееся кол-во таких купюр
                    if (isBalanceNull(devide, maxNominal)) {// если делиться нацело
                        devide = divideСompletely(devide, maxNominal, amount, amountNominal, map);
                    } else {//если не делится нацело
                        devide = divideNotСompletely(devide, maxNominal, amount, amountNominal, map);
                    }
                    if (devide == 0) {// ура всё поделилось
                        mapDenominations.putIfAbsent(numberOfBanknotes, amountNominal);
                        numberOfBanknotes = 0;
                        return devide;
                    }
                }
            }
        }
        return devide;
    }

    public Integer divideСompletely(Integer balance, Integer maxNominal, Integer amount, TreeMap<Integer, Integer> amountNominal,
                                    TreeMap<Integer, Integer> newMap) {
        Integer divide = 0;//остаток от суммы
        Integer count = balance / maxNominal;
        if (count == amount) {//если прям так совпало,  что общая сумма банкнот этого номинала равна требуему к выдаче числу
            numberOfBanknotes += count;//число купюр к выдаче
            amountNominal.put(maxNominal, amount);// заносим в карту к выдаче
            //newMap.put(maxNominal,0);// убираем из вспомогательной карты
        }
        if (count > amount) {// если кол-во имеющихся банкнот меньше необходмого для выдачи полной суммы
            numberOfBanknotes += amount;
            divide = balance - amount * maxNominal;// остаток который нужно выдать имеющимися банкнотами
            amountNominal.put(maxNominal, amount);// заносим в карту к выдаче
            //newMap.put(maxNominal,0);// меняем вспомогательную карту (пока не знаю зачем)
            return divide;// возвращаем остаток для дальнейших операций

        }
        if (count < amount) {//если кол-во имеющихся банкнот больше необходмого для выдачи полной суммы
            numberOfBanknotes += count;
            amountNominal.put(maxNominal, count);// заносим в карту к выдаче
            //newMap.put(maxNominal,amount-count);// меняем вспомогательную карту
        }
        return divide;
    }

    public Integer divideNotСompletely(Integer balance, Integer maxNominal, Integer amount, TreeMap<Integer, Integer> map,
                                       TreeMap<Integer, Integer> newMap) {
        Integer i = balance % maxNominal;// вычисляем остаток от целочисленного деления суммы на купюру макс номинала
        Integer sum = balance - i;// сумма которая поделиться нацело
        Integer devide = i + divideСompletely(sum, maxNominal, amount, map, newMap);
        return devide;// остаток который мы вернём
    }


}
