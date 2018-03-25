
package setraders.data.wrapper;


/**
 *
 * @author Josh Da Silva
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