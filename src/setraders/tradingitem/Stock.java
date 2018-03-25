/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static setraders.tradingitem.Forex.random;
import setraders.ui.tables.PriceTable;

/**
 *
 * @author alton
 */
public class Stock extends TradingItem
{
    private final String company;
    private double price;
    private static ObservableList stocklist;
    static Random random = new Random();
    
    public Stock(String initName)
    {
        price = super.getTradingItemPrice();
        company = initName;
    }
    
    public String getCompany(){
        return company;
    }
    
     public static ObservableList getStockList()
    {      
           double [] stockPrice = new double [22];
        
        for(int i = 0; i < stockPrice.length; i++ ){
            stockPrice[i] = Math.round((0 + (500 - 200) * random.nextDouble())*100)/100.0;
        }
        
     
        ObservableList<PriceTable>  stocklist = FXCollections.observableArrayList(
           
            new PriceTable("Apple",stockPrice[0]),
            new PriceTable("Alphabet",stockPrice[1]),
            new PriceTable("Berkshire Hathaway",stockPrice[2]),
            new PriceTable("Facebook",stockPrice[3]),
            new PriceTable("AT&T",stockPrice[4]),
            new PriceTable("Berkshire Hathaway",stockPrice[5]),
            new PriceTable("JPMorgan Chase",stockPrice[6]),
            new PriceTable("Bank of America",stockPrice[7]),
            new PriceTable("Samsung Electroincs",stockPrice[8]),
            new PriceTable("Visa",stockPrice[9]),
            new PriceTable("Coca-Cola",stockPrice[10]),
            new PriceTable("Oracle",stockPrice[11]),
            new PriceTable("IBM",stockPrice[12]),
            new PriceTable("Tesla",stockPrice[13]),
            new PriceTable("Bose",stockPrice[14]),
            new PriceTable("AMD",stockPrice[15]),
            new PriceTable("Microsoft",stockPrice[16]),
            new PriceTable("Intel",stockPrice[17]),
            new PriceTable("Tesco",stockPrice[18]),
            new PriceTable("Berkshire Hathaway",stockPrice[19]),
            new PriceTable("Amazon",stockPrice[20]),
            new PriceTable("Spotify",stockPrice[21])
            );
        return stocklist;
    }
    
    
}
