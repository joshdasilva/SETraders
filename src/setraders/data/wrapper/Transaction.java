package setraders.data.wrapper;

/**
 *
 * @author Josh Da Silva
 */
public class Transaction {
 int transactionid;
 String item;
 String type;
 double amount;
 String time;
 double openprice;
 double closeprice;

 public Transaction(int transactionid, String item, String type, double amount, String time, double openprice, double closeprice) {
  this.transactionid = transactionid;
  this.item = item;
  this.type = type;
  this.amount = amount;
  this.time = time;
  this.openprice = openprice;
  this.closeprice = closeprice;
 }

 public int getTransactionid() {
  return transactionid;
 }

 public void setTransactionid(int transactionid) {
  this.transactionid = transactionid;
 }

 public String getItem() {
  return item;
 }

 public void setItem(String item) {
  this.item = item;
 }

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }

 public double getAmount() {
  return amount;
 }

 public void setAmount(double amount) {
  this.amount = amount;
 }

 public String getTime() {
  return time;
 }

 public void setTime(String time) {
  this.time = time;
 }

 public double getOpenprice() {
  return openprice;
 }

 public void setOpenprice(double openprice) {
  this.openprice = openprice;
 }

 public double getCloseprice() {
  return closeprice;
 }

 public void setCloseprice(double closeprice) {
  this.closeprice = closeprice;
 }


}