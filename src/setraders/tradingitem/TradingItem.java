/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

/**
 *
 * @author Josh Da Silva
 */
public abstract class TradingItem
{
 
    private double itemPrice;

    public TradingItem()
    {
        this.itemPrice = 0;
    }

    public double getTradingItemPrice(){
        return itemPrice;
    }
    
    public void setTradingPrice(double price){
        itemPrice = price;
    }
    
    
    
}
