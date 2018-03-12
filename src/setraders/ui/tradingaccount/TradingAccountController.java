/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tradingaccount;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import setraders.alert.AlertMaker; //delete these
import setraders.database.DataHelper;
import setraders.database.DatabaseHandler;
import setraders.ui.client.ClientController;

/**
 *
 * @author Josh Da Silva
 */
public class TradingAccountController implements Initializable {
    
    @FXML
    private LineChart<String, Number> lineChart;
    XYChart.Series<String, Number> series, series1, series2, series3;
    
    //listboxes
    @FXML
    private JFXListView twitterListView;

    @FXML 
    private JFXListView companyListView;
   
    //transaction table
    @FXML
    private TableView<Transaction> tableView;
    
    @FXML
    private TableColumn<Transaction, String> companyCol;

    @FXML
    private TableColumn<Transaction, String> typeCol;
    
    @FXML
    private TableColumn<Transaction, String> timeCol;

    @FXML
    private TableColumn<Transaction, String> transactionidCol;
    
    @FXML
    private TableColumn<Transaction, String> marginCol;
    
    // delete after making db
    @FXML
    private AnchorPane mainContainer;
    
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
    @FXML
    private JFXTextField timeid;
    @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;

    @FXML
    private StackPane rootPane;
    private Boolean isInEditMode = Boolean.FALSE;

    // end here
    
    DatabaseHandler databaseHandler;
    
    private  double xOffset = 0;
    private  double yOffset = 0;
    
    private ObservableList<String> forexList = FXCollections.observableArrayList("GBP/EUR", "GBP/USD", "GBP/SGD", "GBP/INR", "GBP/HKD", "GBP/JPY");
    private ObservableList<String> cryptoList = FXCollections.observableArrayList("btc", "eth", "xrp", "neo", "eos", "ltc");
    private ObservableList<String> companyList = FXCollections.observableArrayList("Apple", "Alphabet","Berkshire Hathaway","Facebook","AT&T","JPMorgan Chase","Bank of America","Samsung Electroincs","Visa","Coca-Cola","Oracle","IBM", "Tesla", "Bose", "AMD", "Intel", "Tesco", "Netflix","Amazon","Spotify", "Microsoft");
    ObservableList<Transaction> list = FXCollections.observableArrayList();
    
    @FXML
    private void handleClose(MouseEvent event){
    System.exit(0);
    }
   
    @FXML
    private void handleMin(MouseEvent event){
    ClientController.getStageObj().setIconified(true);
    }
   
    @FXML
    private void handleDrag(MouseEvent event) {
            
    ClientController.getStageObj().setX(event.getScreenX()- xOffset);
    ClientController.getStageObj().setY(event.getScreenY() -yOffset);
        
    }
    
    @FXML
    private void handleDragValue(MouseEvent event) {
        
          xOffset = event.getSceneX();
          yOffset = event.getSceneY();
        
    }
    
    @FXML
    private void handleDepositButtonAction(ActionEvent event){
        
    }
    
    @FXML
    private void handleWithdrawButtonAction(ActionEvent event){
        
    }
    
    @FXML
    private void handleBuyCFDButtonAction(ActionEvent event){
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();
        String bookTime = timeid.getText();


        if (isInEditMode) {
            handleEditOperation();
            return;
        }

        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(bookID, bookName, bookAuthor, bookPublisher, bookTime);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", bookName + " has been added");
            clearEntries();
            refresh();
        } else {
            AlertMaker.showMaterialDialog( rootPane, mainContainer, new ArrayList<>(), "Failed to create new transaction", "Check all the entries and try again");
        }
    }
    

    public void inflateUI(TradingAccountController.Transaction transaction) {
        title.setText(transaction.getTransactionid());
        id.setText(transaction.getCompany());
        author.setText(transaction.getType());
        publisher.setText(transaction.getMargin());
        timeid.setText(transaction.getTime());
        id.setEditable(false);
        isInEditMode = Boolean.TRUE;
      
    }

    private void clearEntries() {
        title.clear();
        id.clear();
        author.clear();
        publisher.clear();
        timeid.clear();
    }

    private void handleEditOperation() {
        TradingAccountController.Transaction transaction = new TradingAccountController.Transaction(title.getText(), id.getText(), author.getText(), publisher.getText(), timeid.getText());
        if (databaseHandler.updateTransaction(transaction)) {
            AlertMaker.showMaterialDialog( rootPane, mainContainer, new ArrayList<>(), "Success", "Update complete");
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed", "Could not update data");
        }
    }
    
    @FXML
    private void handleShortCFDButtonAction(ActionEvent event){
        
    }
    
    @FXML
    private void handleLogoutButtonAction(ActionEvent event){
        
    }
    
    @FXML
    private void handleNotiToggleButtonAction(ActionEvent event){
        
    }

/*    
    @FXML
    private void handleFAQButtonAction(ActionEvent event){
        
    }
    
    @FXML
    private void handleFAQButtonAction(ActionEvent event){
        
    }
*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = DatabaseHandler.getInstance(); 
       
        initcol();
        loadTransactionlist();
       

       
   /*  databaseHandler = new DatabaseHandler();
       checkData();
       leftListView.getItems().clear();
        leftListView.setItems(obList);
        series = new XYChart.Series<>();
        series.setName("Apple");
        lineChart.getData().add(series); */
    
    }
        private void checkData() {
        String qu = "SELECT company FROM TRANS";
        ResultSet rs = databaseHandler.execQuery(qu);
        try {
            while (rs.next()) {
                String titlex = rs.getString("company");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void initcol(){
        transactionidCol.setCellValueFactory(new PropertyValueFactory<>("transactionid"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        marginCol.setCellValueFactory(new PropertyValueFactory<>("margin"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
    }
    
    private void refresh(){
        loadTransactionlist();
    }
    
    private void loadTransactionlist(){

        companyListView.getItems().clear();
        companyListView.setItems(companyList);
        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        list.clear();
        String qu = "SELECT * FROM TRANS";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String transidx = rs.getString("transactionid");
                String companyx = rs.getString("company");
                String typex = rs.getString("type");
                String marginx = rs.getString("margin");
                String dateandtimex = rs.getString("time");
                System.out.println(transidx);
                list.add(new Transaction(transidx, companyx, typex, marginx, dateandtimex));

            }
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }
    
     public static class Transaction {

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

    
}
