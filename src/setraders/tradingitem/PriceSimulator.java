/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import java.util.Random;
import javafx.scene.chart.XYChart;
import setraders.ui.tradingaccount.TradingAccountController;
import static setraders.ui.tradingaccount.TradingAccountController.series;
import static setraders.ui.tradingaccount.TradingAccountController.series1;
import static setraders.ui.tradingaccount.TradingAccountController.series2;




/**
 *
 * @author alton
 */
public class PriceSimulator
{
    public static int[] cryptoPrice;
    static double forexPrice;
    static double stockPrice;
    static Random random = new Random();
    static int cryptoCount = 0;
    static int forexCount = 0;
    static int stockCount = 0;
    
    public static void randomGenCrypto()
    {    
        int [] cryptoPrice = new int [22];
        
        for(int i = 0; i < cryptoPrice.length; i++ ){
        cryptoPrice[i] = (int) (0.1 + (15000-4000) * random.nextInt());
        TradingAccountController.stocksArray[i] = (int) cryptoPrice[i];
       // System.out.println(TradingAccountController.stocksArray[i]);
      
        //series.getData().add(new XYChart.Data<>(cryptoCount+ "", cryptoPrice[i]));
        //cryptoCount++;
        }

        
    }
    
    public static void randomGenForex()
    {
      forexPrice = 0 + (4-1) * random.nextDouble();
       //series1.getData().add(new XYChart.Data<>(forexCount+ "", forexPrice));
       //forexCount++;
    }
    
    public static void randomGenStock()
    {
      stockPrice = 0 + (500 - 100) * random.nextDouble();
       //series2.getData().add(new XYChart.Data<>(stockCount+ "", stockPrice));
       //stockCount++;

    }    
}


