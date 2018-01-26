/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import java.time.LocalDate;


public class Hodnoceni 
{
    private int hodnoceni;
    private String komentar;
    private LocalDate datum;

    public Hodnoceni() {
    }

    public Hodnoceni(int hodnoceni, String komentar, LocalDate datum) {
        this.hodnoceni = hodnoceni;
        this.komentar = komentar;
        this.datum = datum;
    }

    public int getHodnoceni() {
        return hodnoceni;
    }

    public void setHodnoceni(int hodnoceni) {
        this.hodnoceni = hodnoceni;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
    
    
}
