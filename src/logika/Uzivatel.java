/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import java.util.HashSet;
import java.util.Set;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import main.Main;
import ui.UzivatelScene;


/**
 *
 * @author monika
 */
public class Uzivatel{
    private int id;
    private String jmeno; 
    private String heslo; 
    private String email;
    private Boolean jePrihlasen; 
    private Boolean jeSpravce; 
    private Main main;
    private Boolean changeJeSpravce;
    private String changeJmeno="";
    private Uzivatel aktualniUzivatel;

    private List<Uzivatel> seznamUzivatelu;
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String dbConnection = "jdbc:mysql://127.0.0.1:3306/kava?user=root";
    private static final String dbUser = "root";
    private static final String dbPassword = ""; 

    public Uzivatel(Main main){ // potreba vsude predavat stejnou instanci tridy main kvuli pristupu a zmene primaryStage
        seznamUzivatelu = new ArrayList<Uzivatel>();
        this.main=main;
        System.out.println("main");
        databazovaFunkce("GET", "SELECT * FROM kava.uzivatele");

    
    }
    public Uzivatel(String jmeno, String heslo, String email, boolean jeSpravce){ //int id
        this.main = new Main();
        this.jmeno = jmeno;
        this.email = email;
        this.heslo = heslo;
        this.jePrihlasen = false;
        this.jeSpravce = jeSpravce;
       
    }
    
    
    public void registrace(String jmeno, String heslo, String email) {
        for (Uzivatel seznamUzivateluItem : seznamUzivatelu) {
            if (seznamUzivateluItem.getJmeno().equals(jmeno)){ 
               System.out.println("Uzivatelske jmeno jiz existuje");
                
            }
            else{
                this.jmeno=jmeno;
                this.heslo=heslo;
                this.email=email;
                this.jePrihlasen=true;
                this.jeSpravce = false;
                aktualniUzivatel=new Uzivatel(jmeno, heslo, email, jeSpravce);
                seznamUzivatelu.add(new Uzivatel(jmeno, heslo, email, jeSpravce));
                
                databazovaFunkce("INSERT", "INSERT INTO kava.uzivatele (jmeno, heslo, email, jeSpravce) VALUES (?,?,?,?)");
                
  
            }
            
        }
        // test zda user uz neexistuje
            // if ne -> ulozit usera, set prihlasen = true
            //else user existuje
            
    }
    
    public void prihlasit(String jmeno, String heslo) {
      
        // test zda je user s danym jmenem a heslem v DB
        // if ano prihlasi, set prihlasen = true
        // else spatne jmeno nebo heslo        
        for (Uzivatel seznamUzivateluItem : seznamUzivatelu ) {
            
            if ((jmeno.equals(seznamUzivateluItem.getJmeno())) && (heslo.equals(seznamUzivateluItem.getHeslo()))){ 
                this.aktualniUzivatel=seznamUzivateluItem;
                this.jmeno=jmeno;
                this.heslo=heslo;
                this.jePrihlasen=true;
                this.jeSpravce=seznamUzivateluItem.getJeSpravce();
                System.out.println("Uzivatel"+jmeno+"prihlasen");
                
                if(jeSpravce==true){
                    main.primaryStage.setScene(main.SS.getScena()); 
                    return; // ukonceni metody
                }
                else{
                    main.primaryStage.setScene(main.US.getScena());
                    return;
                }
            } 
           
        }
       chybaUdaju(); // pokud se nenajde schoda provede se tato metoda
    }
    
    public void chybaUdaju(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chybné údaje");
        alert.setHeaderText("Heslo nebo jméno jsou špatně!");
        alert.showAndWait();
        System.out.println("Uživatelské jméno nebo heslo je špatně.");
        main.primaryStage.setScene(main.PS.getScena());
    }
    public void odhlasit() { 
        setJePrihlasen(false);
        aktualniUzivatel=null;
        seznamUzivatelu.clear();
        main.poOdhlaseni(); // 
        databazovaFunkce("GET", "SELECT * FROM kava.uzivatele");
        
    }
    public void update(Boolean jeSpravce, String jmeno){
        System.out.println("update");
        System.out.println("*"+jmeno);
        System.out.println("*"+jeSpravce);

         for (Uzivatel seznamUzivateluItem : seznamUzivatelu ) 
             if(jmeno.equals(seznamUzivateluItem.getJmeno())){
                 seznamUzivateluItem.setJeSpravce(jeSpravce);
                 System.out.println(seznamUzivateluItem.jeSpravce+" u "+seznamUzivateluItem.jmeno);
                 seznamUzivateluItem.setJmeno(jmeno);
                 changeJeSpravce = jeSpravce;
                 changeJmeno = jmeno;
                 databazovaFunkce("UPDATE", "UPDATE kava.uzivatele SET jeSpravce = ? WHERE jmeno= ?");
             }
        
    }
    
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getJePrihlasen() {
        return jePrihlasen;
    }

    public Boolean getJeSpravce() {
        return jeSpravce;
    }
     public String getJmeno() {
        return jmeno;
    }

    public String getHeslo() {
        return heslo;
    }
     public List<Uzivatel> getSeznamUzivatelu() {
        return seznamUzivatelu;
    }
      public Uzivatel getAktualniUzivatel() {
        return aktualniUzivatel;
    }
//   
    public void setId(int id) {
        this.id = id;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJePrihlasen(Boolean jePrihlasen) {
        this.jePrihlasen = jePrihlasen;
    }

    public void setJeSpravce(Boolean jeSpravce) {
        this.jeSpravce = jeSpravce;
    }

    public void setAktualniUzivatel(Uzivatel aktualniUzivatel) {
        this.aktualniUzivatel = aktualniUzivatel;
    }
    public void databazovaFunkce(String funkce, String parametr) {
        System.out.println("v db funkci");
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
                
                statement.setString(1, jmeno);
                statement.setString(2, email);
                statement.setString(3, heslo);
                statement.setBoolean(4,jeSpravce);
                
                main.primaryStage.setScene(main.US.getScena());
                statement.executeUpdate();
                
            }
            
            if (funkce.equals("UPDATE")) {
                
                statement = connection.prepareStatement(parametr);
                
                 statement.setBoolean(1, changeJeSpravce);
                 statement.setString(2, changeJmeno);
                 
                System.out.println("v updatu");
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
                    alert.setHeaderText("Chyba pripojeni");

                    alert.showAndWait();
                    System.out.println(ex.getMessage());
                }
            });

        }
        catch ( SQLException ex ) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Chybné údaje");
                    alert.setHeaderText("Zadané uživatelské jméno iž existuje");
                    alert.setContentText("Prosím zadejte jiné");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                           main.primaryStage.setScene(main.RS.getScena());
                    } 
                }
            });

        }
        finally {

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
                String jmeno = rs.getString("jmeno");
                String heslo = rs.getString("heslo");
                String email = rs.getString("email");
                boolean jeSpravce = rs.getBoolean("jeSpravce");
                System.out.println(jmeno+" "+jeSpravce);
                seznamUzivatelu.add(new Uzivatel( jmeno, heslo, email, jeSpravce));
                

            }
        } catch (SQLException ex) {
            Logger.getLogger(Uzivatel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//     @Override
//    public boolean equals(Object o) {
//        if (o instanceof Uzivatel) {
//            Uzivatel druha = (Uzivatel) o;
//            return jmeno.equals(druha.jmeno);
//        }
//        return false;
//    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Uzivatel) {
            Uzivatel druhy = (Uzivatel) o;
            return jmeno.equals(druhy.jmeno);
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
        return jmeno.hashCode();
    }
}
