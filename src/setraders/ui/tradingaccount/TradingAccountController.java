/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tradingaccount;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import setraders.alert.AlertMaker; 
import setraders.database.DataHelper;
import setraders.database.DatabaseHandler;
import setraders.ui.tables.PriceTable;
import setraders.ui.tables.Transaction;
import setraders.util.SetradersUtility;
import setraders.tradingitem.ThreadHandler;



public class TradingAccountController implements Initializable {
    
    //Radio Boxes
    @FXML
    private JFXRadioButton tradeCFD;
    @FXML
    private JFXRadioButton tradeEquities;
    
    //line chart
    @FXML
    private  LineChart<String, Number> lineChart;
    public static XYChart.Series<String, Number> series, series1, series2;
    
    
    //listbox
    @FXML
    private JFXListView twitterListView;
   
    
    //Equity transaction table start
    @FXML
    private TableView<Transaction> tableView;
    @FXML
    private TableColumn<Transaction, String> companyCol;
    @FXML
    private TableColumn<Transaction, String> typeCol;
    @FXML
    private TableColumn<Transaction, String> timeCol;
    @FXML
    private TableColumn<Transaction, Number> priceCol;
    @FXML
    private TableColumn<Transaction, Number> closepriceCol;
    @FXML
    private TableColumn<Transaction, String> transactionidCol;
    @FXML
    private TableColumn<Transaction, String> marginCol;
    //transaction table end
    
    
    //price table start
    @FXML
    public  TableView<PriceTable> priceTable;   
    @FXML
    private TableColumn<PriceTable, String> companycfdCol;
    @FXML
    private TableColumn<PriceTable, Number> pricecfdCol;
    //@FXML
    //private TableColumn<PriceTable, String> changecfdCol;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private AnchorPane windowPane;
    @FXML
    JFXComboBox tradingitemDrop;
    @FXML
    private AnchorPane mainRootPane;
    @FXML
    private Pane fullContainer;
    @FXML
    private Label balancelabel;
    @FXML
    private StackPane rootPane;
    @FXML
    private StackPane stackpane;
    //price table end
    
    // transaction buttons 
    @FXML
    private JFXButton buyEquities;
    @FXML
    private JFXButton sellEquities;
    @FXML
    private JFXButton sellCFD;
    @FXML
    private JFXButton buyCFD;
    @FXML
    private JFXTextField amounttxt;
    // end transaction buttons
   
    
    DatabaseHandler databaseHandler;
    
    //for mousedrag
    private  double xOffset = 0;         
    private  double yOffset = 0;
    
    //to store transaction items when fetched from table
    ObservableList<Transaction> list = FXCollections.observableArrayList();
    

   
    
    //list to store Forex pair data
    private ObservableList<PriceTable> forexList = FXCollections.observableArrayList(
            new PriceTable("USD/EUR ",43),
            new PriceTable("USD/JPY",56),
            new PriceTable("USD/GBP",67),
            new PriceTable("USD/AUD",57),
            new PriceTable("USD/CAD",26),
            new PriceTable("USD/CNY",37),
            new PriceTable("USD/CHF",48),
            new PriceTable("USD/MXN",59),
            new PriceTable("USD/SGD",37),
            new PriceTable("USD/KRW",26),
            new PriceTable("USD/NZD",48),
            new PriceTable("USD/HKD",25),
            new PriceTable("USD/SEK",48),
            new PriceTable("USD/TRY",62),
            new PriceTable("USD/INR",84),
            new PriceTable("USD/RUB",83),
            new PriceTable("USD/NOK",14),
            new PriceTable("USD/BRL",13),
            new PriceTable("USD/ZAR",15),
            new PriceTable("USD/TWD",37),
            new PriceTable("USD/PLN",26),
            new PriceTable("USD/OTH",26)
            );
    
    //list to store cryptocurrency data
    private ObservableList<PriceTable> cryptoList = FXCollections.observableArrayList(

            new PriceTable("Bitcoin (BTC)",31),
            new PriceTable("Ether (ETH)",36),
            new PriceTable("Ripple (XRP)",58),
            new PriceTable("Bitcoin Cash (BCH)",47),
            new PriceTable("Litecoin (LTC)",25),
            new PriceTable("NEO (NEO)",34),
            new PriceTable("Cardano (ADA)",53),
            new PriceTable("Stellar (XLM)",54),
            new PriceTable("EOS (EOS)",36),
            new PriceTable("Monero (XMR)",87),
            new PriceTable("Dash (DASH)",84),
            new PriceTable("IOTA (MIOTA)",98),
            new PriceTable("NEM (XEM)",67),
            new PriceTable("Tether (USDT)",98),
            new PriceTable("VeChain (VEN)",25),
            new PriceTable("TRON (TRX)",25),
            new PriceTable("Ether Classic (ETC)",25),
            new PriceTable("Lisk (LSK)",87),
            new PriceTable("Nano (NANO)",45),
            new PriceTable("OmiseGo (OMG)",76),
            new PriceTable("Bitcoin Gold (BTG)",65),
            new PriceTable("Qtum (QTUM)",23)
            );
    
