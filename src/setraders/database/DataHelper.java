/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import setraders.data.wrapper.Transaction;
import setraders.data.wrapper.Balance;



/**
 *
 * @author alton
 */
public class DataHelper {
     public static boolean insertNewTransaction(Transaction transaction) {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO TRANS(transactionid,company,type,margin,time,price,closeprice) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, transaction.getTransactionid());
            statement.setString(2, transaction.getCompany());
            statement.setString(3, transaction.getType());
            statement.setString(4, transaction.getMargin());
            statement.setString(5, transaction.getTime());
            statement.setString(6,transaction.getPrice());
            statement.setString(7,transaction.getCloseprice());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
            public static boolean updateBalanceminus(Balance balance) {
        try {
           PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
                  "UPDATE bal SET balance = balance - ?");
            
            statement.setDouble(1, balance.getBalance());
            //statement.setString(2,balance.getAccountid());
            return statement.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
        public static boolean updateBalanceplus(Balance balance) {
        try {
           PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement( 
                  "UPDATE bal SET balance = balance + ?");
            
            statement.setDouble(1, balance.getBalance());
            //statement.setString(2,balance.getAccountid());
            return statement.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DataHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     
  }
