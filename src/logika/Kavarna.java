/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import Utils.Observer;
import Utils.Subject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.Main;

/**
 *
 * @author Monika
 */
public class Kavarna implements Subject{
    private int id;
    private String nazev;
    private String adresa;
    private String popis;
    private String kavaNabidka;
    private int chutnalo;
    private int nechutnalo;
    public ObservableList<Kavarna> seznamKavaren;
    private Main main;
    private Uzivatel uzivatel;
    private List<Observer> listObserveru = new ArrayList<Observer>();
    
    //pripojeni do db
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String dbConnection = "jdbc:mysql://127.0.0.1:3306/kava?user=root";
    private static final String dbUser = "root";
    private static final String dbPassword = ""; 
    

    public Kavarna (Main main){
        this.main = main;
        seznamKavaren = FXCollections.observableArrayList();
        System.out.println("main_v_kave");
        databazovaFunkce("GET", "SELECT * FROM kava.kavarny");
        
    }

    public Kavarna() {
    }
    
    
          
    public Kavarna(int id, String nazev, String adresa, String popis, String kavaNabidka, int chutnalo, int nechutnalo ){
        this.id = id;
        this.nazev = nazev;
        this.adresa = adresa;
        this.popis = popis;
        this.kavaNabidka = kavaNabidka;
        this.chutnalo = chutnalo;
        this.nechutnalo = nechutnalo;
       
       
    }  
    public void update(){
        System.out.println("logika.Kavarna.update()");
        System.out.println(this.listObserveru);
        this.notifyAlllObservers();
        main.primaryStage.setScene(main.SS.getScena());
    
        
    }
    




    
    
    public void pridatKavarnu(String nazev, String adresa, String popis, String kavaNabidka){ 
//        for (Kavarna seznamKavarenItem : seznamKavaren) {
//            if (seznamKavarenItem.getNazev().equals(nazev)){ 
//               System.out.println("Kavarna s timto nazvem jiz existuje");
//          
//                
//            }
//            else{
                
                this.nazev=nazev;
                this.adresa= adresa;
                this.popis= popis;
                this.kavaNabidka=kavaNabidka;
                this.chutnalo=0;
                this.nechutnalo=0;
                System.out.println(nazev);
                
                seznamKavaren.add(new Kavarna(0,nazev,adresa,popis,kavaNabidka, chutnalo, nechutnalo));
       
                databazovaFunkce("INSERT", "INSERT INTO kava.kavarny (nazev, adresa, popis, kavaNabidka) VALUES (?,?,?,?)"); 
              
                
//            }
            
//        }
     
    }
    
    //DB
    public void databazovaFunkce(String funkce, String parametr) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {

//          Otevreni komunikace do databaze
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(dbConnection, dbUser, dbPassword);

//          provedeni dotazu
            ResultSet rs = null;
            
            if (funkce.equals("GET")) {

                statement = connection.prepareStatement(parametr);

                rs = statement.executeQuery();

                nactiZDB(rs);
                rs.close();
            }

            if (funkce.equals("INSERT")) {

                statement = connection.prepareStatement(parametr);
                
               statement.setString(1, nazev);
               statement.setString(2, adresa);
               statement.setString(3, popis);
               statement.setString(4, kavaNabidka);
               //statement.setInt(5, chutnalo);
              // statement.setInt(6, nechutnalo);
        
                statement.executeUpdate();
                update();
                
            }
            
            if (funkce.equals("UPDATE")) {

                statement = connection.prepareStatement(parametr);
             
                statement.executeUpdate();
            }

//              uzavreni spojeni
            statement.close();
            connection.close();

        } catch ( NullPointerException | ClassNotFoundException ex) { //SQLException |

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println(ex.getMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("DB");
                    alert.setHeaderText("Chyba připojení");

                    alert.showAndWait();
                    System.out.println(ex.getMessage());
                }
            });
        }
            catch ( SQLException ex ) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chybné údaje");
                    alert.setHeaderText("Kavárna s daným názvem již exituje!");
                    alert.setContentText("Prosím zadejte jiný.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                           main.primaryStage.setScene(main.PK.getScena());
                    } 
                }
            });

        

        } finally {

            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }

        }

    }
    public void nactiZDB(ResultSet rs) {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nazev = rs.getString("nazev");
                String adresa = rs.getString("adresa");
                String popis = rs.getString("popis");
                String kavaNabidka = rs.getString("kavaNabidka");

               // seznamKavaren.add(new Kavarna(id, nazev, adresa, popis, kavaNabidka,0,0));
                seznamKavaren.add(new Kavarna(id, nazev, adresa, popis, kavaNabidka,0,0));

            }
        } catch (SQLException ex) {
            Logger.getLogger(Uzivatel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public int getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getPopis() {
        return popis;
    }

    public String getKavaNabidka() {
        return kavaNabidka;
    }

    public int getChutnalo() {
        return chutnalo;
    }

    public int getNechutnalo() {
        return nechutnalo;
    }

    public ObservableList<Kavarna> getSeznamKavaren() {
        return seznamKavaren;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setKavaNabidka(String kavaNabidka) {
        this.kavaNabidka = kavaNabidka;
    }

    public void setChutnalo(int chutnalo) {
        this.chutnalo = chutnalo;
    }

    public void setNechutnalo(int nechutnalo) {
        this.nechutnalo = nechutnalo;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Kavarna) {
            Kavarna druha = (Kavarna) o;
            return nazev.equals(druha.nazev);
        }
        return false;
    }
    
    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     * 
     * @return čísledný identifikátor instance
     */
    @Override
    public int hashCode() {
        return nazev.hashCode();
    }

    @Override
    public void registerObserver(Observer observer) {
        System.out.println("logika.Kavarna.registerObserver()");
        listObserveru.add(observer);
        System.out.println(listObserveru);
    }

    @Override
    public void deleteObserver(Observer observer) {
         listObserveru.remove(observer);
    }

    @Override
    public void notifyAlllObservers() {
        for (Observer listObserveruItem : listObserveru) {
            listObserveruItem.update();
        }
    }
}
