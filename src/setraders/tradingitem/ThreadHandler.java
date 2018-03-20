package setraders.tradingitem;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ThreadHandler{   
    public static Thread price_thread;

    public ThreadHandler() {
        
        this.price_thread = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    PriceSimulator.randomGenCrypto();
                    PriceSimulator.randomGenForex();
                    PriceSimulator.randomGenStock();
                }
                

            }
        };
    }
}
