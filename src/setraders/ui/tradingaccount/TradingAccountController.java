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
import java.io.BufferedReader;
import java.io.FileReader;
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
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
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
import javafx.scene.chart.NumberAxis;
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
import setraders.tradingitem.CryptoCurrency;
import setraders.tradingitem.Forex;
import setraders.tradingitem.PriceSimulator;
import setraders.tradingitem.Stock;
import setraders.ui.tables.PriceTable;
import setraders.ui.tables.Transaction;
import setraders.util.SetradersUtility;
import setraders.tradingitem.ThreadHandler;



public class TradingAccountController implements Initializable {
    
    
     Timer timer1 = new Timer();
        TimerTask task;
        Random dice = new Random();
    
    //Radio Boxes
    @FXML
    private JFXRadioButton tradeCFD;
    @FXML
    private JFXRadioButton tradeEquities;
    
    //line chart
    //@FXML
   // private  LineChart<String, Number> lineChart;
   // public static XYChart.Series<String, Number> series, series1, series2;
    
    @FXML
    private LineChart<String, Number> lineChart;
    public static XYChart.Series<String, Number> series;
   // public static NumberAxis yAxis;
   // private  NumberAxis xAxis;
   
    
    //twitter
    @FXML
    private JFXListView twitterListView;
    @FXML
    private Label tweet1;
    @FXML
    private Label tweet2;
   
    
    //Equity transaction table start
    @FXML
    private TableView<Transaction> tableView;
    @FXML
    private TableColumn<Transaction, String> itemCol;
    @FXML
    private TableColumn<Transaction, String> typeCol;
    @FXML
    private TableColumn<Transaction, String> timeCol;
    @FXML
    private TableColumn<Transaction, Double> openpriceCol;
    @FXML
    private TableColumn<Transaction, Double> closepriceCol;
    @FXML
    private TableColumn<Transaction, Number> transactionidCol;
    @FXML
    private TableColumn<Transaction, Double> amountCol;
    //transaction table end
    
    
    //price table start
    @FXML
    public  TableView<PriceTable> priceTable;   
    @FXML
    private TableColumn<PriceTable, String> companycfdCol;
    @FXML
    private TableColumn<PriceTable, Double> pricecfdCol;
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
    
 
    Timer stocksTimer;
    Timer forexTimer;
    Timer cryptoTimer;
    
    boolean cryptoSelected;
    boolean stocksSelected;
    boolean forexSelected;
    
    //to store transaction items when fetched from table
    ObservableList<Transaction> list = FXCollections.observableArrayList();
   
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
        int transID = 0;
        try {
            while (rs.next()) {
                 int result = rs.getInt("transactionid");
                  transID = result + 1;        // transaction id
                
            }      
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        String accountid = "user1";
        
        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem(); 
        String transItem = companycfdCol.getCellData(selectedForBuy);  // item name 
        String transType = "Buy Equity";                               //type
        double transOpenprice = 0;        
        transOpenprice = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString());   //open price
        double transCloseprice = 0;                                    //close price
        double transAmount = 0;                                       //initalise amount
        String transTime = dateFormat.format(date);                   //date
        
        
        String toCheck = amounttxt.getText();
        if (toCheck.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No amount input", "Please input an amount in amount field");
            return;
        }
        
        try{  
            transAmount = Double.parseDouble(amounttxt.getText());   //validation
            }catch(NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Invalid input", "Please input a number value in item shares");
            amounttxt.clear();
            return;
        }
        
        
        String qu1 = "SELECT item FROM TRANS WHERE type = 'Sell Equity' and item = '" + transItem+"'" ;
        ResultSet rss = handler.execQuery(qu1);
     
