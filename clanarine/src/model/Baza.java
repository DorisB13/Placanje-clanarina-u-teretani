package model;
import model.Konekcija;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Baza extends Konekcija{

    public static final Baza DB = new Baza();
    private Statement upit;
    private PreparedStatement execUpit;

    public Baza () {
        super();
    }

    public ResultSet select (String sql) {
        try{
            this.upit = this.konekcija.createStatement();
            return this.upit.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println("Nastala je grrška prilikom izvrsavanja upita" + e.getMessage());
            return null;
    }
}
public PreparedStatement exec(String sql){
    try {
        this.execUpit = this.konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        return this.execUpit;
    }catch (SQLException e) {
        System.out.println("Nisam uspio izvrsiti upit" + e.getMessage());
    }
    return null;
}
}
