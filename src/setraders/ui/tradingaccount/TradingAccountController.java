/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.ui.tradingaccount;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import setraders.alert.AlertMaker; 
import setraders.database.DataHelper;
import setraders.database.DatabaseHandler;
import setraders.ui.tables.PriceTable;
import setraders.ui.tables.Transaction;
import setraders.util.SetradersUtility;
import setraders.tradingitem.ThreadHandler;



public class TradingAccountController implements Initializable {
    
    //Graph
    @FXML
    private  LineChart<String, Number> lineChart;
    public static XYChart.Series<String, Number> series, series1, series2;
    
    //listboxes
    @FXML
    private JFXListView twitterListView;
   
    //transaction table start
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
     //transaction table end
    
    //price table start
    @FXML
    private TableView<PriceTable> priceTable;
        
    @FXML
    private TableColumn<PriceTable, String> companycfdCol;
    
    @FXML
    private TableColumn<PriceTable, String> pricecfdCol;
    
    @FXML
    private TableColumn<PriceTable, String> changecfdCol;
    
    @FXML
    private AnchorPane mainContainer;
    
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
    
    private Boolean isInEditMode = Boolean.FALSE;
    //price table end
    
    
    // Data input simulator text fields for generating transaction _ delete when done//
    @FXML
    private JFXTextField companytxt;
    @FXML
    private JFXTextField transidtxt;
    @FXML
    private JFXTextField typetxt;
    @FXML
    private JFXTextField timeidtxt;
    @FXML
    private JFXTextField amounttxt;
    // end here
    
    DatabaseHandler databaseHandler;
    
    //for mousedrag
    private  double xOffset = 0;         
    private  double yOffset = 0;
    
    //to store transaction items when fetched from table
    ObservableList<Transaction> list = FXCollections.observableArrayList();
    

   
    
    //list to store Forex pair data
    private ObservableList<PriceTable> forexList = FXCollections.observableArrayList(
    

            new PriceTable("USD/EUR ","2","1"),
            new PriceTable("USD/JPY","1","1"),
            new PriceTable("USD/GBP","1","2"),
            new PriceTable("USD/AUD","2","1"),
            new PriceTable("USD/CAD","1","1"),
            new PriceTable("USD/CNY","1","2"),
            new PriceTable("USD/CHF","2","1"),
            new PriceTable("USD/MXN","1","1"),
            new PriceTable("USD/SGD","1","2"),
            new PriceTable("USD/KRW","2","1"),
            new PriceTable("USD/NZD","1","2"),
            new PriceTable("USD/HKD","2","1"),
            new PriceTable("USD/SEK","1","1"),
            new PriceTable("USD/TRY","1","2"),
            new PriceTable("USD/INR","2","1"),
            new PriceTable("USD/RUB","1","1"),
            new PriceTable("USD/NOK","1","2"),
            new PriceTable("USD/BRL","2","1"),
            new PriceTable("USD/ZAR","1","1"),
            new PriceTable("USD/TWD","1","2"),
            new PriceTable("USD/PLN","2","1"),
            new PriceTable("USD/OTH","1","1")
            );
    
    //list to store cryptocurrency data
    private ObservableList<PriceTable> cryptoList = FXCollections.observableArrayList(

            new PriceTable("Bitcoin (BTC)","£120","2"),
            new PriceTable("Ether (ETH)","2","1"),
            new PriceTable("Ripple (XRP)","1","1"),
            new PriceTable("Bitcoin Cash (BCH)","1","2"),
            new PriceTable("Litecoin (LTC)","2","1"),
            new PriceTable("NEO (NEO)","1","1"),
            new PriceTable("Cardano (ADA)","1","2"),
            new PriceTable("Stellar (XLM)","2","1"),
            new PriceTable("EOS (EOS)","1","1"),
            new PriceTable("Monero (XMR)","1","2"),
            new PriceTable("Dash (DASH)","2","1"),
            new PriceTable("IOTA (MIOTA)","1","2"),
            new PriceTable("NEM (XEM)","2","1"),
            new PriceTable("Tether (USDT)","1","1"),
            new PriceTable("VeChain (VEN)","1","2"),
            new PriceTable("TRON (TRX)","2","1"),
            new PriceTable("Ether Classic (ETC)","1","1"),
            new PriceTable("Lisk (LSK)","1","2"),
            new PriceTable("Nano (NANO)","2","1"),
            new PriceTable("OmiseGo (OMG)","1","1"),
            new PriceTable("Bitcoin Gold (BTG)","1","2"),
            new PriceTable("Qtum (QTUM)","2","1")
            );
    
    //list to store company data 
    ObservableList<PriceTable> data = FXCollections.observableArrayList(
           
            new PriceTable("Apple","£120","2"),
            new PriceTable("Alphabet","2","1"),
            new PriceTable("Berkshire Hathaway","1","1"),
            new PriceTable("Facebook","1","2"),
            new PriceTable("AT&T","2","1"),
            new PriceTable("Berkshire Hathaway","1","1"),
            new PriceTable("JPMorgan Chase","1","2"),
            new PriceTable("Bank of America","2","1"),
            new PriceTable("Samsung Electroincs","1","1"),
            new PriceTable("Visa","1","2"),
            new PriceTable("Coca-Cola","2","1"),
            new PriceTable("Oracle","1","2"),
            new PriceTable("IBM","2","1"),
            new PriceTable("Tesla","1","1"),
            new PriceTable("Bose","1","2"),
            new PriceTable("AMD","2","1"),
            new PriceTable("Microsoft","1","1"),
            new PriceTable("Intel","1","2"),
            new PriceTable("Tesco","2","1"),
            new PriceTable("Berkshire Hathaway","1","1"),
            new PriceTable("Amazon","2","1"),
            new PriceTable("Spotify","1","1")
            );
     
