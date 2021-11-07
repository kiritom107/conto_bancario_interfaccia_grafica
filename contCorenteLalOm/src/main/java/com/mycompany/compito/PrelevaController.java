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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
public class PrelevaController implements Initializable {

    @FXML
    private Text saldo_attuale;
    @FXML
    private TextField importo;
    private int int_textfileld = 0;
    private int saldo_in_banca = 0;
    private String utente_percorso;
    private String vet_info[];
    private LocalDateTime T = LocalDateTime.now();
    private DateTimeFormatter stampa_data_ita = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withLocale(Locale.ITALY);
    private FileWriter fw;
    private BufferedWriter writer;
    Scanner scanner;
    private Alert warning;

    /**
     * Initializes the controller class.
     */
    public String utente_attuale() throws FileNotFoundException {                   //TROVA L'UTENTE ATTUALE
        String path = "./src/user_attuale.txt";
        scanner = new Scanner(new File(path));
        vet_info = scanner.nextLine().split(",");
        return vet_info[0];
    }

    public void set_saldo() throws FileNotFoundException {                          //   IMPOSTA IL SALDO SULLA SCHERMATA
        utente_percorso = "./src/users_salvati/" + this.utente_attuale();
        scanner = new Scanner(new File(utente_percorso + "/info_user.txt"));
        vet_info = scanner.nextLine().split(",");
        saldo_attuale.setText(vet_info[6]);
        this.saldo_in_banca = Integer.parseInt(vet_info[6]);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {                            
        warning = new Alert(Alert.AlertType.WARNING);
        try {
            set_saldo();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void num_update(int saldo) {                                            //INCREMENTA NUMERO 
        control_error();
        if(this.importo.getText().equals("0")){
            this.importo.setText(String.valueOf(saldo));
        }
        else{
            int_textfileld = Integer.parseInt(this.importo.getText());
            int_textfileld += saldo;
            importo.setText(String.valueOf(int_textfileld));
        }
    }

    @FXML
    private void preleva_50(ActionEvent event) {
        num_update(50);
    }

    @FXML
    private void preleva_100(ActionEvent event) {
        num_update(100);
    }

    @FXML
    private void preleva_150(ActionEvent event) {
        num_update(150);
    }

    @FXML
    private void preleva_200(ActionEvent event) {
        num_update(200);
    }

    @FXML
    private void preleva_250(ActionEvent event) {
        num_update(250);
    }

    private void control_error() {                                         //CONTROLLA L'ERRORE NEL CASO IL TEXT NON CONTENNESE NULLA OPPURE UNA
        if (this.importo.getText().equals("")) {                           //O PIU LETTERE
            this.importo.setText("0");
        }
        if (!this.importo.getText().matches("[0-9]+")) {
            this.importo.setText("0");
            alert_warning("error lettere", "error lettere", "le lettere non possono essere contenute");
        }
    }

    public void alert_warning(String titolo, String heder, String conntent) {       //FUNZIONE GENERICA PER ALERT
        warning.setTitle(titolo);
        warning.setHeaderText(heder);
        warning.setContentText(conntent);
        warning.show();
    }


    private void salva_movimento() throws IOException {         //salvataggio del movimento nel file movimenti
        fw = new FileWriter(utente_percorso + "/movimenti.txt", true);
        writer = new BufferedWriter(fw);
        writer.write("Prelievo " + LocalDateTime.now().format(stampa_data_ita) + " " + int_textfileld + "\n");
        writer.close();
    }

    private void aggiorna_saldo() throws IOException {           //aggiornamento del saldo
        fw = new FileWriter(utente_percorso + "/info_user.txt");
        writer = new BufferedWriter(fw);
        for (int i = 0; i < 6; i++) {
            System.out.println(this.vet_info[i]);
            writer.write(vet_info[i] + ",");
        }
        writer.write(String.valueOf(this.saldo_in_banca));
        writer.close();
    }

    @FXML
    private void conferma(ActionEvent event) throws IOException {
        control_error();
        int_textfileld = Integer.parseInt(this.importo.getText());
        if (this.saldo_in_banca - int_textfileld >= 0 && int_textfileld != 0) {
            this.saldo_in_banca -= int_textfileld;
            salva_movimento();
            aggiorna_saldo();
            App.setRoot("preleva");
        }
        else{
            alert_warning("ERRORE PRELIEVO","Ricontrolla i dati inseriti","-1) controla il valroe prelievo non sia composto da lettere \n"
                    + "-2) controla che il importo inserito non superi il tuo saldo nel conto" );
        }
    }

    @FXML
    private void esci(ActionEvent event) throws IOException {
        App.setRoot("menu");
    }

}
