/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.Kavarna;
import logika.Uzivatel;
import main.Main;

/**
 *
 * @author monika
 */
public class PridatKavarna {
    private Main main;
    private Scene sceneKavarnaPridat;
    private Kavarna kavarna;

    public PridatKavarna(Main main,Kavarna kavarna){
        this.main = main;
        this.kavarna = kavarna;//new Kavarna(main); // potreba vsude predavat stejnou instanci tridy main kvuli pristupu a zmene primaryStage
        init();
        
        
    }     
        
    private void init(){
        //kavarna.databazovaFunkce("GET", "SELECT * FROM kava.kavarny");
        
        BorderPane bpP = new BorderPane();
        sceneKavarnaPridat = new Scene(bpP, 1200, 600);
        
        GridPane gp = new GridPane();
        VBox vbL=new VBox();
        VBox vbC=new VBox();
        TextField nazevInput = new TextField();
        TextField adresaInput = new TextField();
        TextField popisInput = new TextField();
       
        popisInput.setMinWidth(500);
        TextField kavaNabidkaInput = new TextField();
        
        Label label = new Label();
        label.setMinWidth(50);
        Label nazev = new Label();
        Label adresa = new Label();
        Label popis = new Label();
        Label kavaNabidka = new Label();
        
        nazev.setText("Název: ");
        nazev.setFont(Font.font("Arial",FontWeight.BOLD, 28));
        adresa.setText("Adresa: ");
        adresa.setFont(Font.font("Arial",FontWeight.BOLD, 28));
        popis.setText("Popis: ");
        popis.setFont(Font.font("Arial",FontWeight.BOLD, 28));
        kavaNabidka.setText("Nabídka: ");
        kavaNabidka.setFont(Font.font("Arial",FontWeight.BOLD, 28));

        label.setText("");

        Button ulozit =new Button("Uložit");
        ulozit.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        Button zpet =new Button("Zpět");
        zpet.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        FlowPane panel = new FlowPane();
        panel.getChildren().addAll(zpet,ulozit);
        vbL.getChildren().addAll(nazev,adresa,popis,kavaNabidka);
        vbC.getChildren().addAll(nazevInput,adresaInput,popisInput,kavaNabidkaInput, ulozit,zpet);

        gp.addColumn(1, vbL);
        gp.addColumn(2, vbC);

        bpP.setCenter(gp);
        bpP.setRight(label);
        
         ulozit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String nazev = nazevInput.getText();
                String adresa = adresaInput.getText();
                String popis = popisInput.getText();
                String kavaNabidka = kavaNabidkaInput.getText();
                nazevInput.clear();
                adresaInput.clear();
                popisInput.clear();
                kavaNabidkaInput.clear();
                kavarna.pridatKavarnu(nazev,adresa,popis,kavaNabidka);
            }
        } );
         zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nazevInput.clear();
                adresaInput.clear();
                popisInput.clear();
                kavaNabidkaInput.clear();
               main.primaryStage.setScene(main.SS.getScena());
            }
        } );    }
     public Scene getScena(){
        return this.sceneKavarnaPridat;
    }
}
