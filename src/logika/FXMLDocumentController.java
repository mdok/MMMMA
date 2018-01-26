/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.Main;


public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane pKavarna,pHodnoceni,pUprava;
    @FXML
    private Button uUloz,uZpet,hUloz,hZpet,kPridej,kZpet,kUprav;
    @FXML
    private TextField uNazev,uAdresa;
    @FXML
    private TextArea uPopis,uMenu,hKomentar;
    @FXML
    private ComboBox hCombo;
    @FXML
    private Label lNazev,lAdresa,lHodnoceni,lPopis,lMenu;
    @FXML
    public TableView<Hodnoceni> tHodnoceni;
    @FXML
    public TableColumn<Hodnoceni,String> komentar;
    @FXML
    public TableColumn<Hodnoceni,Integer> hodnoceni;
    @FXML
    public TableColumn<Hodnoceni,LocalDate> datum;
    
    private ObservableList<Integer> hvezdicky = FXCollections.observableArrayList();
    private ObservableList<Hodnoceni> listHodnoceni = FXCollections.observableArrayList();
    
    
    private Database databaze;
    private int idKavarny;
    private Main main;
    
    @FXML
    public void Zpet()
    {
        pHodnoceni.setVisible(false);
        pUprava.setVisible(false);
        hKomentar.setText("");
        pKavarna.setVisible(true);
    }
    @FXML
    public void ZpetDoMenu()
    {
        if(main.getSpravce() == 1)
        {
            main.primaryStage.setScene(main.SS.getScena());
            kUprav.setVisible(false);
        }
        if(main.getSpravce() == 0)
        {
            main.primaryStage.setScene(main.US.getScena());
            kUprav.setVisible(false);
        }
    }
    @FXML
    public void PridejHodnoceni()
    {
        pKavarna.setVisible(false);
        pHodnoceni.setVisible(true);
    }
    @FXML
    public void Pridej()
    {
        String pomocny = hCombo.getSelectionModel().getSelectedItem().toString();
        int hvezda = Integer.parseInt(pomocny);
        databaze.pridejHodnoceni(hvezda, hKomentar.getText(), LocalDate.now(),idKavarny);
        vypocitejHodnoceni();
        Zpet();
    }
    @FXML
    public void UpravKavarnu()
    {
        pKavarna.setVisible(false);
        pUprava.setVisible(true);
        
        Kavarna upravena = new Kavarna();
        upravena = databaze.getKavarna(idKavarny);
        
        uNazev.setText(upravena.getNazev());
        uAdresa.setText(upravena.getAdresa());
        uPopis.setText(upravena.getPopis());
        uMenu.setText(upravena.getKavaNabidka());
    }
    @FXML
    public void Uprav()
    {
        databaze.upravKavarnu(idKavarny, uNazev.getText(), uAdresa.getText(), uPopis.getText(), uMenu.getText());
        lNazev.setText(uNazev.getText());
        lAdresa.setText(uAdresa.getText());
        lPopis.setText(uPopis.getText());
        lMenu.setText(uMenu.getText());
        Zpet();
    }
    @FXML
    public void Nahraj(int id,Main main)
    {
        this.main = main;
        idKavarny = id;
        databaze = main.getDatabaze();
        Kavarna pom = databaze.getKavarna(id);
        
        if(main.getSpravce() == 1)
        {
            kUprav.setVisible(true);
        }
        if(main.getSpravce() == 0)
        {
            kUprav.setVisible(false);
        }
        
        lNazev.setText(pom.getNazev());
        lAdresa.setText(pom.getAdresa());
        lPopis.setText(pom.getPopis());
        lMenu.setText(pom.getKavaNabidka());
        vypocitejHodnoceni();
    }
    @FXML
    public void vypocitejHodnoceni()
    {
        listHodnoceni = databaze.listHodnoceni(idKavarny);
        tHodnoceni.setItems(listHodnoceni);
        
        Double pomocneCislo = 0.0;
        int pocet = listHodnoceni.size();
        for(Hodnoceni hod : listHodnoceni)
        {
          pomocneCislo += hod.getHodnoceni();
        }
        pomocneCislo = pomocneCislo / pocet;
        
        
        if(pocet!=0)
        {
            lHodnoceni.setText(String.valueOf(pomocneCislo));
        }
        else
        {
            lHodnoceni.setText("Kavárna zatím nemá žádné hodnocení.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        hCombo.setItems(hvezdicky);
        hvezdicky.add(1);
        hvezdicky.add(2);
        hvezdicky.add(3);
        hvezdicky.add(4);
        hvezdicky.add(5);
        
        
        
       komentar.setCellValueFactory(new PropertyValueFactory<Hodnoceni,String>("komentar")); 
       hodnoceni.setCellValueFactory(new PropertyValueFactory<Hodnoceni,Integer>("hodnoceni"));
       datum.setCellValueFactory(new PropertyValueFactory<Hodnoceni,LocalDate>("datum"));
    }    
    
}