    public static int[] stocksArray = new int[22]; 
    //list to store company data 
    public ObservableList<PriceTable> data = FXCollections.observableArrayList(
         
            new PriceTable("Apple",stocksArray[0]),
            new PriceTable("Alphabet",stocksArray[1]),
            new PriceTable("Berkshire Hathaway",stocksArray[2]),
            new PriceTable("Facebook",stocksArray[3]),
            new PriceTable("AT&T",stocksArray[4]),
            new PriceTable("Berkshire Hathaway",stocksArray[5]),
            new PriceTable("JPMorgan Chase",stocksArray[6]),
            new PriceTable("Bank of America",stocksArray[7]),
            new PriceTable("Samsung Electroincs",stocksArray[8]),
            new PriceTable("Visa",stocksArray[9]),
            new PriceTable("Coca-Cola",stocksArray[10]),
            new PriceTable("Oracle",stocksArray[11]),
            new PriceTable("IBM",stocksArray[12]),
            new PriceTable("Tesla",stocksArray[13]),
            new PriceTable("Bose",stocksArray[14]),
            new PriceTable("AMD",stocksArray[15]),
            new PriceTable("Microsoft",stocksArray[16]),
            new PriceTable("Intel",stocksArray[17]),
            new PriceTable("Tesco",stocksArray[18]),
            new PriceTable("Berkshire Hathaway",stocksArray[19]),
            new PriceTable("Amazon",stocksArray[20]),
            new PriceTable("Spotify",stocksArray[21])
            );
    
    
  //----------Controls------------  
     
    @FXML//minimise button
    private void handleMin(MouseEvent event){
    getStage().setIconified(true);
    }
    
    @FXML//close button
    private void handleClose(MouseEvent event){
    System.exit(0);
    }
    
    @FXML//drag application window
    private void handleDrag(MouseEvent event) {        
    getStage().setX(event.getScreenX()- xOffset);
    getStage().setY(event.getScreenY() -yOffset);
    }
  
