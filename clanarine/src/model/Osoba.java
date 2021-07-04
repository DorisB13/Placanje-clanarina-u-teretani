package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Osoba {
    private final int ID;
    private final String Ime;
    private final String Prezime;
    private final int JMBG;
    private final String Adresa;
    private final String Vrsta_korisnika;
    private final String Korisnicko_ime;
    private final String Lozinka;

    public Osoba(int ID, String Ime, String Prezime, int JMBG, String Adresa, String Vrsta_korisnika, String Korisnicko_ime, String Lozinka) {
        this.ID = ID;
        this.Ime = Ime;
        this.Prezime = Prezime;
        this.JMBG = JMBG;
        this.Adresa = Adresa;
        this.Vrsta_korisnika=Vrsta_korisnika;
        this.Korisnicko_ime = Korisnicko_ime;
        this.Lozinka = Lozinka;
    }

    public int getID() {
        return ID;
    }

    public String getIme() {
        return Ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public int getJMBG() {
        return JMBG;
    }

    public String getAdresa() {
        return Adresa;
    }

    public String getVrsta_korisnika() {
        return Vrsta_korisnika;
    }

    public String getKorisnicko_ime() {
        return Korisnicko_ime;
    }

    public String getLozinka() {
        return Lozinka;
    }

    public static ObservableList<Osoba> listaOsoba(){
        ObservableList<Osoba> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM `osoba`");

        try {
            while (rs.next()) {
                lista.add(new Osoba(rs.getInt("ID"), rs.getString("Ime"), rs.getString("Prezime"), rs.getInt("JMBG"),  rs.getString("Adresa"),rs.getString("Vrsta_korisnika"), rs.getString("Korisnicko_ime"),  rs.getString("Lozinka")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
}
