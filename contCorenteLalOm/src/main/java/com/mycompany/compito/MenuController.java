/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.compito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author om
 */
public class MenuController implements Initializable {

    @FXML
    private Text saldo_attuale;
    Scanner scanner;
    String vet_info[];

    /**
     * Initializes the controller class.
     */
    public String utente_attuale() throws FileNotFoundException {                       //FUNZIONE PER TROVARFE IL USER ATTUALE
        String path = "./src/user_attuale.txt";
        scanner = new Scanner(new File(path));
        vet_info = scanner.nextLine().split(",");
        return vet_info[0];
    }

    public void set_saldo() throws FileNotFoundException {                                //VA NEL FILE DEL UTENTE CHE HA ACCEDUTO ORA E METTE IL SANDO NELLA 
        String utente_percorso = "./src/users_salvati/" + this.utente_attuale();          //SCHERMATA
        scanner = new Scanner(new File(utente_percorso + "/info_user.txt"));
        vet_info = scanner.nextLine().split(",");
        saldo_attuale.setText(vet_info[6]);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){     
        try {
            set_saldo();                                                    //RICHINO FUNZIONE PER INDICARE IL SALDO A INZIO PAGINA
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }    

    @FXML
    private void preleva(ActionEvent event) throws IOException {
        App.setRoot("preleva");                                     //MANDA NELLA SEZIONE PRELEVA
    }

    @FXML
    private void versa(ActionEvent event) throws IOException {
        App.setRoot("versa");                                       //MANDA NELLA SEZIONE VERSA
    }

    @FXML
    private void mostra_mov(ActionEvent event) throws IOException {
        App.setRoot("movimenti");                                       //MANDA NELLA SEZIONI MOVIMENTI
    }

    @FXML
    private void log_out(ActionEvent event) throws IOException {           //LOG OUT MANDA IN LOG IN
         App.setRoot("log_in");
    }
    
}
