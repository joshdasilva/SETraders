/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tables;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import  setraders.ui.tradingaccount.TradingAccountController;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author alton
 */
        public class PriceTable {

        private final SimpleStringProperty companycfdCol;
        private final SimpleDoubleProperty pricecfdCol;
 


        public PriceTable(String a, double b) {
            this.companycfdCol = new SimpleStringProperty(a);
            this.pricecfdCol = new SimpleDoubleProperty(b);
        
        }

        public String getCompanycfdCol() {
            return companycfdCol.get();
        }
        
        public void setCompanycfdCol(String v) {
             companycfdCol.set(v);
        }

        public double getPricecfdCol() {
            return pricecfdCol.get();
        }
        
        public void setPricecfdCol(double v) {
             pricecfdCol.set(v);
        }
  
    }
