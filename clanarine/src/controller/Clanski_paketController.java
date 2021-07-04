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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Baza;
import model.Clanski_paket;

import javax.swing.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Clanski_paketController implements Initializable {


    ObservableList<Clanski_paket> data1 = FXCollections.observableArrayList();
    Baza db = new Baza();

    @FXML
    private TextField IDR;

    @FXML
    private TextField NazivR;

    @FXML
    private TextField OpisR;

    @FXML
    private TableView<Clanski_paket> Clanski_paketR;

    @FXML
    private TableColumn<Clanski_paket, String> ColumnIDR;

    @FXML
    private TableColumn<Clanski_paket, String> ColumnNazivR;

    @FXML
    private TableColumn<Clanski_paket, String> ColumnOpisR;
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void listaClanski_paket () {

        Baza DB = new Baza();
        ResultSet rs = DB.select("SELECT * FROM `clanski_paket`");
        ColumnIDR.setCellValueFactory(new PropertyValueFactory<Clanski_paket, String>("ID"));
        ColumnNazivR.setCellValueFactory(new PropertyValueFactory<Clanski_paket, String>("Naziv"));
        ColumnOpisR.setCellValueFactory(new PropertyValueFactory<Clanski_paket, String>("Opis"));


        try {

            data1.clear();
            while (rs.next()) {
                data1.add(new Clanski_paket(
                        rs.getInt("ID"),
                        rs.getString("Naziv"),
                        rs.getString("Opis")));
                Clanski_paketR.setItems(data1);
            }
        } catch (SQLException ex) {
            System.out.println("Nastala je greška prilikom iteriranja: " + ex.getMessage());
        }
    }
    public void postaviPodatkeUCelijeR(){
        Clanski_paketR.setOnMouseClicked(e -> {
            Clanski_paket r = Clanski_paketR.getItems().get(Clanski_paketR.getSelectionModel().getSelectedIndex());
            IDR.setText(String.valueOf(r.getID()));
            NazivR.setText(r.getNaziv());
            OpisR.setText(r.getOpis());

        });
    }
    public void DodajR(ActionEvent event){
        try{
            String sql = "INSERT INTO Clanski_paket (Naziv, Opis) VALUES (?, ?)";

            PreparedStatement ps = db.exec(sql);
            ps.setString(1, NazivR.getText());
            ps.setString(2, OpisR.getText());

            ps.execute();


            JOptionPane.showMessageDialog(null, "Uspjesno dodano");
            IDR.clear();
            NazivR.clear();
            OpisR.clear();
            data1.clear();
            listaClanski_paket();

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void UrediR(ActionEvent event){
        try{

            String sql =  "UPDATE `Clanski_paket` SET `Naziv`='"+NazivR.getText()+"',`Opis`='"+OpisR.getText();
            PreparedStatement ps = db.exec(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspjesno azurirano");
            data1.clear();
            listaClanski_paket();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void ObrisiR(ActionEvent event){
        try{
            String sql = "DELETE FROM Clanski_paket WHERE ID="+IDR.getText();
            PreparedStatement ps = db.exec(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uspjesno obrisano");
            IDR.clear();
            NazivR.clear();
            OpisR.clear();
            data1.clear();
            listaClanski_paket();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void IsprazniPoljaR(ActionEvent event){
        try{
            IDR.clear();
            NazivR.clear();
            OpisR.clear();
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
