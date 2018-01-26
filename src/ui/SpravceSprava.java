/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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
 * @author Monika
 */
public class SpravceSprava extends GridPane{
   Scene sceneSprava;
    private Uzivatel uzivatel;
    private Main main;
    private List<Uzivatel> seznamUzivatelu;
    private VBox kavarnyBox;
    private FlowPane kavarnaZaznam;
    private List<Uzivatel> seznamSpravcu;
    private List<Uzivatel> seznamNeSpravcu;
    private ComboBox cbUzivatele;
    private ComboBox cbSpravci;
    private VBox vbLspravci;
    private VBox vbLuzivatele;


    
  
    
    
    public SpravceSprava(Main main) {
        this.main = main;
        this.uzivatel = new Uzivatel(main);
        this.seznamUzivatelu = uzivatel.getSeznamUzivatelu();
        
        init();
        update();
       
        
       
       
      
    }
    private void init(){
        GridPane uzivateleBox = new GridPane();
        GridPane spravciBox = new GridPane();
        BorderPane bpP = new BorderPane();
        sceneSprava = new Scene(bpP, 1200, 600);
        vbLuzivatele=new VBox();
        VBox vbRuzivatele=new VBox(); // pro make spravce button
        vbLspravci=new VBox();
        VBox vbRspravci=new VBox(); // pro odeber spravce button
        cbUzivatele = new ComboBox();
        cbSpravci = new ComboBox();
        seznamSpravcu = new ArrayList();
        seznamNeSpravcu = new ArrayList();
        Label sprava = new Label("Správa");
        sprava.setFont(Font.font("Arial",FontWeight.BOLD, 36));
        sprava.setAlignment(Pos.TOP_CENTER);
        VBox label = new VBox();
        label.getChildren().add(sprava);
        label.setMinWidth(60);
        
        VBox logo= new VBox(new ImageView("/zdroje/kava.png"));
        logo.setAlignment(Pos.BASELINE_LEFT);
        
        
        Label uzivatelJmeno = new Label();
        Label pridat = new Label();
        Label spravceJmeno = new Label();
        Label odebrat = new Label();
        
        uzivatelJmeno.setText("Uživatelé: ");
        uzivatelJmeno.setMinWidth(800);
        spravceJmeno.setText("Správci: ");
        spravceJmeno.setMinWidth(800);

        
        uzivatelJmeno.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        pridat.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        spravceJmeno.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        odebrat.setFont(Font.font("Arial",FontWeight.BOLD, 18));

       

        Button zpet = new Button();
        zpet.setText("Zpět");
        zpet.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        
        Button ulozit = new Button();
        ulozit.setText("Uložit");
        ulozit.setFont(Font.font("Arial",FontWeight.BOLD, 18));
       
        vbLuzivatele.getChildren().addAll(uzivatelJmeno,cbUzivatele);
        vbLspravci.getChildren().addAll(spravceJmeno,cbSpravci);

        uzivateleBox.addColumn(1, vbLuzivatele);
        uzivateleBox.addColumn(3, vbRuzivatele);
        uzivateleBox.getColumnConstraints().add(new ColumnConstraints(100)); 
        uzivateleBox.getColumnConstraints().add(new ColumnConstraints(200)); 

        
        spravciBox.addColumn(1, vbLspravci);
        spravciBox.addColumn(2, vbRspravci);
        spravciBox.getColumnConstraints().add(new ColumnConstraints(100)); 
        spravciBox.getColumnConstraints().add(new ColumnConstraints(200)); 
        
        FlowPane panel = new FlowPane();
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.setMinHeight(40);
        
        zpet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               cbSpravci.setValue("");
               cbUzivatele.setValue("");
               main.primaryStage.setScene(main.SS.getScena());
            }
        } );
        ulozit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               for(Uzivatel SeznamUzivateluItem: seznamUzivatelu){
                   if(SeznamUzivateluItem.getJmeno().equals(cbUzivatele.getValue())){
                       SeznamUzivateluItem.setJeSpravce(Boolean.TRUE);
                       String jmeno = SeznamUzivateluItem.getJmeno();
                       uzivatel.update(Boolean.TRUE, jmeno);
                   }
                   else{
                       if(SeznamUzivateluItem.getJmeno().equals(cbSpravci.getValue())){
                           SeznamUzivateluItem.setJeSpravce(Boolean.FALSE);
                           String jmeno = SeznamUzivateluItem.getJmeno();
                           uzivatel.update(Boolean.FALSE, jmeno);
                       }
                   }
               }
               update();
               
               main.primaryStage.setScene(main.SS.getScena());
            }
        } );
       panel.getChildren().addAll(zpet,ulozit);
       Label uzivatelScene;
       uzivatelScene = new Label("Scéna uživatele");
       VBox listy =new VBox();
       FlowPane vypln = new FlowPane(new Label(""));
       vypln.minHeight(300);
       listy.getChildren().addAll(uzivateleBox,vypln,spravciBox);
       listy.setAlignment(Pos.CENTER);
       uzivateleBox.minHeight(100);
       spravciBox.minHeight(100);
       cbSpravci.minHeight(100);
       cbUzivatele.minHeight(100);

        bpP.setTop(label);
        bpP.setRight(logo);
        bpP.setCenter(listy);
        bpP.setBottom(panel);
        
        
    }
    public void update(){
     cbSpravci.getItems().clear();
     cbUzivatele.getItems().clear();
     for (Uzivatel seznamUzivateluItem : seznamUzivatelu) {
            
            if(seznamUzivateluItem.getJeSpravce()==false){
            cbUzivatele.getItems().add(seznamUzivateluItem.getJmeno());
             
            }
            else{
            cbSpravci.getItems().add(seznamUzivateluItem.getJmeno());
            }
        } 
    }
    
    public Scene getScena(){
        return this.sceneSprava;
    }
}