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
        double transPricedouble = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString());
        String transPrice = Double.toString(transPricedouble);
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
        
        if(transAmounts.isEmpty()||selectedForBuy.equals(null)) {
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
        
        if (transAmount<= check){
        
        setraders.data.wrapper.Balance bal1 = new  setraders.data.wrapper.Balance(accountid, balance);
        boolean qresult = DataHelper.updateBalanceminus(bal1);
        if (qresult) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Amount","£"+ balance + " has been withdrawn");
            loadbalance();
            
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", transCompany + "'s transaction completed");
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
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        marginCol.setCellValueFactory(new PropertyValueFactory<>("margin"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        closepriceCol.setCellValueFactory(new PropertyValueFactory<>("closeprice"));
        
        companycfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("companycfdCol"));
        pricecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable,Double>("pricecfdCol"));
       // changecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("changecfdCol"));
    
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
                    //priceTable.refresh();
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


