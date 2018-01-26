/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


public class Database 
{
private Connection con;
    private Statement st;
    private ResultSet rs;
   // konstruktor  třídy databáze, kterým se aplikace připojí k databázi
    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/kava?useUnicode=yes&characterEncoding=UTF-8", "root", ""); 
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }

    }

     public ObservableList<Hodnoceni> listHodnoceni(int id)
     {
         try {
            String query = "SELECT * FROM `hodnoceni` WHERE id_kavarny = \"" + id + "\"";
            rs = st.executeQuery(query);
            ObservableList<Hodnoceni>list = FXCollections.observableArrayList();
            while(rs.next())
            {
                Hodnoceni pom = new Hodnoceni();
                pom.setHodnoceni(rs.getInt(2));
                pom.setKomentar(rs.getString(3));
                
                java.util.Date input = rs.getDate(4);
                LocalDate date = Instant.ofEpochMilli(input.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                pom.setDatum(date);
                
                list.add(pom);
            }
            return list;
            } 
         catch(Exception ex) 
            {
     System.out.println(ex);
            }
         return null;
      }
     
     public Kavarna getKavarna(int id)
     {
         try {
            String query = "SELECT * FROM `kavarny` WHERE id = \"" + id + "\"";
            rs = st.executeQuery(query);
            Kavarna pom = new Kavarna();
            while(rs.next())
            {
                pom.setId(rs.getInt(1));
                pom.setNazev(rs.getString(2));
                pom.setAdresa(rs.getString(3));
                pom.setPopis(rs.getString(4));
                pom.setKavaNabidka(rs.getString(5));
            }
            return pom;
            } 
         catch(Exception ex) 
            {
     System.out.println(ex);
            }
         return null;
      }
     
     public void pridejHodnoceni(int hodnoceni, String komentar,LocalDate datum,int id)
    {
        try {
            String query = "Insert into `hodnoceni` SET `hodnoceni` = \"" + hodnoceni + "\",`komentar`= \"" + komentar + "\",`datum`= \"" + datum + "\",`id_kavarny`= \"" + id + "\"";
            st.executeUpdate(query);
            Uspech();
        } catch (Exception ex) {
            Error(ex.toString());
        }  
    }
     
    public void upravKavarnu(int id,String nazev,String adresa,String popis,String menu)
    {
        try {
            String query = "UPDATE `kavarny` SET `nazev`= \"" + nazev + "\",`adresa`= \"" + adresa + "\",`popis`= \"" + popis + "\",`kavaNabidka`= \"" + menu + "\" WHERE id = \"" + id + "\"";
            st.executeUpdate(query);
            Uspech();
        } catch (Exception ex) {
            Error(ex.toString());
        }  
    }
    
    public ObservableList<Kavarna> najdiKavarny(String parametr)
    {
        try {
            String pomocny = "%";
            pomocny += parametr + "%";
            String query = "SELECT * FROM `kavarny` WHERE Nazev LIKE \"" + pomocny + "\"";
            rs = st.executeQuery(query);
            ObservableList<Kavarna>list = FXCollections.observableArrayList();
            while(rs.next())
            {
                Kavarna pom = new Kavarna();
                pom.setId(rs.getInt(1));
                pom.setNazev(rs.getString(2));
                pom.setAdresa(rs.getString(3));
                pom.setPopis(rs.getString(4));
                pom.setKavaNabidka(rs.getString(5));
                
                list.add(pom);
            }
            return list;
            } 
         catch(Exception ex) 
            {
            System.out.println(ex);
            }
         return null;
    }
    
    
    
    public void Error(String error)
     {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba!");
        alert.setHeaderText("Jedno nebo více políček je špatně");
        alert.setContentText(error);
        alert.showAndWait();
     }
     
     public void Uspech()
     {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oznámení");
        alert.setHeaderText("Úspěch");
        alert.setContentText("Operace proběhla úspěšně");
        alert.showAndWait();
     }

    
}
