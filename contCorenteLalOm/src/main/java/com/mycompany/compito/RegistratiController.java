/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.compito;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author om
 */
public class RegistratiController implements Initializable {

    @FXML
    private TextField nome;
    @FXML
    private TextField codice_fiscale;
    private TextField cogniome;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField conferma_password;
    @FXML
    private TextField cognome;
    @FXML
    private TextField anno;
    @FXML
    private TextField mese;
    @FXML
    private TextField giorno;
    @FXML

    private Text error;
    private Alert warning = new Alert(Alert.AlertType.WARNING);
    private String path = "./src/users_salvati/";
    File cartella;
    String[] lista;
    Scanner scanner;
    String path_appoggio;
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        creazione_vet_utenti();
    }    

    @FXML
    private void esci(ActionEvent event) throws IOException {
        App.setRoot("accedi");
    }

    @FXML
    private void conferma(ActionEvent event) throws IOException {     //IL BUTTTON CONFERMA
        if (controlo_formato()) {
            if (trova_utente()){
                boolean success = (new File(path)).mkdir();
                path = path_appoggio + nome.getText() + "_" + cognome.getText();
                success = (new File(path)).mkdir();
                FileWriter fw = new FileWriter(path + "/info_user.txt");
                BufferedWriter writer = new BufferedWriter(fw);
                writer.write(nome.getText() + "," + cognome.getText() + "," + codice_fiscale.getText() + "," + email.getText() + "," + anno.getText() + "-" + mese.getText() + "-" + giorno.getText() + "," + password.getText() + ",0");
                writer.close();         //SCRITURA DATI E CHIUSURA
                App.setRoot("accedi");   
            }
        }
        path = "./src/users_salvati/";
    }
    
    private boolean controlo_formato() throws FileNotFoundException{       //CONTROLO ERORRI DI SINTASSI
        String text="";
        if (codice_fiscale.getText().length() != 13) {   //se codice fiscale non ha 13 caratteri 
            text=(text+"Codice fiscale deve essere composto da 13 carateri \n");       
        }
        if (!nome.getText().matches("[a-zA-Z]+")) {   //se  cogniome  non contiene lettere
            text=(text+"Il nome non puo contenere caratteri speciali o numeri \n");
        }
        if (!cognome.getText().matches("[a-zA-Z]+")){  //se  cogniome  non contiene lettere
            text=(text+"Il cogniome non puo contenere caratteri speciali o numeri\n");
        }

        if (!password.getText().equals(conferma_password.getText())) {  // se la pasw conferma è diversa da quella normale
             text=(text+"la password di conferma è diversa dalla password inserita\n");
            
        } 
        if(text!=""){
            alert_warning("SYNTAX ERROR", text);
        }
        else{
            return true;
        }
        return false;
    }

    public void alert_warning(String titolo, String conntent) {  //FUNZIONE CREAZIONE ALERT E SHOW
        warning.setTitle(titolo);
        warning.setContentText(conntent);
        warning.show();
    }
    
    
    private void creazione_vet_utenti() {       //CREAZIONE IL VETTORE CHE CONTINE GLI UTENTI ESISTENTI
        cartella = new File(path);
        lista = cartella.list();
        scanner = new Scanner(path);
        path_appoggio = path;
    }
    
    

    private boolean trova_utente() throws FileNotFoundException {   //CONTROLO SE IL IL UTENTE NON ESISTE GIà
        if(lista!=null){                        //SE NON CE NESSUN UTENTE NON FA NESSUN CONTROLO
            for (int i = 0; i < lista.length; i++) {
            path = path_appoggio; 
            path = (path + lista[i] + "/info_user.txt");
            scanner = new Scanner(new File(path));
            String[] vet_info = scanner.nextLine().split(",");
            if (this.codice_fiscale.getText().equals(vet_info[2])) {    //COMPRA LE VARIE SEZIONI EMAIL CF NOME COGNIOME
                    alert_warning("DATA ERROR", "Codice fiscale già esistente");
                    return false;
                }
                if (this.email.getText().equals(vet_info[3])) {
                    alert_warning("DATA ERROR", "Email già registrata");
                    return false;
                }
                if (this.nome.getText().equals(vet_info[0])&& this.cognome.getText().equals(vet_info[1])) {
                    alert_warning("DATA ERROR", "Nome e cogniome utente già registrato");
                    return false;
                }
            }
        } 
        return true;
    }
}
