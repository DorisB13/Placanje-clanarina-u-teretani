package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Baza;
import model.Osoba;

import javax.swing.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    ObservableList<Osoba> data1 = FXCollections.observableArrayList();
    Baza db = new Baza();

@FXML
private TextField jIDK;

@FXML
private TextField jImeK;

@FXML
private TextField jPrezimeK;

@FXML
private TextField jJMBGK;

@FXML
private TextField jAdresaK;

@FXML
private TextField jTipK;

@FXML
private TextField jKImeK;

@FXML
private PasswordField jLozinkaK;

@FXML
private TableView<Osoba> jTableK;

@FXML
private TableColumn<Osoba, String> ColumnIDK;

@FXML
private TableColumn<Osoba, String> ColumnImeK;

@FXML
private TableColumn<Osoba, String> ColumnPrezimeK;

@FXML
private TableColumn<Osoba, String> ColumnJMBGK;

@FXML
private TableColumn<Osoba, String> ColumnAdresaK;

@FXML
private TableColumn<Osoba, String> ColumnKImeK;

@FXML
private TableColumn<Osoba, String> ColumnLozinkaK;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void listaOsoba() {

        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM osoba");
        ColumnIDK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ID"));
        ColumnImeK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Ime"));
        ColumnPrezimeK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Prezime"));
        ColumnJMBGK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("JMBG"));
        ColumnAdresaK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Adresa"));
        ColumnKImeK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Korisnicko_ime"));
        ColumnLozinkaK.setCellValueFactory(new PropertyValueFactory<Osoba, String>("Lozinka"));


        try {
            data1.clear();
            while (rs.next()) {
                data1.add(new Osoba(
                        rs.getInt("ID"),
                        rs.getString("Ime"),
                        rs.getString("Prezime"),
                        rs.getInt("JMBG"),
                        rs.getString("Adresa"),
                        rs.getString("Vrsta_korisnika"),
                        rs.getString("Korisnicko_ime"),
                        rs.getString("Lozinka")));
                jTableK.setItems(data1);
            }
        } catch (SQLException ex){
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());

        }
    }

    public void postaviPodatkeUCelijeO(){
        jTableK.setOnMouseClicked(e -> {
            Osoba os = jTableK.getItems().get(jTableK.getSelectionModel().getSelectedIndex());
            jIDK.setText(String.valueOf(os.getID()));
            jImeK.setText(os.getIme());
            jPrezimeK.setText(os.getPrezime());
            jJMBGK.setText(String.valueOf(os.getJMBG()));
            jAdresaK.setText(os.getAdresa());
            jTipK.setText(os.getVrsta_korisnika());
            jKImeK.setText(os.getKorisnicko_ime());
            jLozinkaK.setText(os.getLozinka());
        });
    }
    public void DodajO(ActionEvent event){
        try{
            String sql = "INSERT INTO osoba (Ime, Prezime, JMBG, Adresa, Vrsta_korisnika, Korisnicko_ime, Lozinka) VALUES (?, ?, ?,?,?,?,?)";

            PreparedStatement ps = db.exec(sql);
            ps.setString(1, jImeK.getText());
            ps.setString(2, jPrezimeK.getText());
            ps.setString(3, jJMBGK.getText());
            ps.setString(4, jAdresaK.getText());
            ps.setString(5, jTipK.getText());
            ps.setString(6, jKImeK.getText());
            ps.setString(7, jLozinkaK.getText());

            ps.execute();


            JOptionPane.showMessageDialog(null, "Uspjesno dodano");
            jIDK.clear();
            jImeK.clear();
            jPrezimeK.clear();
            jJMBGK.clear();
            jAdresaK.clear();
            jTipK.clear();
            jKImeK.clear();
            jLozinkaK.clear();
            data1.clear();
            listaOsoba();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void UrediO(ActionEvent event){
        try{

            String sql =  "UPDATE `osoba` SET `Ime`='"+jImeK.getText()+"',`Prezime`='"+jPrezimeK.getText()+"',`JMBG`='"+jJMBGK.getText()+"',`Adresa`='"+jAdresaK.getText()+"',`Vrsta_korisnika`='"+jTipK.getText()+"',`Korisnicko_ime`='"+jKImeK.getText()+"',`Lozinka`='"+jLozinkaK.getText()+"' WHERE `ID`= "+jIDK.getText();
            PreparedStatement ps = db.exec(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspjesno azurirano");
            data1.clear();
            listaOsoba();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void ObrisiO(ActionEvent event){
        try{
            String sql = "DELETE FROM osoba WHERE ID="+jIDK.getText();
            PreparedStatement ps = db.exec(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspjesno obrisano");
            jIDK.clear();
            jImeK.clear();
            jPrezimeK.clear();
            jJMBGK.clear();
            jAdresaK.clear();
            jTipK.clear();
            jKImeK.clear();
            jLozinkaK.clear();
            data1.clear();
            listaOsoba();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void IsprazniPoljaO(ActionEvent event){
        try{
            jIDK.clear();
            jImeK.clear();
            jPrezimeK.clear();
            jJMBGK.clear();
            jAdresaK.clear();
            jTipK.clear();
            jKImeK.clear();
            jLozinkaK.clear();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Odjava(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Prijava!");
            stage.setScene(new Scene(root, 370, 295));
            stage.show();
        } catch (Exception e) {

        }
    }
}
