/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import logika.Uzivatel;
import main.Main;

/**
 *
 * @author monika
 */
public class PrihlasitScene extends GridPane {
    private Scene scenePrihlasit;
    private BorderPane bpP;
    private Uzivatel uzivatel;
    private Main main;

    /**
     *
     * @param main
     */
    public PrihlasitScene(Main main ){
        this.main = main;
        this.uzivatel = new Uzivatel(main); // potreba vsude predavat stejnou instanci tridy main kvuli pristupu a zmene primaryStage
        BorderPane bpP = new BorderPane();
        scenePrihlasit = new Scene(bpP, 1200, 600);
        
        GridPane gp = new GridPane();
        VBox vbL=new VBox();
        VBox vbC=new VBox();
        TextField jmenoInput = new TextField();
        PasswordField hesloInput = new PasswordField();
        Button odeslat =new Button("Odeslat");
        odeslat.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        Button zpet =new Button("Zpět");
        zpet.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        
        FlowPane panel = new FlowPane();
        panel.getChildren().addAll(odeslat,zpet);
        
        Label kava = new Label("Přihlášení");
        kava.setAlignment(Pos.CENTER_RIGHT);
        kava.setFont(Font.font("Arial",FontWeight.BOLD, 36));
        VBox label = new VBox();
        label.getChildren().add(kava);
        label.setMinWidth(60);
        
        VBox logo= new VBox(new ImageView("/zdroje/kava.png"));
        logo.setMinWidth(500);
        logo.setAlignment(Pos.CENTER_LEFT);
        
        Label jmeno = new Label();
        Label heslo = new Label();
        
        jmenoInput.setMaxWidth(200);
        hesloInput.setMaxWidth(200);

        jmeno.setText("*Jméno: ");
        heslo.setText("*Heslo: ");
        jmeno.setFont(Font.font("Arial",FontWeight.BOLD, 26));
        heslo.setFont(Font.font("Arial",FontWeight.BOLD, 26));

        vbL.getChildren().addAll(jmeno,heslo);
        vbC.getChildren().addAll(jmenoInput,hesloInput,panel);

        gp.addColumn(1, vbL);
        gp.setAlignment(Pos.CENTER_LEFT);
        gp.addColumn(2, vbC);
        gp.setAlignment(Pos.CENTER_LEFT);
        
        FlowPane pad = new FlowPane();
        pad.setMinWidth(800);
        pad.setMinHeight(40);
        
        bpP.setLeft(label);
        bpP.setRight(logo);
        bpP.setCenter(gp);
        bpP.setTop(pad);
        
        odeslat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String jmeno = jmenoInput.getText();
                String heslo = hesloInput.getText();
                jmenoInput.clear();
                hesloInput.clear();
                uzivatel.prihlasit(jmeno, heslo);
                
            }
        } );
        zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jmenoInput.clear();
                hesloInput.clear();
                main.primaryStage.setScene(main.sceneUvod);
                
            }
        } );

    }

    /**
     *
     * @return
     */
    public Scene getScena(){
        return this.scenePrihlasit;
    }
    
}
