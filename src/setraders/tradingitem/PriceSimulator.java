/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import setraders.ui.tradingaccount.TradingAccountController;
import setraders.ui.tables.PriceTable;
import static setraders.ui.tradingaccount.TradingAccountController.series;
import static setraders.ui.tradingaccount.TradingAccountController.series1;
import static setraders.ui.tradingaccount.TradingAccountController.series2;




/**
 *
 * @author alton
 */
public class PriceSimulator
{

    public static ObservableList<PriceTable> cryptoList;
   
     Random random = new Random();
     int cryptoCount = 0;
     
    
    public  void randomGenCrypto()
    {    
        double [] cryptoPrice = new double [22];
        
        for(int i = 0; i < cryptoPrice.length; i++ ){
            cryptoPrice[i] =  (0 + (15000-2000) * random.nextDouble());
           // System.out.println(cryptoPrice[i]);
        //series.getData().add(new XYChart.Data<>(cryptoCount+ "", cryptoPrice[i]));
        //cryptoCount++;
        }
        
        //TradingAccountController.fxlist[i] = (int) cryptoPrice[i];
     
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
        
        TradingAccountController.cList = cryptolist;
        
    }
    
    public void randomGenForex()
    {
      //forexPrice = 0 + (4-1) * random.nextDouble();
       //series1.getData().add(new XYChart.Data<>(forexCount+ "", forexPrice));
       //forexCount++;
       
      
    }
    
    public void randomGenStock()
    {
      
       //series2.getData().add(new XYChart.Data<>(stockCount+ "", stockPrice));
       //stockCount++;
  
      int [] stockPrice = new int [22];
        
        for(int i = 0; i < stockPrice.length; i++ ){
         stockPrice[i] =  (0 + (500 - 100) * random.nextInt());
        
        //TradingAccountController.fxlist[i] = (int) cryptoPrice[i];
        
        ObservableList<PriceTable> cryptolist = FXCollections.observableArrayList(
        new PriceTable("Bitcoin (BTC)",stockPrice[0]),
            new PriceTable("Ether (ETH)",stockPrice[1]),
            new PriceTable("Ripple (XRP)",stockPrice[2]),
            new PriceTable("Bitcoin Cash (BCH)",stockPrice[3]),
            new PriceTable("Litecoin (LTC)",stockPrice[4]),
            new PriceTable("NEO (NEO)",stockPrice[5]),
            new PriceTable("Cardano (ADA)",stockPrice[6]),
            new PriceTable("Stellar (XLM)",stockPrice[7]),
            new PriceTable("EOS (EOS)",stockPrice[8]),
            new PriceTable("Monero (XMR)",stockPrice[9]),
            new PriceTable("Dash (DASH)",stockPrice[10]),
            new PriceTable("IOTA (MIOTA)",stockPrice[11]),
            new PriceTable("NEM (XEM)",stockPrice[12]),
            new PriceTable("Tether (USDT)",stockPrice[13]),
            new PriceTable("VeChain (VEN)",stockPrice[14]),
            new PriceTable("TRON (TRX)",stockPrice[15]),
            new PriceTable("Ether Classic (ETC)",stockPrice[16]),
            new PriceTable("Lisk (LSK)",stockPrice[17]),
            new PriceTable("Nano (NANO)",stockPrice[18]),
            new PriceTable("OmiseGo (OMG)",stockPrice[19]),
            new PriceTable("Bitcoin Gold (BTG)",stockPrice[20]),
            new PriceTable("Qtum (QTUM)",stockPrice[21])
            );
        TradingAccountController.cList = cryptolist;
        //TradingAccountController.priceTable.refresh();
        
        
        
       // series.getData().add(new XYChart.Data<>(cryptoCount+ "", cryptoPrice[i]));
        //cryptoCount++; */
        }
    }
}

       

        



