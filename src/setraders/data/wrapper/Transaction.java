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
public class Transaction {
    String transactionid;
    String company;
    String type;
    String margin;
    String time;
    String price;
    String closeprice;

    public Transaction(String transactionid, String company, String type, String margin, String time, String price, String closeprice) {
        this.transactionid = transactionid;
        this.company = company;
        this.type = type;
        this.margin = margin;
        this.time = time;
        this.price = price;
        this.closeprice = closeprice;
    }
    
 public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getCloseprice() {
        return closeprice;
    }

    public void setCloseprice(String closeprice) {
        this.closeprice = closeprice;
    }
    
    
}
