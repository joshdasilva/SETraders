/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import java.util.Random;
import javafx.scene.chart.XYChart;
import static setraders.ui.tradingaccount.TradingAccountController.series;





/**
 *
 * @author alton
 */
public class PriceSimulator
{

    double stockPrice;
    double cryptoPrice;
    double forexPrice;
   
     Random random = new Random();
     int cryptoCount = 0;
     
    
    public  void randomGenCrypto()
    {    
        cryptoPrice = 0 + (15000-4000) * random.nextDouble();
        
        
        
        
        
        
        //series.getData().add(new XYChart.Data<>(cryptoCount+ "", cryptoPrice));
        //cryptoCount++;
        
        

        
    }
    
    public void randomGenForex()
    {
      //forexPrice = 0 + (4-1) * random.nextDouble();
       //series1.getData().add(new XYChart.Data<>(forexCount+ "", forexPrice));
       //forexCount++;
                forexPrice = 0 + (4-1) * random.nextDouble();
       
      
    }
    
    public void randomGenStock()
    {
      stockPrice = 0 + (500 - 200) * random.nextDouble();  
        
    }
}

       

        



