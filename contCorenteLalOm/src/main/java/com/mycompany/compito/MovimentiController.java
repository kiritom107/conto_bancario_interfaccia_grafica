/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.compito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author om
 */
public class MovimentiController implements Initializable {

    @FXML
    private TextArea fild;
    public String vet_info[];
    private String utente_percorso;
    private Integer num=0;          // è il valore che varia e serve per aumentare il conto
    private LocalDateTime T = LocalDateTime.now();
    private DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withLocale(Locale.ITALY);
    

    /**
     * Initializes the controller class.
     */
    
    public String utente_attuale() throws FileNotFoundException{                    //PER TROVARE UTENTE ATTUALE
        Scanner scanner;  
        String path="./src/user_attuale.txt";             
        scanner = new Scanner(new File(path));
        vet_info= scanner.nextLine().split(",");
        return vet_info[0];
        
    }
    
    
    public void trasformazione_binario(){     // TRASFORMAZIONE FILE IN BINARIO PER STAMPARLO TUTTO IN 1 VOLTA
       try {     
                File crunchifyFile = new File("./src/users_salvati/"+utente_attuale()+"/movimenti.txt");
		FileInputStream fileInputStream;
            
                fileInputStream = new FileInputStream(crunchifyFile);
                byte[] crunchifyValue = new byte[(int) crunchifyFile.length()];	

                fileInputStream.read(crunchifyValue);  
                fileInputStream.close();
                this.vet_info = new String(crunchifyValue, "UTF-8").split("\n");	
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {                
                trasformazione_binario();                               //APPENA INZIA LA SCHERMATA RICHIAMO LA FUNZIONE CHE TRASFORMA TUTTO IN BINARIO
    }    

    @FXML
    private void ultimi_5(ActionEvent event) {                          //STAMPA GLI ULTIMI 5
        fild.clear();
        if(vet_info.length<=5) {
            stampa_all();
        }
        else {
            for (int i=vet_info.length-5; i<vet_info.length; i++){
                fild.setText(fild.getText()+this.vet_info[i]+"\n");
            }
        }    
    }
   
    public LocalDateTime trova_data(String str){                        //TRASFORMA UN STRINGA IN UN LOCALDATETIME
        String vet[]= str.split(" ");
        String data= (vet[1]+" "+vet[2]);
        return LocalDateTime.parse(data, formatter);   
    }   
    
    
    @FXML
    private void tutti(ActionEvent event) {                             //STAMPA TUTTI
        stampa_all();
    }
    
    private void stampa_all() {                                     //FUNZIONE PER STAMPA DI TUTTI MOVIMENTI
        fild.clear();
        String text="";
        for (int i=0; i<vet_info.length; i++) 
        {
            text=(text+vet_info[i]+"\n");
        }
	fild.setText(text);
    }

    private void stampa_con_eccezione(int num) {                           //STAMPA CON ECCEZIONE
        fild.clear();
        String text = "";
        LocalDateTime date = LocalDateTime.now().minusDays(num);
        for (int i = 0; i < vet_info.length; i++) {
            if(trova_data(vet_info[i]).isAfter(date)){
                text = (text + vet_info[i] + "\n");
            } 
        }
        fild.setText(text);
    }

    @FXML
    private void esci(ActionEvent event) throws IOException {     //MANDA NELLA SEZIONE MENU
        App.setRoot("menu");
    }

    @FXML
    private void ultimi_7giorni(ActionEvent event) {
        stampa_con_eccezione(7);                                    //STAMPA SOLO I MOVIMENTI NELL'ARCO DI 7 GIORNI
    }

    @FXML
    private void ultimi_30giorni(ActionEvent event) {
        stampa_con_eccezione(30);                                   //STAMPA SOLO I MOVIMENTI NELL'ARCO DI 30 GIORNI
    }
}
