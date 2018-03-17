/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import java.util.Random;
import javafx.scene.chart.XYChart;
import static setraders.ui.tradingaccount.TradingAccountController.series;
import static setraders.ui.tradingaccount.TradingAccountController.series1;
import static setraders.ui.tradingaccount.TradingAccountController.series2;




/**
 *
 * @author alton
 */
public class PriceSimulator
{
    static double cryptoPrice;
    static double forexPrice;
    static double stockPrice;
    static Random random = new Random();
    static int cryptoCount = 0;
    static int forexCount = 0;
    static int stockCount = 0;
    
    public static void randomGenCrypto()
    {
        cryptoPrice = 0 + (15000-4000) * random.nextDouble();
        series.getData().add(new XYChart.Data<>(cryptoCount+ "", cryptoPrice));
        cryptoCount++;
        
    }
    
    public static void randomGenForex()
    {
       forexPrice = 0 + (4-1) * random.nextDouble();
       System.out.println(forexPrice);
       series1.getData().add(new XYChart.Data<>(forexCount+ "", forexPrice));
       forexCount++;
    }
    
    public static void randomGenStock()
    {
       stockPrice = 0 + (500 - 100) * random.nextDouble();
       series2.getData().add(new XYChart.Data<>(stockCount+ "", stockPrice));
       stockCount++;

    }    
}


