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
public class Log_inController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private TextField pasword;
    @FXML
    private Text error;
    private Alert warning;
    private String path = "./src/users_salvati/";

    File cartella;
    String[] lista;
    Scanner scanner;
    String path_appoggio;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        creazione_vet_utenti();                             //RICHIAMA CREAZIONE VETTORE CONTENETE GLI UTENTI
        warning = new Alert(Alert.AlertType.WARNING);       //CREAZIONE WARNING ALERT
        warning.setTitle("Error");
        warning.setHeaderText("acceso negato");
        warning.setContentText("credenziali errate\n"
                + "email o utente non disponibili");

    }

    private void creazione_vet_utenti() {                //CREAZIONE VETTORE CONTENETE GLI UTENTI
        cartella = new File(path);
        lista = cartella.list();
        scanner = new Scanner(path);
        path_appoggio = path;
    }

    private String trova_utente() throws FileNotFoundException {           //PER IDENTIFICARE UN UTENTE
        if (lista != null) {
            for (int i = 0; i < lista.length; i++) {
                path = path_appoggio;
                path = (path + lista[i] + "/info_user.txt");
                scanner = new Scanner(new File(path));
                String[] vet_info = scanner.nextLine().split(",");
                if (vet_info[3].equals(email.getText()) && vet_info[5].equals(pasword.getText())) {
                    return lista[i];
                }
            }
        }
        return "";
    }

    private void salva_utente_att(String str) throws IOException {          
        FileWriter fw = new FileWriter("./src/user_attuale.txt");   //salva il nome dell'utente che sta usando il programma ora
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(str);
        writer.close();
        App.setRoot("menu");
    }

    @FXML
    private void log_in(ActionEvent event) throws IOException {        //FA IL LOG IN DOPO VARI CONTROlLI 
        String str = trova_utente();
        if (str == "") {
            warning.show();
        } else {
            salva_utente_att(str);
        }
    }

    @FXML
    private void esci(ActionEvent event) throws IOException {           //SE ESCE RITORNA NELLA PAGINA ACCEDI
        App.setRoot("accedi");
    }

}
