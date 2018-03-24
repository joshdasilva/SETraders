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
            forexPrice[i] =  (0 + (15000-2000) * random.nextDouble());
        }
        
     
        ObservableList<PriceTable>  forexlist = FXCollections.observableArrayList(
            new PriceTable("USD/EUR ",forexPrice[0]),
            new PriceTable("USD/JPY",forexPrice[1]),
            new PriceTable("USD/GBP",forexPrice[2]),
            new PriceTable("USD/AUD",forexPrice[3]),
            new PriceTable("USD/CAD",forexPrice[4]),
            new PriceTable("USD/CNY",forexPrice[5]),
            new PriceTable("USD/CHF",forexPrice[6]),
            new PriceTable("USD/MXN",forexPrice[7]),
            new PriceTable("USD/SGD",forexPrice[8]),
            new PriceTable("USD/KRW",forexPrice[9]),
            new PriceTable("USD/NZD",forexPrice[10]),
            new PriceTable("USD/HKD",forexPrice[11]),
            new PriceTable("USD/SEK",forexPrice[12]),
            new PriceTable("USD/TRY",forexPrice[13]),
            new PriceTable("USD/INR",forexPrice[14]),
            new PriceTable("USD/RUB",forexPrice[15]),
            new PriceTable("USD/NOK",forexPrice[16]),
            new PriceTable("USD/BRL",forexPrice[17]),
            new PriceTable("USD/ZAR",forexPrice[18]),
            new PriceTable("USD/TWD",forexPrice[19]),
            new PriceTable("USD/PLN",forexPrice[20]),
            new PriceTable("USD/OTH",forexPrice[21])
            );
        
        return forexlist;
    
        
    }
    
    
    /*
    public double getPrice(){
        return super.getTradingItemPrice();
    }
    */


    
    
    
}

