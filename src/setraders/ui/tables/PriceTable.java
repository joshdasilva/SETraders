/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tables;

import  setraders.ui.tradingaccount.TradingAccountController;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alton
 */
        public class PriceTable {

        private final SimpleStringProperty companycfdCol;
        private final SimpleStringProperty pricecfdCol;
        private final SimpleStringProperty changecfdCol;


        public PriceTable(String rcc, String rpc, String rgc) {
            this.companycfdCol = new SimpleStringProperty(rcc);
            this.pricecfdCol = new SimpleStringProperty(rpc);
            this.changecfdCol = new SimpleStringProperty(rgc);
        }

        public String getCompanycfdCol() {
            return companycfdCol.get();
        }
        
        public void setCompanycfdCol(String v) {
             companycfdCol.set(v);
        }

        public String getPricecfdCol() {
            return pricecfdCol.get();
        }
        
        public void setPricecfdCol(String v) {
             pricecfdCol.set(v);
        }

        public String getChangecfdCol() {
            return changecfdCol.get();
        }
                
        public void setChangecfdCol(String v) {
             changecfdCol.set(v);
        }
  
    }
