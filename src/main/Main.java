/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.Database;
import logika.FXMLDocumentController;
import logika.Kavarna;
import logika.Uzivatel;
import ui.DetailKavarna;
//import logika.Kavarna;
//import logika.Uzivatel;
import ui.PridatKavarna;
import ui.UzivatelScene;
import ui.RegistraceScene;
import ui.PrihlasitScene;
import ui.SpravceScene;
import ui.SpravceSprava;



/**
 *
 * @author monika
 */
public class Main extends Application {
    
    
    public Stage primaryStage; 
    public PrihlasitScene PS;
    public RegistraceScene RS;
    public UzivatelScene US;
    public PridatKavarna PK;
    public SpravceScene SS;
    public SpravceSprava SSp;
    public DetailKavarna DK;

    public Scene sceneUvod;
    private Kavarna kavarna; 
    
    private int idKavarny;
    public FXMLDocumentController kontroler;
    private int spravce;
    private Database databaze;
    
    
    /**
     * Metoda vytváří hlavní jeviště hry a vkládá na něj objekty.
     */ 
    @Override
    public void start(Stage primaryStage) {
        spravce = 0;
        databaze = new Database();
        
        this.primaryStage = primaryStage;
        kavarna = new Kavarna(this); //nove
        PS = new PrihlasitScene(this);
        RS = new RegistraceScene(this);
        US = new UzivatelScene(this);
        PK = new PridatKavarna(this,kavarna); //pridana kavarna
        SS = new SpravceScene(this,kavarna);
        SSp = new SpravceSprava(this);
        DK = new DetailKavarna(this, kavarna);
        
        BorderPane bp = new BorderPane();
        VBox uvod = new VBox();
        uvod.setAlignment(Pos.CENTER);
        FlowPane logo= new FlowPane(new ImageView("/zdroje/kava.png"));
        logo.setAlignment(Pos.TOP_CENTER);
        
        Label kava = new Label("Káva");
        kava.setFont(Font.font("Arial",FontWeight.BOLD, 36));
        kava.setAlignment(Pos.BOTTOM_CENTER);
        
  
        Button prihlasit = new Button();
        prihlasit.setText("Přihlášení");
        prihlasit.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        Button registrace = new Button();
        registrace.setText("Registrace");
        registrace.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        FlowPane panel = new FlowPane();
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.getChildren().addAll(prihlasit,registrace);
        
        uvod.getChildren().addAll(kava,logo,panel);
        bp.setCenter(uvod);
        
        sceneUvod = new Scene(bp, 1200, 600); // vytvoreni nove sceny a vloyeni tlacitka (pane) do sceny , sirka vyska
        primaryStage.setTitle("Káva"); // title sceny
        primaryStage.setScene(sceneUvod); // vlozeni sceny na stage 
        primaryStage.show(); // zobrazeni sceny

        
        prihlasit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               primaryStage.setScene(PS.getScena());
            }
        } );
        
        
        registrace.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(RS.getScena());
            }
        } );
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public void setSceneUS(){
        
        System.out.println(this.primaryStage.toString());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       if(args.length == 0){ //spusti v gui
            launch(args);
       }//launch(args); //zavola metodu start
    }
    public void poOdhlaseni(){
        start(primaryStage);
    }

    public int getIdKavarny() {
        return idKavarny;
    }

    public void setIdKavarny(int idKavarny) {
        this.idKavarny = idKavarny;
    }
    
    
    public void Kavarna()
    {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
       Parent root;
       try 
       {
       root = loader.load();
       kontroler = (FXMLDocumentController)loader.getController();
       kontroler.Nahraj(idKavarny,this);
       Scene scene = new Scene(root);
       primaryStage.setScene(scene);
       } 
       catch (IOException ex) 
       {
       Logger.getLogger(UzivatelScene.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    public int getSpravce() {
        return spravce;
    }

    public void setSpravce(int spravce) {
        this.spravce = spravce;
    }

    public Database getDatabaze() {
        return databaze;
    }
    
    
            
}
    

