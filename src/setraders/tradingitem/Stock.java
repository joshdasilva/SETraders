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
public class Stock extends TradingItem
{
    private final String company;
    private double price;
    
    public Stock(String initName)
    {
        price = super.getTradingItemPrice();
        company = initName;
    }
    
    public String getCompany(){
        return company;
    }
    
}
