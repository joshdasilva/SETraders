/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static setraders.tradingitem.Forex.random;
import setraders.ui.tables.PriceTable;

/**
 *
 * @author alton
 */
public class CryptoCurrency extends TradingItem
{

    public static ObservableList<PriceTable> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private final String cryptoName;
    private double price;
    public static  String chosenCurrency;
 
    
    public CryptoCurrency(String initName)
    {
        price = super.getTradingItemPrice();
        cryptoName = initName;
    }
     
    public String getCryptoName(){
        return cryptoName;
    }   
    
        public static ObservableList getCryptoList()
    { 
         double [] cryptoPrice = new double [22];
        
        for(int i = 0; i < cryptoPrice.length; i++ ){
            cryptoPrice[i] = Math.round((0 + (15000 - 4000) * random.nextDouble())*100)/100.0;
        }
        
   
        System.out.println(chosenCurrency);
        ObservableList<PriceTable>  cryptolist = FXCollections.observableArrayList(
            new PriceTable(chosenCurrency +"/Bitcoin (BTC)",cryptoPrice[0]),
            new PriceTable(chosenCurrency +"/Ether (ETH)",cryptoPrice[1]),
            new PriceTable(chosenCurrency +"/Ripple (XRP)",cryptoPrice[2]),
            new PriceTable(chosenCurrency +"/Bitcoin Cash (BCH)",cryptoPrice[3]),
            new PriceTable(chosenCurrency +"/Litecoin (LTC)",cryptoPrice[4]),
            new PriceTable(chosenCurrency +"/NEO (NEO)",cryptoPrice[5]),
            new PriceTable(chosenCurrency +"/Cardano (ADA)",cryptoPrice[6]),
            new PriceTable(chosenCurrency +"/Stellar (XLM)",cryptoPrice[7]),
            new PriceTable(chosenCurrency +"/EOS (EOS)",cryptoPrice[8]),
            new PriceTable(chosenCurrency +"/Monero (XMR)",cryptoPrice[9]),
            new PriceTable(chosenCurrency +"/Dash (DASH)",cryptoPrice[10]),
            new PriceTable(chosenCurrency +"/IOTA (MIOTA)",cryptoPrice[11]),
            new PriceTable(chosenCurrency +"/NEM (XEM)",cryptoPrice[12]),
            new PriceTable(chosenCurrency +"/Tether (USDT)",cryptoPrice[13]),
            new PriceTable(chosenCurrency +"/VeChain (VEN)",cryptoPrice[14]),
            new PriceTable(chosenCurrency +"/TRON (TRX)",cryptoPrice[15]),
            new PriceTable(chosenCurrency +"/Ether Classic (ETC)",cryptoPrice[16]),
            new PriceTable(chosenCurrency +"/Lisk (LSK)",cryptoPrice[17]),
            new PriceTable(chosenCurrency +"/Nano (NANO)",cryptoPrice[18]),
            new PriceTable(chosenCurrency +"/OmiseGo (OMG)",cryptoPrice[19]),
            new PriceTable(chosenCurrency +"/Bitcoin Gold (BTG)",cryptoPrice[20]),
            new PriceTable(chosenCurrency +"/Qtum (QTUM)",cryptoPrice[21])
            );
        
        return cryptolist;
    
        
    }
        
        public static void selectedcryptoUSDCurrency(){
              chosenCurrency = "USD";
              
        }
        
        public static void selectedcryptoGBPCurrency(){
              chosenCurrency = "GBP";
           
        }
        
        
        
        
}
