/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import jdk.nashorn.internal.runtime.regexp.RegExp;
import logika.Uzivatel;
import main.Main;

/**
 *
 * @author monika
 */
public class RegistraceScene {
    Scene sceneRegistrovat;
    private Uzivatel uzivatel;
    private Main main;
    public String emailPatern = "([A-Za-z0-9.?]+)@([A-Za-z0-9]+).([A-Za-z]+)";
    
    
    public RegistraceScene(Main main) {
        this.main = main;
        this.uzivatel = new Uzivatel(main); // potreba vsude predavat stejnou instanci tridy main kvuli pristupu a zmene primaryStage
        BorderPane bpP = new BorderPane();
        sceneRegistrovat = new Scene(bpP, 1200, 600);
        
        GridPane gp = new GridPane();
        VBox vbL=new VBox();
        VBox vbC=new VBox();
        TextField jmenoInput = new TextField();
        PasswordField hesloInput = new PasswordField();
        PasswordField hesloZnovuInput = new PasswordField();
        TextField emailInput = new TextField();

        Button registrovat =new Button("Registrovat");
        registrovat.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        Button zpet =new Button("Zpět");
        zpet.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        FlowPane panel = new FlowPane();
        panel.getChildren().addAll(registrovat,zpet);
        
        Label kava = new Label("Registrace");
        kava.setFont(Font.font("Arial",FontWeight.BOLD, 36));
        kava.setAlignment(Pos.CENTER_RIGHT);
        VBox label = new VBox();
        label.getChildren().add(kava);
        label.setMinWidth(60);
        
        VBox logo= new VBox(new ImageView("/zdroje/kava.png"));
        logo.setMinWidth(400);
        logo.setAlignment(Pos.CENTER_LEFT);
        Label jmeno = new Label();
        Label heslo = new Label();
        Label hesloZnovu = new Label();
        Label email = new Label();

        jmenoInput.setMaxWidth(200);
        hesloInput.setMaxWidth(200);
        hesloZnovuInput.setMaxWidth(200);
        emailInput.setMaxWidth(200);

        jmeno.setText("*Jméno: ");
        jmeno.setMinHeight(25);
        heslo.setText("*Heslo: ");
        heslo.setMinHeight(25);
        hesloZnovu.setText("*Heslo znovu: ");
        hesloZnovu.setMinHeight(25);
        email.setText("*Email: ");
        email.setMinHeight(25);

        jmeno.setFont(Font.font("Arial",FontWeight.BOLD, 28));
        heslo.setFont(Font.font("Arial",FontWeight.BOLD, 28));
        hesloZnovu.setFont(Font.font("Arial",FontWeight.BOLD, 28));
        email.setFont(Font.font("Arial",FontWeight.BOLD, 28));

        vbL.getChildren().addAll(jmeno,heslo,hesloZnovu,email);
        vbL.setAlignment(Pos.TOP_RIGHT);
        vbC.getChildren().addAll(jmenoInput,hesloInput,hesloZnovuInput,emailInput,panel);

        gp.addColumn(1, vbL);
        gp.setAlignment(Pos.CENTER_LEFT);
        gp.addColumn(2, vbC);
        gp.setAlignment(Pos.CENTER_LEFT);
        
        FlowPane pad = new FlowPane();
        pad.setMinWidth(800);
        pad.setMinHeight(40);
        
        bpP.setLeft(label);
        bpP.setRight(logo);
        bpP.setTop(pad);
        bpP.setCenter(gp);
        
        registrovat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(jmenoInput.getText().length()<1 || hesloInput.getText().length()<1 || emailInput.getText().length()<1){
                    jmenoInput.clear();
                    hesloInput.clear();
                    hesloZnovuInput.clear();
                    emailInput.clear();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chybné údaje");
                    alert.setHeaderText("Musí být vyplněna všechna pole!");
                    alert.showAndWait();
                }
                else{
                    String jmeno = jmenoInput.getText();
                    String heslo = hesloInput.getText();
                    String hesloZnovu =hesloZnovuInput.getText();
                    String email = emailInput.getText();
                    jmenoInput.clear();
                    hesloInput.clear();
                    hesloZnovuInput.clear();
                    emailInput.clear();
                    if((heslo.equals(hesloZnovu)) && (email.matches(emailPatern)==true)){
                        uzivatel.registrace(jmeno, heslo,email);
                    }
                    else{
                    if(email.matches(emailPatern)==false){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chybne udaje");
                    alert.setHeaderText("Email nema spravny format. Zahrnte znak @");
                    alert.showAndWait();
                    }
                    else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chybné údaje.");
                    alert.setHeaderText("Heslo se nehsoduje s heslo znovu!");
                    alert.showAndWait();
                   }
                    
                    }
                }
               
            }
        } );
        zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                jmenoInput.clear();
                hesloInput.clear();
                hesloZnovuInput.clear();
                emailInput.clear();
                main.primaryStage.setScene(main.sceneUvod);
                
            }
        } );
    }
    
    public Scene getScena(){
        return this.sceneRegistrovat;
    }
}
