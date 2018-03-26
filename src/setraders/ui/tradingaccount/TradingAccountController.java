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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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




    //Radio Boxes
    @FXML
    private JFXRadioButton tradeCFD;
    @FXML
    private JFXRadioButton tradeEquities;

    //line chart
    @FXML
    private LineChart < Number, Number > lineChart;
    public static XYChart.Series < Number, Number > series;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
    //Twitter list view
    @FXML
    private JFXListView twitterListView;
    @FXML
    private Label tweet1;
    @FXML
    private Label tweet2;
    @FXML
    private Label tweet3;
    @FXML
    private Label tweet4;
    @FXML
    private Label tweet5;
    @FXML
    private Label tweet6;
    
    //Twitter handles
    @FXML
    private Label handle1;
    @FXML
    private Label handle2;
    @FXML
    private Label handle3;
    @FXML
    private Label handle4;
    @FXML
    private Label handle5;
    @FXML
    private Label handle6;

    //confindence indicator
    @FXML
    private Label confidenceLabel;
    @FXML
    private Label confidenceItemName;

    
   //Equity transaction table start
    @FXML
    private TableView < Transaction > tableView;
    @FXML
    private TableColumn < Transaction, String > itemCol;
    @FXML
    private TableColumn < Transaction, String > typeCol;
    @FXML
    private TableColumn < Transaction, String > timeCol;
    @FXML
    private TableColumn < Transaction, Double > openpriceCol;
    @FXML
    private TableColumn < Transaction, Double > closepriceCol;
    @FXML
    private TableColumn < Transaction, Number > transactionidCol;
    @FXML
    private TableColumn < Transaction, Double > amountCol;
    //transaction table end


    //price table start
    @FXML
    public TableView < PriceTable > priceTable;
    @FXML
    private TableColumn < PriceTable, String > companycfdCol;
    @FXML
    private TableColumn < PriceTable, Double > pricecfdCol;
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
    private double xOffset = 0;
    private double yOffset = 0;

    public int increment = 0;

    Timer stocksTimer;
    Timer forexTimer;
    Timer cryptoTimer;

    Timer graphTimer;
    Random dice = new Random();
    static Random random = new Random();

    boolean cryptoSelected;
    boolean stocksSelected;
    boolean forexSelected;
    boolean graphRun = false;

    //to store transaction items when fetched from table
    ObservableList < Transaction > list = FXCollections.observableArrayList();

    //----------Controls------------  

    @FXML //minimise button
    private void handleMin(MouseEvent event) {
        getStage().setIconified(true);
    }

    @FXML //close button
    private void handleClose(MouseEvent event) {
        System.exit(0);
    }

    @FXML //drag application window
    private void handleDrag(MouseEvent event) {
        getStage().setX(event.getScreenX() - xOffset);
        getStage().setY(event.getScreenY() - yOffset);
    }

    @FXML //drag value for application window
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
    private void handleLogoutButtonAction(ActionEvent event) {
        try {
            Parent sceneParent = FXMLLoader.load(getClass().getResource("/setraders/ui/client/Client.fxml"));
            Scene scene = new Scene(sceneParent);
            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //deposit 
    private void handleDepositButtonAction(ActionEvent event) {
        try {
            BoxBlur blur = new BoxBlur(4, 4, 4);
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
    private void handleWithdrawButtonAction(ActionEvent event) {
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
    private void handleFaqButton(ActionEvent event) {
        try {
            BoxBlur blur = new BoxBlur(4, 4, 4);
            Parent root = FXMLLoader.load(getClass().getResource("/setraders/ui/faq/Faq.fxml"));

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
    private void HandleTradingTypeRadioButton(ActionEvent event) {

        if (tradeEquities.isSelected()) {

            sellCFD.setVisible(false);
            buyCFD.setVisible(false);
            buyEquities.setVisible(true);
            sellEquities.setVisible(true);
        }
        if (tradeCFD.isSelected()) {

            sellCFD.setVisible(true);
            buyCFD.setVisible(true);
            buyEquities.setVisible(false);
            sellEquities.setVisible(false);
        }
    }

    //--------------------Radio button end---------------------------------------------------------    



    //------------------------- CFD Buy and Sell buttons ------------------------------
    @FXML //
    private void handleBuyCFDButtonAction(ActionEvent event) {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();

        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        int transID = 0;
        try {
            while (rs.next()) {
                int result = rs.getInt("transactionid");
                transID = result + 1; // transaction id
            }

        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String accountid = "user1";

        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();
        if (priceTable.getSelectionModel().isEmpty()){
             AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please select an item from the trading list");
            amounttxt.clear();
            return;
        }
        String transItem = companycfdCol.getCellData(selectedForBuy); // item name 
        String transType = "Buy CFD"; //type
        double transOpenprice = 0;
        transOpenprice = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString()); //open price
        double transCloseprice = 0; //close price
        double transAmount = 0; //initalise amount
        String transTime = dateFormat.format(date); //date

        String toCheck = amounttxt.getText();
        if (toCheck.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "No amount input", "Please input an amount in amount field");
            return;
        }
        
        try {
            transAmount = Double.parseDouble(amounttxt.getText()); //validation
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please input a number value in item shares");
            amounttxt.clear();
            return;
        }


        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transItem, transType, transAmount, transTime, transOpenprice, transCloseprice);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {

            double balance = transAmount;

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

            if (transAmount <= check) {

                setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
                boolean qresult = DataHelper.updateBalanceminus(bal1);
                if (qresult) {
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Amount", "£" + balance + " has been withdrawn");
                    loadbalance();

                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "New transaction created", transItem + "'s transaction completed");
                    amounttxt.clear();
                    refreshTransactionTable();
                    priceTable.refresh();
                }
            } else {
                AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Failed to withdraw amount", "The amount you've selected is too high. Deposit first to continue with trade");
            }
        }
    }

    @FXML //short button
    private void handleShortCFDButtonAction(ActionEvent event) {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();

        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        int transID = 0;
        try {
            while (rs.next()) {
                int result = rs.getInt("transactionid");
                transID = result + 1; // transaction id
            }

        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String accountid = "user1";

        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();
        if (priceTable.getSelectionModel().isEmpty()){
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please select an item from the trading list");
            amounttxt.clear();
            return;
        }
        String transItem = companycfdCol.getCellData(selectedForBuy); // item name 
        String transType = "Sell CFD"; //type       
        double transOpenprice = 0; //open price
        double transCloseprice = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString()); //close price
        double transAmount = 0; //initalise amount
        String transTime = dateFormat.format(date); //date


        String toCheck = amounttxt.getText();
        if (toCheck.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "No amount input", "Please input an amount in amount field");
            return;
        }

        try {
            transAmount = Double.parseDouble(amounttxt.getText()); //validation
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please input a number value in item shares");
            amounttxt.clear();
            return;
        }

//fix this
        String qu1 = "SELECT item FROM TRANS WHERE type = 'Buy CFD' AND item = '" + transItem + "'";
        ResultSet rss = handler.execQuery(qu1);
        double openpricex = 0;
        try {
            while (rss.next()) {
                String result = rss.getString("item");
                openpricex = rss.getDouble("openprice");
                System.out.println(openpricex - transCloseprice);
                if (result != transItem) {
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Buy CFD first", "Buy CFD to sell it");
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
            
            String qu2 = "SELECT item FROM TRANS WHERE amount =" + transAmount + " AND type = 'Buy CFD' AND item = '" + transItem + "'";
            ResultSet rs2 = handler.execQuery(qu1);

            double profit = 0;
            try {
                while (rs2.next()) {
                    String resultx = rss.getString("item");
                    double closepricex = rss.getDouble("closeprice");
                    profit = openpricex - closepricex;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            double balance = transAmount;

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

            setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
            boolean qresult = DataHelper.updateBalanceplus(bal1);
            if (qresult) {
                AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Amount", "£" + balance + " has been added to your account");
                loadbalance();

                AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "New transaction created", transItem + "'s transaction completed");
                amounttxt.clear();
                refreshTransactionTable();
                priceTable.refresh();
            }
        }
    }

    //--------------------------END CFD buy and sell/short button-------------------------


    //------------------------- Equity Buy and Sell buttons ------------------------------

    @FXML //buy equities
    private void handleBuyEquityButtonAction(ActionEvent event) {

        DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();

        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        int transID = 0;
        try {
            while (rs.next()) {
                int result = rs.getInt("transactionid");
                transID = result + 1; // transaction id

            }

        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String accountid = "user1";

        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();
            if (priceTable.getSelectionModel().isEmpty()){
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please select an item from the trading list");
            amounttxt.clear();
            return;
        }
        String transItem = companycfdCol.getCellData(selectedForBuy); // item name 
        String transType = "Buy Equity"; //type
        double transOpenprice = 0;
        transOpenprice = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString()); //open price
        double transCloseprice = 0; //close price
        double transAmount = 0; //initalise amount
        String transTime = dateFormat.format(date); //date


        String toCheck = amounttxt.getText();
        if (toCheck.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "No amount input", "Please input an amount in amount field");
            return;
        }

        try {
            transAmount = Double.parseDouble(amounttxt.getText()); //validation
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please input a number value in item shares");
            amounttxt.clear();
            return;
        }


        setraders.data.wrapper.Transaction transaction = new setraders.data.wrapper.Transaction(transID, transItem, transType, transAmount, transTime, transOpenprice, transCloseprice);
        boolean result = DataHelper.insertNewTransaction(transaction);
        if (result) {

            double balance = transAmount;

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

            if (transAmount <= check) {

                setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
                boolean qresult = DataHelper.updateBalanceminus(bal1);
                if (qresult) {
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Amount", "£" + balance + " has been withdrawn");
                    loadbalance();

                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "New transaction created", transItem + "'s transaction completed");
                    amounttxt.clear();
                    refreshTransactionTable();
                    priceTable.refresh();


                }
            } else {
                AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Failed to withdraw amount", "The amount you've selected is too high. Deposit first to continue with trade");

            }

        }

    }


    @FXML //sell/short equities button
    private void handleSellEquityButtonAction(ActionEvent event) {

        DatabaseHandler handler = DatabaseHandler.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();

        String qu = "SELECT * FROM TRANS WHERE transactionid=(SELECT MAX(transactionid) FROM TRANS)";
        ResultSet rs = handler.execQuery(qu);
        int transID = 0;
        try {
            while (rs.next()) {
                int result = rs.getInt("transactionid");
                transID = result + 1; // transaction id

            }

        } catch (SQLException ex) {
            Logger.getLogger(TradingAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String accountid = "user1";

        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();
            if (priceTable.getSelectionModel().isEmpty()){
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please select an item from the trading list");
            amounttxt.clear();
            return;
        }
        String transItem = companycfdCol.getCellData(selectedForBuy); // item name 
        String transType = "Sell Equity"; //type       
        double transOpenprice = 0; //open price
        double transCloseprice = Double.parseDouble(pricecfdCol.getCellData(selectedForBuy).toString()); //close price
        double transAmount = 0; //initalise amount
        String transTime = dateFormat.format(date); //date


        String toCheck = amounttxt.getText();
        if (toCheck.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "No amount input", "Please input an amount in amount field");
            return;
        }

        try {
            transAmount = Double.parseDouble(amounttxt.getText()); //validation
        } catch (NumberFormatException e) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Invalid input", "Please input a number value in item shares");
            amounttxt.clear();
            return;
        }


        String qu1 = "SELECT item FROM TRANS WHERE type = 'buy equity' AND item = '" + transItem + "'";
        ResultSet rss = handler.execQuery(qu1);

        try {
            while (rss.next()) {
                String result = rss.getString("item");
                System.out.println(result);
                if (result != transItem) {
                    AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Buy Equity first", "Buy equity to sell it");
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

            double balance = transAmount;

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



            setraders.data.wrapper.Balance bal1 = new setraders.data.wrapper.Balance(accountid, balance);
            boolean qresult = DataHelper.updateBalanceplus(bal1);
            if (qresult) {
                AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "Amount", "£" + balance + " has been added to your account");
                loadbalance();

                AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList < > (), "New transaction created", transItem + "'s transaction completed");
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

        List < List > printData = new ArrayList < > ();
        String[] headers = {
            "ID",
            " Item ",
            "Type",
            "Amount",
            "Openprice",
            "Closeprice",
            "Time"
        };
        printData.add(Arrays.asList(headers));

        for (Transaction transaction: list) {
            List row = new ArrayList < > ();
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

        // initgraph();
        initButtons();
        initComboBox();
        initColumns();
        loadPriceTable();
        loadTransactionTable();
        loadbalance();
        loadTweets();
    }
    //--------------------------------Twitter code -------------------------------------------------
     
    
    public void loadTweets() {

  String fileName = "TwitterStream.txt";
  String line;
  ArrayList < String > arr = new ArrayList < > ();

  try {
   BufferedReader output = new BufferedReader(new FileReader(fileName));
   if (!output.ready())
    throw new IOException();

   while ((line = output.readLine()) != null) {
    arr.add(line);
   }
   output.close();
  } catch (IOException e) {
   System.out.println(e);
  }

  try {
   handle1.setText(arr.get(0));
   tweet1.setText(arr.get(1));
   tweet1.setWrapText(true);
   tweet1.setWrapText(true);

   //second tweet
   handle2.setText(arr.get(2));
   tweet2.setText(arr.get(3));
   tweet2.setWrapText(true);
   tweet2.setWrapText(true);

   //third tweet.
   handle3.setText(arr.get(4));
   tweet3.setText(arr.get(5));
   tweet3.setWrapText(true);
   tweet3.setWrapText(true);

   //fourth tweet.
   handle4.setText(arr.get(6));
   tweet4.setText(arr.get(7));
   tweet4.setWrapText(true);
   tweet4.setWrapText(true);

   //fifth tweet.
   handle5.setText(arr.get(8));
   tweet5.setText(arr.get(9));
   tweet5.setWrapText(true);
   tweet5.setWrapText(true);

   //sixth tweet.
   handle6.setText(arr.get(10));
   tweet6.setText(arr.get(11));
   tweet6.setWrapText(true);
   tweet6.setWrapText(true);

  } catch (Exception e) {
   e.printStackTrace();
  }

 }
    
    
    
    
    
    

    //-------------------------------------Graph Code--------------------------------------------------
    @FXML
    private void loadGraph(MouseEvent event) {
        initConfidenceIndicator();

        if (graphRun == true) {
            lineChart.getData().clear();
            increment = 0;
            graphTimer.cancel();
            graphTimer.purge();


        }
        PriceTable selectedForBuy = priceTable.getSelectionModel().getSelectedItem();
        String transCompany = companycfdCol.getCellData(selectedForBuy);
        confidenceItemName.setText(transCompany);
        //int transPriceint = Integer.parseInt(pricecfdCol.getCellData(selectedForBuy).toString());
        //String transPrice = Integer.toString(transPriceint);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);
        lineChart.setTitle(transCompany);
        lineChart.setHorizontalGridLinesVisible(true);


        XYChart.Series < Number, Number > series = new XYChart.Series < Number, Number > ();
        lineChart.getData().add(series);
        graphRun = true;

        graphTimer = new Timer();
        graphTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                Platform.runLater(new Runnable() {

                    public void run() {
                        if (cryptoSelected == true) {
                            double cryptoPrice = Math.round((0 + (15000 - 4000) * random.nextDouble()) * 100) / 100.0;
                            series.getData().add(new XYChart.Data < Number, Number > (increment, cryptoPrice));
                             increment = increment +2;
                        } else if (forexSelected == true) {
                            double forexPrice = Math.round((0 + (4 - 1) * random.nextDouble()) * 100) / 100.0;
                            series.getData().add(new XYChart.Data < Number, Number > (increment, forexPrice));
                             increment = increment +2;
                        } else if (stocksSelected == true) {
                            double stocksPrice = Math.round((0 + (500 - 100) * random.nextDouble()) * 100) / 100.0;
                            series.getData().add(new XYChart.Data < Number, Number > (increment, stocksPrice));
                            increment = increment +2;
                        }

                    }
                });
            }
        }, 0, 5000);
    }



    //------------------------------End graph code here-------------------------------------------------


    private void initConfidenceIndicator() {

        Confidence c = new Confidence();
        ThreadHandler th = new ThreadHandler();
        th.thThread.start();
        c.cThread.start();
    }

    public void setConfidence(String c) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                confidenceLabel.setText(c);

            }
        });




    }



    //------------------------------------hiding cfd buttons on start up---------------------------

    private void initButtons() {
        sellCFD.setVisible(true);
        buyCFD.setVisible(true);
    }
    //------------------------------------End -----------------------------------------------------          

    //-----------------------------------Combobox to choose trading item--------------------------------

    private void initComboBox() {
        List < String > drop_list = new ArrayList < String > ();
        drop_list.add("Stock");
        drop_list.add("Forex");
        drop_list.add("Cryptocurrencies");

        ObservableList oblist_drop = FXCollections.observableList(drop_list);
        tradingitemDrop.getItems().clear();
        tradingitemDrop.setItems(oblist_drop);

        tradingitemDrop.setOnAction((event)-> {

            String item = (String) tradingitemDrop.getSelectionModel().getSelectedItem();
            if (item == "Stock") {
                cryptoSelected = false;
                forexSelected = false;
                stocksSelected = true;

                stocksTimer = new Timer();
                stocksTimer.schedule(

                    new TimerTask() {

                        @Override
                        public void run() {
                            if (stocksSelected == true) {
                                priceTable.setItems(Stock.getStockList());
                                //priceTable.refresh();
                            } else if (stocksSelected == false) {
                                stocksTimer.cancel();
                                stocksTimer.purge();
                            }
                        }
                    }, 0, 5000);

            } else if (item == "Forex") {
                cryptoSelected = false;
                forexSelected = true;
                stocksSelected = false;

                forexTimer = new Timer();
                forexTimer.schedule(

                    new TimerTask() {

                        @Override
                        public void run() {
                            if (forexSelected == true) {
                                priceTable.setItems(Forex.getForexList());
                                // priceTable.refresh();
                            } else if (forexSelected == false) {
                                forexTimer.cancel();
                                forexTimer.purge();
                            }
                        }
                    }, 0, 5000);

            } else if (item == "Cryptocurrencies") {
                cryptoSelected = true;
                forexSelected = false;
                stocksSelected = false;

                cryptoTimer = new Timer();
                cryptoTimer.schedule(

                    new TimerTask() {

                        @Override
                        public void run() {
                            if (cryptoSelected == true) {
                                priceTable.setItems(CryptoCurrency.getCryptoList());
                                //priceTable.refresh();
                            } else if (cryptoSelected == false) {
                                cryptoTimer.cancel();
                                cryptoTimer.purge();
                            }
                        }
                    }, 0, 5000);
            }
        });
    }



    //---------------------------------End Combobox---------------------------------------------------------


    //-------------Initialise Columns for price and transaction tables and load price table------------------

    private void initColumns() {
        transactionidCol.setCellValueFactory(new PropertyValueFactory < > ("transactionid"));
        itemCol.setCellValueFactory(new PropertyValueFactory < > ("item"));
        typeCol.setCellValueFactory(new PropertyValueFactory < > ("type"));
        amountCol.setCellValueFactory(new PropertyValueFactory < > ("amount"));
        timeCol.setCellValueFactory(new PropertyValueFactory < > ("time"));
        openpriceCol.setCellValueFactory(new PropertyValueFactory < > ("openprice"));
        closepriceCol.setCellValueFactory(new PropertyValueFactory < > ("closeprice"));

        companycfdCol.setCellValueFactory(new PropertyValueFactory < PriceTable, String > ("companycfdCol"));
        pricecfdCol.setCellValueFactory(new PropertyValueFactory < PriceTable, Double > ("pricecfdCol"));


    }

    //load price table data
    public void loadPriceTable() {
        stocksSelected = true;

        stocksTimer = new Timer();
        stocksTimer.schedule(

            new TimerTask() {

                @Override
                public void run() {
                    if (stocksSelected == true) {
                        priceTable.setItems(Stock.getStockList());
                    } else if (stocksSelected == false) {
                        stocksTimer.cancel();
                        stocksTimer.purge();
                    }
                }
            }, 0, 5000);
    }

    //load transaction table data
    private void loadTransactionTable() {

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
    private void refreshTransactionTable() {
        loadTransactionTable();
    }

    //---------------------------------End tables init here----------------------------------------------




    //----------------------------------loading balance ------------------------------------------------
    private void loadbalance() {

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


    public class Confidence {
        public Thread cThread;
        private double[] prices;
        private double average;
        private String indicator;
        private String type;

        public Confidence() {
            this.cThread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        //getType(); <------ GET STRING OF TYPE OF TRADING ITEM
                        type = "Cryptocurrency"; // HARDCODED AS CRYPTO
                        prices = new double[3];
                        switch (type) {
                            case "Cryptocurrency":
                                prices[0] = PriceSimulator.cryptoPrice;
                                break;
                            case "Forex":
                                prices[0] = PriceSimulator.forexPrice;
                                break;
                            case "Stock":
                                prices[0] = PriceSimulator.stockPrice;
                                break;
                            default:
                                break;
                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Confidence.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        switch (type) {
                            case "Cryptocurrency":
                                prices[1] = PriceSimulator.cryptoPrice;
                                break;
                            case "Forex":
                                prices[1] = PriceSimulator.forexPrice;
                                break;
                            case "Stock":
                                prices[1] = PriceSimulator.stockPrice;
                                break;
                            default:
                                break;
                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Confidence.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        switch (type) {
                            case "Cryptocurrency":
                                prices[2] = PriceSimulator.cryptoPrice;
                                break;
                            case "Forex":
                                prices[2] = PriceSimulator.forexPrice;
                                break;
                            case "Stock":
                                prices[2] = PriceSimulator.stockPrice;
                                break;
                            default:
                                break;
                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Confidence.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        average = (prices[0] + prices[1] + prices[2]) / 3;
                        if (prices[2] > average) {
                            indicator = "up";
                        } else if (prices[2] < average) {
                            indicator = "down";
                        } else {
                            indicator = "to stay steady";
                        }

                        String result = ("The price is likely going " + indicator);
                        setConfidence(result);
                    }
                }
            };
        }


    }

}