    @FXML//close button
    private void handleClose(MouseEvent event){
    System.exit(0);
    }
    
    private Stage getStage() {
        return (Stage) tableView.getScene().getWindow();
    }
   
    @FXML//minimise button
    private void handleMin(MouseEvent event){
    getStage().setIconified(true);
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
    
    @FXML//deposit button
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
   
    
    @FXML//withdraw button
    private void handleWithdrawButtonAction(ActionEvent event){
    try { //getStage().setIconified(true);
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
    
    @FXML //test simulation to get data from text field to load to db ___use this code to add data to transaction list database
    private void handleBuyCFDButtonAction(ActionEvent event){
        String transID = transidtxt.getText();                                                    
        String transType = typetxt.getText();
        String transCompany = companytxt.getText();
        String transAmount = amounttxt.getText();
        String transTime = timeidtxt.getText();

        if (isInEditMode) {
            handleEditOperation();
            return;
        }
        
        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transCompany, transType, transAmount, transTime);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "New transaction created", transCompany + "'s transaction completed");
            clearEntries();
            refreshTransactionTable();
        } else {
            AlertMaker.showMaterialDialog( rootPane, mainContainer, new ArrayList<>(), "Failed to create new transaction", "Check all the entries and try again");
        }
    }
            
    @FXML //create transaction receipt list functionality
    private void exportAsPDF(ActionEvent event) {
        System.out.println("This worked");
        List<List> printData = new ArrayList<>();
        String[] headers = {"Transaction ID", "   Company  ", "  Type  ", "  Amount ", "Time"};
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
    
    //test simulation to get data from text field to load to db
    public void inflateUI( Transaction transaction) {
        companytxt.setText(transaction.getTransactionid());
        transidtxt.setText(transaction.getCompany());
        typetxt.setText(transaction.getType());
        amounttxt.setText(transaction.getMargin());
        timeidtxt.setText(transaction.getTime());
        transidtxt.setEditable(false);
        isInEditMode = Boolean.TRUE; 
    }
    
    //test simulation clear textfields
    private void clearEntries() {
        companytxt.clear();
        transidtxt.clear();
        typetxt.clear();
        amounttxt.clear();
        timeidtxt.clear();
    }
    
    //test simulation text field edit operation
    private void handleEditOperation() {
        Transaction transaction = new  Transaction(companytxt.getText(), transidtxt.getText(), typetxt.getText(), amounttxt.getText(), timeidtxt.getText());
        if (databaseHandler.updateTransaction(transaction)) {
            AlertMaker.showMaterialDialog( rootPane, mainContainer, new ArrayList<>(), "Success", "Update complete");
        } else {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Failed", "Could not update data");
        }
    }
    
    @FXML //short button
    private void handleShortCFDButtonAction(ActionEvent event){
        
    }
    
    @FXML //logout button
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
    
    @FXML //notification button
    private void handleNotiToggleButtonAction(ActionEvent event){
        double demostock=200;
        Notifications notificationBuilder = Notifications.create()
        .title("Price Alert")
        .text("Your stock has hit £"+ demostock)
        .graphic(null)
        .hideAfter(Duration.seconds(5))
        .position(Pos.BOTTOM_RIGHT)
        .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event1) {
                System.out.println("Clicked on notification");
            }
        });
        notificationBuilder.darkStyle();
        notificationBuilder.showInformation();
    }
    
    @Override//loaded at start
    public void initialize(URL url, ResourceBundle rb) {
        
        ThreadHandler threadHandler = new ThreadHandler();
        threadHandler.cryptoPrice.start();
        threadHandler.forexPrice.start();
        threadHandler.stockPrice.start();
        
        series = new XYChart.Series<>();
        series.setName("Apple");
      series1 = new XYChart.Series<>();
      series1.setName("SAM");
      series2 = new XYChart.Series<>();
      series2.setName("DAM");
        lineChart.getData().add(series);
      lineChart.getData().add(series1);
      lineChart.getData().add(series2);

        
        databaseHandler = DatabaseHandler.getInstance();
        
        initComboBox();
        initColumns();
        loadPriceTable();
        loadTransactionTable();
        loadbalance();
        
        
        //line chart code
 
        
    }
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
    //initialise transaction and price table columns    
    private void initColumns(){  
        transactionidCol.setCellValueFactory(new PropertyValueFactory<>("transactionid"));
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        marginCol.setCellValueFactory(new PropertyValueFactory<>("margin"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        
        companycfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("companycfdCol"));
        pricecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable,String>("pricecfdCol"));
        changecfdCol.setCellValueFactory(new PropertyValueFactory<PriceTable, String>("changecfdCol"));
    
    }
    //load price table data
    private void loadPriceTable(){
        priceTable.setItems(data);   
    
    }
   
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
//load transaction table data from database
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
 
                list.add(new Transaction(transidx, companyx, typex, marginx, dateandtimex));

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
}
