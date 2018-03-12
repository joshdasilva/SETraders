/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

/**
 *
 * @author alton
 */
public class Forex extends TradingItem
{
    private final String currencyPair;
    private double price;
    
    public Forex(String initName, String c)
    {
        price = super.getTradingItemPrice();
        currencyPair = initName;
    }
    
    public String getCurrencyPair()
    {
        return currencyPair;
    }
    
    /*
    public double getPrice(){
        return super.getTradingItemPrice();
    }
    */
    
}

