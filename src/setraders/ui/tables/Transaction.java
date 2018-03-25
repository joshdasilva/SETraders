/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tables;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author alton
 */
     public class Transaction {

        private final SimpleIntegerProperty transactionid;
        private final SimpleStringProperty item;
        private final SimpleStringProperty type;
        private final SimpleDoubleProperty amount;
        private final SimpleStringProperty time;
        private final SimpleDoubleProperty openprice;
        private final SimpleDoubleProperty closeprice;

        public Transaction(int tid, String iid, String tyid, double aid, String datid, double op, double cp) {
            this.transactionid = new SimpleIntegerProperty(tid);
            this.item = new SimpleStringProperty(iid);
            this.type = new SimpleStringProperty(tyid);
            this.amount = new SimpleDoubleProperty(aid);
            this.time = new SimpleStringProperty(datid);
            this.openprice = new SimpleDoubleProperty(op);
            this.closeprice = new SimpleDoubleProperty(cp);
            

        }

        public int getTransactionid() {
            return transactionid.get();
        }

        public String getItem() {
            return item.get();
        }

        public String getType() {
            return type.get();
        }

        public double getAmount() {
            return amount.get();
        }
        public String getTime() {
            return time.get();
        }
        public double getOpenprice() {
            return openprice.get();
        }
        public double getCloseprice() {
            return closeprice.get();
        }
    }
