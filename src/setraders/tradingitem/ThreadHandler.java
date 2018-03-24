package setraders.tradingitem;

import java.util.logging.Level;
import java.util.logging.Logger;
import setraders.ui.tradingaccount.TradingAccountController;


public class ThreadHandler{   
    public  Thread price_thread;

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
                    
                  
                    PriceSimulator ps = new PriceSimulator();
                    ps.randomGenCrypto();
                    ps.randomGenForex();
                    ps.randomGenStock();
                    
                    //TradingAccountController p1 = new TradingAccountController();
                    //p1.priceTable.refresh();
                    
                   
                }
                

            }
        };
    }
}
