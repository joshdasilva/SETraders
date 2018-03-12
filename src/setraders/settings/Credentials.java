/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setraders.settings;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
//import library.assistant.alert.AlertMaker;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Josh Da Silva
 */
public class Credentials {

public static final String CONFIG_FILE = "config.txt";
String username;
String password;

    public Credentials() {
        username = "admin";
        setPassword("admin");
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        if (password.length() < 16) {
            this.password = DigestUtils.shaHex(password);
        }else
            this.password = password;
    }

    public static void initConfig() {
        Writer writer = null;
        try {
            Credentials credential = new Credentials();
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(credential, writer);
        } catch (IOException ex) {
            Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static Credentials getCredentials() {
        Gson gson = new Gson();
        Credentials credentials = new Credentials();
        try {
            credentials = gson.fromJson(new FileReader(CONFIG_FILE), Credentials.class);
        } catch (FileNotFoundException ex) {
            initConfig();
            Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
        }
        return credentials;
    }

    public static void writeCredentialsToFile(Credentials credential) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(CONFIG_FILE);
            gson.toJson(credential, writer);

           // AlertMaker.showSimpleAlert("Success", "Settings updated");
        } catch (IOException ex) {
            Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
          //  AlertMaker.showErrorMessage(ex, "Failed", "Cant save configuration file");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
