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
            cryptoPrice[i] =  (0 + (15000-2000) * random.nextDouble());
        }
        
     
        ObservableList<PriceTable>  cryptolist = FXCollections.observableArrayList(
            new PriceTable("Bitcoin (BTC)",cryptoPrice[0]),
            new PriceTable("Ether (ETH)",cryptoPrice[1]),
            new PriceTable("Ripple (XRP)",cryptoPrice[2]),
            new PriceTable("Bitcoin Cash (BCH)",cryptoPrice[3]),
            new PriceTable("Litecoin (LTC)",cryptoPrice[4]),
            new PriceTable("NEO (NEO)",cryptoPrice[5]),
            new PriceTable("Cardano (ADA)",cryptoPrice[6]),
            new PriceTable("Stellar (XLM)",cryptoPrice[7]),
            new PriceTable("EOS (EOS)",cryptoPrice[8]),
            new PriceTable("Monero (XMR)",cryptoPrice[9]),
            new PriceTable("Dash (DASH)",cryptoPrice[10]),
            new PriceTable("IOTA (MIOTA)",cryptoPrice[11]),
            new PriceTable("NEM (XEM)",cryptoPrice[12]),
            new PriceTable("Tether (USDT)",cryptoPrice[13]),
            new PriceTable("VeChain (VEN)",cryptoPrice[14]),
            new PriceTable("TRON (TRX)",cryptoPrice[15]),
            new PriceTable("Ether Classic (ETC)",cryptoPrice[16]),
            new PriceTable("Lisk (LSK)",cryptoPrice[17]),
            new PriceTable("Nano (NANO)",cryptoPrice[18]),
            new PriceTable("OmiseGo (OMG)",cryptoPrice[19]),
            new PriceTable("Bitcoin Gold (BTG)",cryptoPrice[20]),
            new PriceTable("Qtum (QTUM)",cryptoPrice[21])
            );
        
        return cryptolist;
    
        
    }
}
