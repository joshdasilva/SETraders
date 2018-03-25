/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import static java.lang.Math.random;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static setraders.tradingitem.CryptoCurrency.chosenCurrency;
import static setraders.tradingitem.Stock.random;
import setraders.ui.tables.PriceTable;
import setraders.ui.tradingaccount.TradingAccountController;

/**
 *
 * @author alton
 */
public class Forex extends TradingItem{

  
    private static ObservableList cryptolist;

    private final String currencyPair;
    private double price;
    static Random random = new Random();
    public static  String chosenCurrency;
    public static  String oppositePair;

    
    public Forex(String initName, String c)
    {
        price = super.getTradingItemPrice();
        currencyPair = initName;
    }
    
    public String getCurrencyPair()
    {
        return currencyPair;
    }
    
    public static ObservableList getForexList()
    { 
         double [] forexPrice = new double [22];
        
        for(int i = 0; i < forexPrice.length; i++ ){
            forexPrice[i] =  Math.round((0 + (4 - 1) * random.nextDouble())*100)/100.0;
        }
        
     
        ObservableList<PriceTable>  forexlist = FXCollections.observableArrayList(
            new PriceTable(chosenCurrency +"/EUR ",forexPrice[0]),
            new PriceTable(chosenCurrency+"/JPY",forexPrice[1]),
            new PriceTable(chosenCurrency+"/"+ oppositePair,forexPrice[2]),
            new PriceTable(chosenCurrency+"/AUD",forexPrice[3]),
            new PriceTable(chosenCurrency+"/CAD",forexPrice[4]),
            new PriceTable(chosenCurrency+"/CNY",forexPrice[5]),
            new PriceTable(chosenCurrency+"/CHF",forexPrice[6]),
            new PriceTable(chosenCurrency+"/MXN",forexPrice[7]),
            new PriceTable(chosenCurrency+"/SGD",forexPrice[8]),
            new PriceTable(chosenCurrency+"/KRW",forexPrice[9]),
            new PriceTable(chosenCurrency+"/NZD",forexPrice[10]),
            new PriceTable(chosenCurrency+"/HKD",forexPrice[11]),
            new PriceTable(chosenCurrency+"/SEK",forexPrice[12]),
            new PriceTable(chosenCurrency+"/TRY",forexPrice[13]),
            new PriceTable(chosenCurrency+"/INR",forexPrice[14]),
            new PriceTable(chosenCurrency+"/RUB",forexPrice[15]),
            new PriceTable(chosenCurrency+"/NOK",forexPrice[16]),
            new PriceTable(chosenCurrency+"/BRL",forexPrice[17]),
            new PriceTable(chosenCurrency+"/ZAR",forexPrice[18]),
            new PriceTable(chosenCurrency+"/TWD",forexPrice[19]),
            new PriceTable(chosenCurrency+"/PLN",forexPrice[20]),
            new PriceTable(chosenCurrency+"/OTH",forexPrice[21])
            );
        
        return forexlist;
    
        
    }
           public static void selectedforexUSDCurrency(){
              chosenCurrency = "USD";
              oppositePair = "GBP";
        }
        
        public static void selectedforexGBPCurrency(){
              chosenCurrency = "GBP";
              oppositePair = "USD";
        }
    
    
    /*
    public double getPrice(){
        return super.getTradingItemPrice();
    }
    */


    
    
    
}

