/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import setraders.ui.tables.Transaction;



/**
 *
 * @author Josh Da Silva
 */
public final class DatabaseHandler {
    private static DatabaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;
 
    
    static {
           createConnection();
           inflateDB();
  
    } 


      private DatabaseHandler() {
      createConnection();
      inflateDB();
      //dbadmin();
      //dbadmin1();
      //dbadmin1();
      //createtable();
    }
    

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }
     //UPDATE BAL SET balance=? WHERE accountid=?
    //INSERT INTO BAL (accountid, balance)\n" +
//"VALUES ('1000', 'user1')
     
    
        public boolean dbadmin2() {
        try {
            String updatebalance = "CREATE TABLE TRANS (transactionid int, item VARCHAR(20),type VARCHAR(20),amount DOUBLE, time VARCHAR(30),openprice DOUBLE,closeprice DOUBLE)";
            PreparedStatement stmt = conn.prepareStatement(updatebalance);
           System.out.println("administration done");
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
            
            
            
        public boolean dbadmin1() {
        try {
            String updatebalance = "INSERT INTO TRANS (transactionid,item,type,amount,time,openprice,closeprice) VALUES(1,'Apple','Buy',1000,'21:39 19/03/2018',1000,0)";
            PreparedStatement stmt = conn.prepareStatement(updatebalance);
           System.out.println("administration done");
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        
        public boolean dbadmin() {
        try {
            String updatebalance = "DELETE FROM TRANS";
            PreparedStatement stmt = conn.prepareStatement(updatebalance);
           System.out.println("administration done");
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        

    
    
     void createtable(){
        String TABLE_NAME = "BAL";
        try{ System.out.println("this worked");
            stmt = conn.createStatement();
            
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(),null);
            
            if (tables.next()){
                System.out.println("Table "+ TABLE_NAME + "already exists.Ready to go!");
                
            }else {
                stmt.execute("CREATE TABLE " +TABLE_NAME+ "("
                +"      accountid varchar(200) primary key, \n"
                +"      balance DECIMAL(20,2)"
                +" )");
                
            }
        } catch (SQLException e ){
            System.err.println(e.getMessage()+ ".....setupDatabase");
        
        } finally {
            
        }
        
    }
    
private static void inflateDB() {
        List<String> tableData = new ArrayList<>();
        try {
            Set<String> loadedTables = getDBTables();
            System.out.println("Already loaded tables " + loadedTables);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(DatabaseHandler.class.getClass().getResourceAsStream("/resources/database/tables.xml"));
            NodeList nList = doc.getElementsByTagName("table-entry");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element entry = (Element) nNode;
                String tableName = entry.getAttribute("name");
                String query = entry.getAttribute("col-data");
                if (!loadedTables.contains(tableName.toLowerCase())) {
                    tableData.add(String.format("CREATE TABLE %s (%s)", tableName, query));
                }
            }
            if (tableData.isEmpty()) {
                System.out.println("Tables are already loaded");
            } else {
                System.out.println("Inflating new tables.");
                createTables(tableData);
            }
        } catch (Exception ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static Set<String> getDBTables() throws SQLException {
        Set<String> set = new HashSet<>();
        DatabaseMetaData dbmeta = conn.getMetaData();
        readDBTable(set, dbmeta, "TABLE", null);
        return set;
    }

    private static void readDBTable(Set<String> set, DatabaseMetaData dbmeta, String searchCriteria, String schema) throws SQLException {
        ResultSet rs = dbmeta.getTables(null, schema, null, new String[]{searchCriteria});
        while (rs.next()) {
            set.add(rs.getString("TABLE_NAME").toLowerCase());
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }
    
        public static void main(String[] args) throws Exception {
        DatabaseHandler.getInstance();
    }
    private static void createTables(List<String> tableData) throws SQLException {
        Statement statement = conn.createStatement();
        statement.closeOnCompletion();
        for (String command : tableData) {
            System.out.println(command);
            statement.addBatch(command);
        }
        statement.executeBatch();
    }
        public boolean updateTransaction(Transaction transaction) {
        try {
            String update = "UPDATE TRANS SET COMPANY=?, TYPE=?, MARGIN=?,TIME=? WHERE TRANSACTIONID=?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setInt(1, transaction.getTransactionid());
            stmt.setString(2, transaction.getItem());
            stmt.setString(3, transaction.getType());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setString(5,transaction.getTime());
            stmt.setDouble(6,transaction.getOpenprice());
            stmt.setDouble(7,transaction.getCloseprice());
            int res = stmt.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Connection getConnection() {
        return conn;
    }

    
}
