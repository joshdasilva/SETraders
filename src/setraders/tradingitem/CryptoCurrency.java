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
public class CryptoCurrency extends TradingItem
{
    private final String cryptoName;
    private double price;
    public CryptoCurrency(String initName)
    {
        price = super.getTradingItemPrice();
        cryptoName = initName;
    }
     
    public String getCryptoName(){
        return cryptoName;
    }    
}