        try {
            while (rss.next()) {
                 String result = rss.getString("item");
                 System.out.println(result);
                    if (result != transItem){
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Equity has been bought already", "Try Selling it to buy more");
                    amounttxt.clear();
                    return;
                    }
            }      
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transItem, transType, transAmount, transTime, transOpenprice, transCloseprice);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {
        
        double balance =  transAmount;
            
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
        
        if (transAmount<= check){
        
        setraders.data.wrapper.Balance bal1 = new  setraders.data.wrapper.Balance(accountid, balance);
        boolean qresult = DataHelper.updateBalanceminus(bal1);
        if (qresult) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Amount","£"+ balance + " has been withdrawn");
            loadbalance();
            
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", transItem + "'s transaction completed");
            amounttxt.clear();
            refreshTransactionTable();
            priceTable.refresh();
            
            
        }} else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed to withdraw amount", "Check all the entries and try again");
            
        }
    
    } 
      
}
       
    
    @FXML //sell/short equities button
    private void handleSellEquityButtonAction(ActionEvent event){
  
        DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();
        
        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        int transID = 0;
        try {
            while (rs.next()) {
                 int result = rs.getInt("transactionid");
                  transID = result + 1;        // transaction id
                
            }      
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        
        String accountid = "user1";
        
        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem(); 
        String transItem = companycfdCol.getCellData(selectedForBuy);  // item name 
        String transType = "Sell Equity";                               //type       
        double transOpenprice = 0;   //open price
        double transCloseprice = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString()); //close price
        double transAmount = 0;                                       //initalise amount
        String transTime = dateFormat.format(date);                   //date
        
        
        String toCheck = amounttxt.getText();
        if (toCheck.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "No amount input", "Please input an amount in amount field");
            return;
        }
        
        try{  
            transAmount = Double.parseDouble(amounttxt.getText());   //validation
            }catch(NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Invalid input", "Please input a number value in item shares");
            amounttxt.clear();
            return;
        }
        
      
        String qu1 = "SELECT item FROM TRANS WHERE type = 'Sell Equity' AND item ='"+transItem+"'" ;
        ResultSet rss = handler.execQuery(qu1);
     
        try {
            while (rss.next()) {
                 String result = rss.getString("item");
                 System.out.println(result);
                    if (result != transItem){
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Buy Equity first", "Buy equity to sell it");
                    amounttxt.clear();
                    return;
                    }
            }      
            
        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }  

        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transItem, transType, transAmount, transTime, transOpenprice, transCloseprice);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {
        
        double balance =  transAmount;
            
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
        boolean qresult = DataHelper.updateBalanceplus(bal1);
        if (qresult) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Amount","£"+ balance + " has been added to your account");
            loadbalance();
            
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", transItem + "'s transaction completed");
            amounttxt.clear();
            refreshTransactionTable();
            priceTable.refresh();
            
            
        } 
    
    } 

    }
    
    //----------------------------------END CFD buy and sell/short button----------------------------------------------
    
    
    
    
    //--------------------------------create transaction receipt list functionality-----------------------------------
            
    @FXML 
    private void exportAsPDF(ActionEvent event) {
        Transaction Reciept = tableView.getSelectionModel().getSelectedItem(); 
                        
        List<List> printData = new ArrayList<>();
        String[] headers = {"ID", " Item ", "Type", "Amount","Openprice","Closeprice", "Time"};
        printData.add(Arrays.asList(headers));
        
        for (Transaction transaction : list) {
            List row = new ArrayList<>();
            row.add(transaction.getTransactionid());
            row.add(transaction.getItem());
            row.add(transaction.getType());
            row.add(transaction.getAmount());
            row.add(transaction.getOpenprice());
            row.add(transaction.getCloseprice());
            row.add(transaction.getTime());
            
        
            printData.add(row);
        }
        SetradersUtility.initPDFExprot(stackpane, mainRootPane, getStage(), printData);
    }
    
    //------------------------------------------------End create reciept--------=---------------------------------------
    
   
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        databaseHandler = DatabaseHandler.getInstance();
       
      //  initgraph();
        initButtons();
        initComboBox();
        initColumns();
        loadPriceTable();
        loadTransactionTable();
        loadbalance();
        //loadtwitter();
        
    }
    
    private void loadtwitter(){
        
        
        String fileName = "TweetStream.txt";
        String line;
        ArrayList arr = new ArrayList();

        try{
            BufferedReader output = new BufferedReader(new FileReader(fileName));
            if(!output.ready())
                throw new IOException();

            while((line = output.readLine()) != null) {
                arr.add(line);
            }
            output.close();
            
        }catch(IOException e)
        {
            System.out.println(e);
        }

        try {
            for (int i = 0; i < arr.size(); i++) {
                Thread.sleep(1000);
                System.out.println(arr.get(i).toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initgraph(){
        /*
        yAxis = new NumberAxis();
        xAxis = new NumberAxis(0, PriceSimulator.MAX_DATA_POINTS, PriceSimulator.MAX_DATA_POINTS / 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        
        lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
            @Override
            protected void dataItemAdded(XYChart.Series<Number, Number> series, int itemIndex, XYChart.Data<Number, Number> item) {
            }
        };

        lineChart.setAnimated(false);
        lineChart.setTitle("Price chart");
        lineChart.setHorizontalGridLinesVisible(true);
        
        
        series = new XYChart.Series<>();
        series.setName("Crypto");
        ThreadHandler threadHandler = new ThreadHandler();
        threadHandler.price_thread.start(); 
        lineChart.getData().add(series);
        */
        
  XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();



    series.getData().add(new XYChart.Data<String,Number>("1",0));

    lineChart.getData().add(series);
   // xAxis.setForceZeroInRange(false);

    task = new TimerTask(){
        int secondsPassed = 0;
        @Override
        public void run() {
            secondsPassed++;
            //System.out.println(secondsPassed);
            int number;
            number = 1+dice.nextInt(6);
            series.getData().add(new XYChart.Data<String,Number>(String.valueOf(secondsPassed),number));
        }

    };

    timer1.scheduleAtFixedRate(task, 1000, 1000);      
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
        drop_list.add("Stock");
        drop_list.add("Forex");
        drop_list.add("Cryptocurrencies");
        
        ObservableList oblist_drop = FXCollections.observableList(drop_list);
        tradingitemDrop.getItems().clear();
        tradingitemDrop.setItems(oblist_drop);
        
        tradingitemDrop.setOnAction((event) -> {
            
        String item = (String)tradingitemDrop.getSelectionModel().getSelectedItem();
        if (item == "Stock"){   
            cryptoSelected = false;
            forexSelected = false;
            stocksSelected = true;
     
            stocksTimer = new Timer();
            stocksTimer.schedule(
                    
            new TimerTask() {
            
                @Override
                public void run()   {
                    if(stocksSelected == true) { 
                    priceTable.setItems(Stock.getStockList());
                    //priceTable.refresh();
                    }
            
                    else if (stocksSelected == false){
                    stocksTimer.cancel();
                    stocksTimer.purge();
                    }        
                }
            }, 0, 5000);
            
        }else if (item == "Forex"){
            cryptoSelected = false;
            forexSelected = true;
            stocksSelected = false;
            
            forexTimer = new Timer();
            forexTimer.schedule(
                    
            new TimerTask() {
            
                @Override
                public void run()   {
                    if(forexSelected == true) { 
                    priceTable.setItems(Forex.getForexList());
                   // priceTable.refresh();
                    }
            
                    else if (forexSelected == false){
                    forexTimer.cancel();
                    forexTimer.purge();
                    }        
                }
            }, 0, 5000);
            
        }else if (item == "Cryptocurrencies"){
            cryptoSelected = true;
            forexSelected = false;
            stocksSelected = false;
           
            cryptoTimer = new Timer();
            cryptoTimer.schedule(
                    
            new TimerTask() {
            
                @Override
                public void run()   {
                    if(cryptoSelected == true) { 
                    priceTable.setItems(CryptoCurrency.getCryptoList());
                    //priceTable.refresh();
                    }
            
                    else if (cryptoSelected == false){
                    cryptoTimer.cancel();
                    cryptoTimer.purge();
                    }        
                }
            }, 0, 5000);  
    }});}
     
    /*
    
    
    */
    
    //---------------------------------End Combobox---------------------------------------------------------
    
    
    //-------------Initialise Columns for price and transaction tables and load price table------------------
          
    private void initColumns(){  
        transactionidCol.setCellValueFactory(new PropertyValueFactory<>("transactionid"));
        itemCol.setCellValueFactory(new PropertyValueFactory<>("item"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        openpriceCol.setCellValueFactory(new PropertyValueFactory<>("openprice"));
        closepriceCol.setCellValueFactory(new PropertyValueFactory<>("closeprice"));
        
        companycfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("companycfdCol"));
        pricecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable,Double>("pricecfdCol"));

    
    }
    
    //load price table data
    public void loadPriceTable(){
       stocksSelected = true;
     
            stocksTimer = new Timer();
            stocksTimer.schedule(
                    
            new TimerTask() {
            
                @Override
                public void run()   {
                    if(stocksSelected == true) { 
                    priceTable.setItems(Stock.getStockList());
                    }
            
                    else if (stocksSelected == false){
                    stocksTimer.cancel();
                    stocksTimer.purge();
                    }        
                }
            }, 0, 5000);   
    }
    
    //load transaction table data
    private void loadTransactionTable(){
        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        list.clear();
        String qu = "SELECT * FROM TRANS";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int transidx = rs.getInt("transactionid");
                String itemx = rs.getString("item");
                String typex = rs.getString("type");
                double amountx = rs.getDouble("amount");
                String dateandtimex = rs.getString("time");
                double openpricex = rs.getDouble("openprice");
                double closepricex = rs.getDouble("closeprice");
 
                list.add(new Transaction(transidx, itemx, typex, amountx, dateandtimex, openpricex, closepricex));

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


