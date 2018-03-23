/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tables;

import javafx.beans.property.SimpleStringProperty;
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
        private final SimpleStringProperty price;
        private final SimpleStringProperty closeprice;

        public Transaction(String tid, String cid, String tyid, String mid, String datid, String pid, String pic) {
            this.transactionid = new SimpleStringProperty(tid);
            this.company = new SimpleStringProperty(cid);
            this.type = new SimpleStringProperty(tyid);
            this.margin = new SimpleStringProperty(mid);
            this.time = new SimpleStringProperty(datid);
            this.price = new SimpleStringProperty(pid);
            this.closeprice = new SimpleStringProperty(pic);
            

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
        public String getPrice() {
            return price.get();
        }
        public String getCloseprice() {
            return closeprice.get();
        }
    }
