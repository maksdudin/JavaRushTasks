package com.javarush.task.task26.task2613;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {

    private static CurrencyManipulatorFactory currencyManipulatorFactory;
    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    private CurrencyManipulatorFactory() {
    }

    public static CurrencyManipulatorFactory getCurrencyManipulatorFactory() {
        if (currencyManipulatorFactory == null) currencyManipulatorFactory = new CurrencyManipulatorFactory();
        return currencyManipulatorFactory;

    }
    
    //В этом методе будем создавать нужный манипулятор, если он еще не существует, либо возвращать ранее созданный.
    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        currencyCode=currencyCode.toUpperCase();// не дложно быть чувствительно к регистру
        map.putIfAbsent(currencyCode, new CurrencyManipulator(currencyCode));// если такой валюты не
        // было она добавится в map
        return map.get(currencyCode);
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        return map.values();
    }

    public  static boolean isCurrencyCodeExist(String currencyCode){
        return map.containsKey(currencyCode);
    }


}