    @FXML//drag value for application window
    private void handleDragValue(MouseEvent event) {
    xOffset = event.getSceneX();
    yOffset = event.getSceneY();
    }
    
    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }
    
    //-------End Controls--------
    
    
    //-------Logout, Deposit, notification and withdraw buttons---------
    
    @FXML //logout 
    private void handleLogoutButtonAction(ActionEvent event){
        try{
        Parent sceneParent = FXMLLoader.load(getClass().getResource("/setraders/ui/client/Client.fxml"));
        Scene scene = new Scene(sceneParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        } catch (IOException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML//deposit 
    private void handleDepositButtonAction(ActionEvent event){
    try { BoxBlur blur = new BoxBlur(4, 4, 4);
        Parent root = FXMLLoader.load(getClass().getResource("/setraders/ui/deposit/Deposit.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        fullContainer.setEffect(blur);
        stage.showAndWait();
        fullContainer.setEffect(null);
        loadbalance();
        } catch (IOException ex) {
        Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    @FXML //withdraw
    private void handleWithdrawButtonAction(ActionEvent event){
    try { 
        BoxBlur blur = new BoxBlur(4, 4, 4);
        Parent root = FXMLLoader.load(getClass().getResource("/setraders/ui/withdraw/Withdraw.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        fullContainer.setEffect(blur);
        stage.showAndWait();
        fullContainer.setEffect(null);
        loadbalance();

        } catch (IOException ex) {
        Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML //set notification button
    private void handleNotiToggleButtonAction(ActionEvent event){
            try { BoxBlur blur = new BoxBlur(4, 4, 4);
        Parent root = FXMLLoader.load(getClass().getResource("/setraders/ui/notification/Notification.fxml"));
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        fullContainer.setEffect(blur);
        stage.showAndWait();
        fullContainer.setEffect(null);
        loadbalance();
  
        } catch (IOException ex) {
        Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//--------------------END logout, withdraw, notification and deposit buttons -------------------
    
//---------------------Radio Button Trading Type------------------------------------------------

    @FXML
    private void HandleTradingTypeRadioButton(ActionEvent event){
        
        if(tradeEquities.isSelected()){
          
        sellCFD.setVisible(false);
        buyCFD.setVisible(false);
        buyEquities.setVisible(true);
        sellEquities.setVisible(true);
        }
        if(tradeCFD.isSelected()){
        
        sellCFD.setVisible(true);
        buyCFD.setVisible(true);
        buyEquities.setVisible(false);
        sellEquities.setVisible(false);
        }
    }

//--------------------Radio button end---------------------------------------------------------    
    
    
    
//------------------------- CFD Buy and Sell buttons ------------------------------
    @FXML //
    private void handleBuyCFDButtonAction(ActionEvent event){
    
        DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        
        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        String transID = new String();
        try {
            while (rs.next()) {
                 String result = rs.getString("transactionid");
                 int transIDn = Integer.parseInt(result) + 1;
                 transID = Integer.toString(transIDn);
            }      
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        
        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();                      
        String transType = "Buy CFD";
        String accountid = "user1";
        String transCompany = companycfdCol.getCellData(selectedForBuy);
         int transPriceint = Integer.parseInt(pricecfdCol.getCellData(selectedForBuy).toString());
        String transPrice = Integer.toString(transPriceint);
        String transClosePrice = "-";
        double transAmount = 0;
        
        try{  
            transAmount = Double.parseDouble(amounttxt.getText());
            }catch(NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Invalid input", "Please input a number value");
            amounttxt.clear();
            return;
        }
        String transAmounts = Double.toString(transAmount);
        String transTime = dateFormat.format(date);
        
        if (selectedForBuy == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No item selected", "Please select a trading item from pricelist to trade");
            return;
        }
        
        if(transAmounts.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter trading amount.");
            return;
        }
         
        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transCompany, transType, transAmounts, transTime, transPrice, transClosePrice);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {
        
        double balance =  Double.parseDouble(transAmounts);
            
        String query = "SELECT * FROM bal";
        ResultSet rs1 = handler.execQuery(query);
        String balancex = new String();
        double check = 0;
        
        try { 
            while (rs1.next()) {
                 balancex = rs1.getString("balance");
                 check = Double.parseDouble(balancex);         
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }     
        
        
        setraders.data.wrapper.Balance bal1 = new  setraders.data.wrapper.Balance(accountid, balance);
        boolean qresult = DataHelper.updateBalanceminus(bal1);
        if (qresult) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Amount","£"+ balance + " has been withdrawn");
            loadbalance();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to withdraw amount", "Check all the entries and try again");
        }             
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", transCompany + "'s transaction completed");
            amounttxt.clear();
            refreshTransactionTable();
        } else {
            AlertMaker.showMaterialDialog( rootPane, mainContainer, new ArrayList<>(), "Failed to create new transaction", "Check all the entries and try again");
        }
    }
    
    @FXML //short button
    private void handleShortCFDButtonAction(ActionEvent event){
  
    

    }
    
        
    //--------------------------END CFD buy and sell/short button-------------------------
    
    
    //------------------------- Equity Buy and Sell buttons ------------------------------
    
    @FXML //buy equities
    private void handleBuyEquityButtonAction(ActionEvent event){
            DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        
        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        String transID = new String();
        try {
            while (rs.next()) {
                 String result = rs.getString("transactionid");
                 int transIDn = Integer.parseInt(result) + 1;
                 transID = Integer.toString(transIDn);
            }      
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        
        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();                      
        String transType = "Buy Equity";
        String accountid = "user1";
        String transCompany = companycfdCol.getCellData(selectedForBuy);
         int transPriceint = Integer.parseInt(pricecfdCol.getCellData(selectedForBuy).toString());
        String transPrice = Integer.toString(transPriceint);
        String transClosePrice = "-";
        double transAmount = 0;
        
        try{  
            transAmount = Double.parseDouble(amounttxt.getText());
            }catch(NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Invalid input", "Please input a number value");
            amounttxt.clear();
            return;
        }
        String transAmounts = Double.toString(transAmount);
        String transTime = dateFormat.format(date);
        
        if (selectedForBuy == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No item selected", "Please select a trading item from pricelist to trade");
            return;
        }
        
        if(transAmounts.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter trading amount.");
            return;
        }
         
        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transCompany, transType, transAmounts, transTime, transPrice, transClosePrice);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {
        
        double balance =  Double.parseDouble(transAmounts);
            
        String query = "SELECT * FROM bal";
        ResultSet rs1 = handler.execQuery(query);
        String balancex = new String();
        double check = 0;
        
        try { 
            while (rs1.next()) {
                 balancex = rs1.getString("balance");
                 check = Double.parseDouble(balancex);         
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }     
        
        
        setraders.data.wrapper.Balance bal1 = new  setraders.data.wrapper.Balance(accountid, balance);
        boolean qresult = DataHelper.updateBalanceminus(bal1);
        if (qresult) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Amount","£"+ balance + " has been withdrawn");
            loadbalance();
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to withdraw amount", "Check all the entries and try again");
        }             
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", transCompany + "'s transaction completed");
            amounttxt.clear();
            refreshTransactionTable();
        } else {
            AlertMaker.showMaterialDialog( rootPane, mainContainer, new ArrayList<>(), "Failed to create new transaction", "Check all the entries and try again");
        }
       

    }
       
    
    @FXML //sell/short equities button
    private void handleSellEquityButtonAction(ActionEvent event){
         

    }
    
    //----------------------------------END CFD buy and sell/short button----------------------------------------------
    
    
    
    
    //--------------------------------create transaction receipt list functionality-----------------------------------
            
    @FXML 
    private void exportAsPDF(ActionEvent event) {
        Transaction Reciept = tableView.getSelectionModel().getSelectedItem(); 
        
        List<List> printData = new ArrayList<>();
        String[] headers = {"Transaction ID", " Company ", " Amount ", "  Type ", "Time"};
        printData.add(Arrays.asList(headers));
        
        for (Transaction transaction : list) {
            List<String> row = new ArrayList<>();
            row.add(transaction.getTransactionid());
            row.add(transaction.getCompany());
            row.add(transaction.getMargin());
            row.add(transaction.getType());
            row.add(transaction.getTime());
            printData.add(row);
        }
        SetradersUtility.initPDFExprot(stackpane, mainRootPane, getStage(), printData);
    }
    
    //------------------------------------------------End create reciept--------=---------------------------------------
    
   
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* ThreadHandler threadHandler = new ThreadHandler();
        threadHandler.price_thread.start();
                series = new XYChart.Series<>();
        series.setName("Apple");
      series1 = new XYChart.Series<>();
      series1.setName("SAM");
      series2 = new XYChart.Series<>();
      series2.setName("DAM");
        lineChart.getData().add(series);
      lineChart.getData().add(series1);
      lineChart.getData().add(series2);
*/
        databaseHandler = DatabaseHandler.getInstance();
       
        initButtons();
        initComboBox();
        initColumns();
        loadPriceTable();
        loadTransactionTable();
        loadbalance();
    }
    
    
    //------------------------------------hiding cfd buttons on start up---------------------------
    
    private void initButtons(){
        sellCFD.setVisible(false);
        buyCFD.setVisible(false);
    }
    //------------------------------------End -----------------------------------------------------          
    
    //-----------------------------------Combobox to choose trading item--------------------------------
    
    private void initComboBox(){
    List<String> drop_list = new ArrayList<String>();
        drop_list.add("Shares");
        drop_list.add("Forex");
        drop_list.add("Cryptocurrencies");
        
        ObservableList oblist_drop = FXCollections.observableList(drop_list);
        tradingitemDrop.getItems().clear();
        tradingitemDrop.setItems(oblist_drop);
        
        tradingitemDrop.setOnAction((event) -> {
            
        String item = (String)tradingitemDrop.getSelectionModel().getSelectedItem();
        if (item == "Shares"){            
            priceTable.setItems(data);
        } else if (item == "Forex"){
            priceTable.setItems(forexList);
        } else if (item == "Cryptocurrencies"){
            priceTable.setItems(cryptoList);
        }
        });
    }
    
    //---------------------------------End Combobox---------------------------------------------------------
    
    
    //-------------Initialise Columns for price and transaction tables and load price table------------------
          
    private void initColumns(){  
        transactionidCol.setCellValueFactory(new PropertyValueFactory<>("transactionid"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        marginCol.setCellValueFactory(new PropertyValueFactory<>("margin"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        closepriceCol.setCellValueFactory(new PropertyValueFactory<>("closeprice"));
        
        companycfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("companycfdCol"));
        pricecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable,Number>("pricecfdCol"));
       // changecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("changecfdCol"));
    
    }
    
    //load price table data
    public void loadPriceTable(){
        priceTable.setItems(data);   
    }
    
    //load transaction table data
    private void loadTransactionTable(){
        
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
                String pricex = rs.getString("price");
                String pricec = rs.getString("closeprice");
 
                list.add(new Transaction(transidx, companyx, typex, marginx, dateandtimex, pricex, pricec));

            }
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.setItems(list);
    }
    
        //Transaction table
    private void refreshTransactionTable(){
        loadTransactionTable();
    } 
    
    //---------------------------------End tables init here----------------------------------------------
    
    
   
    
    //-------------------------------------Graph Code--------------------------------------------------
    @FXML
    private void loadGraph(MouseEvent event){
        //PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();  
        //String transCompany = companycfdCol.getCellData(selectedForBuy);
        //int transPriceint = Integer.parseInt(pricecfdCol.getCellData(selectedForBuy).toString());
        //String transPrice = Integer.toString(transPriceint);
        
    }
    
    //------------------------------End graph code here-------------------------------------------------
    



    //----------------------------------loading balance ------------------------------------------------
    private void loadbalance(){
        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        String qu = "SELECT * FROM bal";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String bal1 = rs.getString("balance");
                balancelabel.setText(bal1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  // ----------------------------------load balance end here----------------------------------------------    
}
