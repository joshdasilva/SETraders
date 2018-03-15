/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.data.wrapper;


/**
 *
 * @author alton
 */
 public class Balance {

        String accountid;
        double balance;



        public Balance(String id, double bal) {
            this.accountid = id;
            this.balance = bal;

        }

        public String getAccountid() {
            return accountid;
        }
        
        public void setAccountid(String id) {
             this.accountid = id;
        }

        public double getBalance() {
            return balance;
        }
        
        public void setBalance(double bal) {
             this.balance = bal;
        }
  
    }

