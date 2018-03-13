/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tables;

import javafx.beans.property.SimpleStringProperty;
import setraders.ui.tradingaccount.TradingAccountController;
/**
 *
 * @author alton
 */
     public class Transaction {

        private final SimpleStringProperty transactionid;
        private final SimpleStringProperty company;
        private final SimpleStringProperty type;
        private final SimpleStringProperty margin;
        private final SimpleStringProperty time;

        public Transaction(String tid, String cid, String tyid, String mid, String datid) {
            this.transactionid = new SimpleStringProperty(tid);
            this.company = new SimpleStringProperty(cid);
            this.type = new SimpleStringProperty(tyid);
            this.margin = new SimpleStringProperty(mid);
            this.time = new SimpleStringProperty(datid);

        }

        public String getTransactionid() {
            return transactionid.get();
        }

        public String getCompany() {
            return company.get();
        }

        public String getType() {
            return type.get();
        }

        public String getMargin() {
            return margin.get();
        }
         public String getTime() {
            return time.get();
        }
    }
