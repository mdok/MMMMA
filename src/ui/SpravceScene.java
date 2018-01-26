/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Utils.Observer;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
public class SpravceScene  implements Observer{
   Scene sceneRegistrovat;
    private Uzivatel uzivatel;
    private Main main;
    private Kavarna kavarna;
    private ObservableList<Kavarna> seznamKavaren;
    private ObservableList<Kavarna> vysledky;
    private ObservableList<Kavarna> puvodniSeznam;
 
   
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
    
  
    
    
    public SpravceScene(Main main, Kavarna kavarna) {
        this.main = main;
        this.uzivatel = new Uzivatel(main);
        this.kavarna= kavarna;
        kavarna.registerObserver(this);
        
        init();
        
       
       
      
    }
    public void init(){
        bpP = new BorderPane();
        sceneRegistrovat = new Scene(bpP, 1200, 600);
        puvodniSeznam = FXCollections.observableArrayList();
     
        Button pridatKavarnu = new Button();
        pridatKavarnu.setText("Přidat kavárnu");
        pridatKavarnu.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        Button sprava = new Button();
        sprava.setText("Správa");
        sprava.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        Button odhlasit = new Button();
        odhlasit.setText("Odhlásit");
        odhlasit.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        
        
        panel = new FlowPane();
        panel.setAlignment(Pos.TOP_RIGHT);
        panel.setMinHeight(20);
        panel.getChildren().addAll(sprava,odhlasit);
        
        panelVyhledat = new FlowPane();
        panelVyhledat.setAlignment(Pos.BOTTOM_LEFT);
        
        vyhledatInput = new TextField("Zadejte dotaz");
        vyhledatInput.setFont(Font.font("Arial",FontWeight.NORMAL, 18));
        //clearovani napovedy po kliknuti
        vyhledatInput.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               if (vyhledatInput.getText().equals("Zadejte dotaz")){
                   vyhledatInput.setText("");
               }
               return; // navrat v pripade ze obsahuje uzivatelem zadany text
            }
        });
        vyhledatInput.setFocusTraversable(true);
        vyhledatInput.setMinWidth(400);
        vyhledatButton = new Button("Vyhledat");
        vyhledatButton.setFont(Font.font("Arial",FontWeight.BOLD, 18));
        
        panelVyhledat.getChildren().addAll(vyhledatInput,vyhledatButton,pridatKavarnu);
        
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
        
         pridatKavarnu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               main.primaryStage.setScene(main.PK.getScena());
            }
        } );
        
         sprava.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               main.primaryStage.setScene(main.SSp.getScena());
            }
        } );
         odhlasit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               uzivatel.odhlasit();
            }
        } );
         
         update();
         
         table.setRowFactory( tv -> {
    TableRow<Kavarna> row = new TableRow<>();
    row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            main.setSpravce(1);
            Kavarna rowData = row.getItem();
            main.setIdKavarny(rowData.getId());
            main.Kavarna();
        }
    });
    return row ;
    });

    }
     
    public Scene getScena(){
        return this.sceneRegistrovat;
    }

    @Override
    public void update() {
        System.out.println("ui.SpravceScene.update()");
//        
        seznamKavaren=kavarna.getSeznamKavaren();
        for (Kavarna seznamKavarenItem: seznamKavaren){
            puvodniSeznam.add(seznamKavarenItem);
        }
        createTable(seznamKavaren);
        bpP.setCenter(table); //kavarnaBox
        bpP.setTop(top);
    }
    
//    
    private TableView<Kavarna> createTable(ObservableList<Kavarna> seznamKavaren) {
        table = new TableView<Kavarna>();
        table.setEditable(true);
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
   
}