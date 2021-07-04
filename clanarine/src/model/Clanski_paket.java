package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Clanski_paket {
    private final int ID ;
    private final String Naziv;
    private final String Opis;


    public Clanski_paket(int ID, String Naziv,String Opis){
        this.ID=ID;
        this.Naziv=Naziv;
        this.Opis =Opis;
    }
    public int getID() {
        return ID;
    }
    public String getNaziv() {
        return Naziv;
    }
    public String getOpis() {
        return Opis;
    }


    public static ObservableList<Clanski_paket> listaClanski_paket(){
        ObservableList<Clanski_paket> lista = FXCollections.observableArrayList();
        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM `Clanski_paket`");

        try {
            while (rs.next()) {
                lista.add(new Clanski_paket(rs.getInt("ID"), rs.getString("Naziv"), rs.getString("Opis")));
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
        return lista;
    }
}
