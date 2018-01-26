/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class UzivatelScene {
   Scene sceneRegistrovat;
    private Uzivatel uzivatel;
    private Main main;
    private Kavarna kavarna;
    private ObservableList<Kavarna> seznamKavaren = FXCollections.observableArrayList();
    
    
    private ObservableList<Kavarna> vysledky = FXCollections.observableArrayList();
    
   
    private BorderPane bpP ;
    private FlowPane panel;
    private FlowPane panelVyhledat;
    private VBox top;
    private TextField vyhledatInput;
    private Button vyhledatButton;
    private FlowPane logo;
    
    private TableView<Kavarna> table;
    private TableColumn<Kavarna, String> sloupecNazev;
    private TableColumn<Kavarna, String> sloupecPopis;
    private TableColumn<Kavarna, String> sloupecDetail;

    private List<Kavarna> detailK;

    
    
    public UzivatelScene(Main main) {
        this.main = main;
        this.uzivatel = new Uzivatel(main);
        this.kavarna= new Kavarna(main);
        this.seznamKavaren = kavarna.getSeznamKavaren();
        
        
        
        init();
        
       
       
      
    }
    private void init(){
        
        BorderPane bpP = new BorderPane();
        sceneRegistrovat = new Scene(bpP, 1200, 600);
       
        Button odhlasit = new Button("Odhlásit");
        odhlasit.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        panel = new FlowPane();
        panel.setAlignment(Pos.TOP_RIGHT);
        panel.setMinHeight(20);
        panel.getChildren().addAll(odhlasit);
        
        panelVyhledat = new FlowPane();
        panelVyhledat.setAlignment(Pos.BOTTOM_LEFT);
        
        vyhledatInput = new TextField("Zadejte dotaz");
        vyhledatInput.setMinWidth(400);
        vyhledatInput.setFont(Font.font("Arial",FontWeight.NORMAL, 18));

        vyhledatInput.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
               if (vyhledatInput.getText().equals("Zadejte dotaz")){
                   vyhledatInput.setText("");
               }
               return; // navrat v pripade ze obsahuje uzivatelem zadany text
            }
        });
        
        vyhledatButton = new Button("Vyhledat");
        vyhledatButton.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        
        panelVyhledat.getChildren().addAll(vyhledatInput,vyhledatButton);
        
        logo= new FlowPane(new ImageView("/zdroje/kava_mini.png"));
        logo.setAlignment(Pos.BASELINE_LEFT);
        
        top = new VBox();
        top.getChildren().addAll(panel,logo,panelVyhledat);
        
         vyhledatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               String hledanyVyraz = vyhledatInput.getText(); 
               ObservableList<Kavarna> vysledky = FXCollections.observableArrayList();
               
               vysledky = main.getDatabaze().najdiKavarny(hledanyVyraz);
                
               table.setItems(vysledky);
            }
        } );
         odhlasit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               uzivatel.odhlasit();
            }
        } );
        
//    
        seznamKavaren=kavarna.getSeznamKavaren();
        createTable(seznamKavaren);
        bpP.setCenter(table); //kavarnaBox
        bpP.setTop(top);

        
        
       table.setRowFactory( tv -> {
    TableRow<Kavarna> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            main.setSpravce(0);
            Kavarna rowData = row.getItem();
            main.setIdKavarny(rowData.getId());
            main.Kavarna();
        }
    });
    return row ;
    });
        
        
        
    }
    
    
    public List<Kavarna> getterT(){
    	Kavarna kavarnaT = table.getSelectionModel().selectedItemProperty().get();
   	 	DetailKavarna detail = new DetailKavarna(main,kavarnaT);
   	 	detail.nazevD = kavarnaT.getNazev();
   	 	System.out.println(detail.nazevD);
   	 	detailK.add(kavarnaT);
    	System.out.println(detailK);
    	return new ArrayList<Kavarna> (detailK);
    }
    /*
    public String getterT(){
    	Kavarna kavarnaT = table.getSelectionModel().selectedItemProperty().get();
   	 	nazevD = kavarnaT.getNazev();
    	return nazevD;
    	
    	
    }
    */
    private TableView<Kavarna> createTable(ObservableList<Kavarna> seznamKavaren) {
        table = new TableView<Kavarna>();
        table.setEditable(true);
        //seznamKavaren=kavarna.getSeznamKavaren();
        sloupecNazev = new TableColumn("Název");
        sloupecNazev.setEditable(false);
        sloupecNazev.setPrefWidth(150);
        sloupecPopis = new TableColumn("Popis");
        sloupecPopis.setPrefWidth(950);
        sloupecPopis.setEditable(false);
        sloupecDetail = new TableColumn("Detail");
        sloupecDetail.setPrefWidth(100);
        sloupecDetail.setEditable(true);
        
        sloupecNazev.setCellValueFactory(new PropertyValueFactory<Kavarna, String>("nazev"));
        sloupecPopis.setCellValueFactory(new PropertyValueFactory<Kavarna, String>("popis"));
        sloupecDetail.setCellValueFactory(c -> new SimpleStringProperty("detail"));
        
       
        
        
        
        table.setItems(seznamKavaren);
        table.getColumns().addAll(sloupecNazev, sloupecPopis, sloupecDetail);
        return table;
    }
     /*
    public String getNazev()
    {
        // Get selected item
        Kavarna kavarnaT = table.getSelectionModel().selectedItemProperty().get();
        nazevD = kavarnaT.getNazev();
        return nazevD;
        
    }
    */
    public Scene getScena(){
        return this.sceneRegistrovat;
    }
}