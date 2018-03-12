/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.tradingitem;

import java.util.Random;


/**
 *
 * @author alton
 */
public class PriceSimulator
{
    static double[] cryptoPrice;
    static double forexPrice;
    static double stockPrice;
    static Random random = new Random();

    public static void randomGenCrypto()
    {
        cryptoPrice[10] = 0 + (15000-4000) * random.nextDouble();
        
    }
    
    public static void randomGenForex()
    {
        forexPrice = 0 + (4-1) * random.nextDouble();
       
    }
    
    public static void randomGenStock()
    {
        stockPrice = 0 + (500 - 100) * random.nextDouble();
        
    }    
}


