package setraders.tradingitem;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ThreadHandler{   
    public static Thread cryptoPrice;
    public static Thread forexPrice;
    public static Thread stockPrice;

    public ThreadHandler() {
        
        this.cryptoPrice = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PriceSimulator.randomGenCrypto();

                }
                

            }
        };
        
        this.forexPrice = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PriceSimulator.randomGenForex();
                }
            }
        };
        
        this.stockPrice = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PriceSimulator.randomGenStock();
                }
            }
        };
    }
}
