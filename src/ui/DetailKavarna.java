/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.Kavarna;
import main.Main;


/**
 *
 * @author monika
 */
public class DetailKavarna {
	private Main main;
    private Scene sceneKavarnaDetail;
    private Kavarna kavarna;
    private ObservableList<Kavarna> seznamKavaren = FXCollections.observableArrayList();
    public String nazevD;
    public DetailKavarna(Main main,Kavarna kavarna){
        this.main = main;
        this.kavarna = kavarna;//new Kavarna(main); // potreba vsude predavat stejnou instanci tridy main kvuli pristupu a zmene primaryStage
        init();
	
	
	
}

    
    private void init(){
    	 
    	
    	
    	seznamKavaren = kavarna.getSeznamKavaren();
    	BorderPane bpP = new BorderPane();
        sceneKavarnaDetail = new Scene(bpP, 1200, 600);
        
        GridPane gp = new GridPane();
        VBox vbL=new VBox();
        VBox vbC=new VBox();
    	
        
        Label nazev = new Label();
        Label adresa = new Label();
        Label popis = new Label();
        Label kavaNabidka = new Label();
        
        
        //System.out.println(UzivatelScene.getterT());
        
        nazev.setText("Nazev: ");
        adresa.setText("Adresa: ");
        popis.setText("Popis: ");
        kavaNabidka.setText("Kava: ");
    	
        Button zpet =new Button("Zpet");
        zpet.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        FlowPane panel = new FlowPane();
        panel.getChildren().addAll(zpet);
        vbL.getChildren().addAll(nazev,adresa,popis,kavaNabidka);
        vbC.getChildren().addAll(zpet);

        gp.addColumn(1, vbL);
        gp.addColumn(2, vbC);

        bpP.setCenter(gp);
        zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               main.primaryStage.setScene(main.US.getScena());
            }
        } );    }
    public Scene getScena(){
        return this.sceneKavarnaDetail;
    }

    
    

}