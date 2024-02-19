package persistentie;

import domein.Speler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpelerMapper {

    private static final String INSERT_SPELER = "INSERT INTO ID340733_g26.Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld)"
            + "VALUES (?, ?, ?, ?)";
            
    public void voegToe(Speler speler) 
    {
    	try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) 
        {
            query.setString(1, speler.getGebruikersnaam());
            query.setInt(2, speler.getGeboortejaar());
            query.setInt(3, speler.getAantalGewonnen());
            query.setInt(4, speler.getAantalGespeeld());
            
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    public Speler geefSpeler(String gebruikersnaam) {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID340733_g26.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) 
                {
                    int geboortejaar = rs.getInt("geboortejaar");
                    int aantalGewonnen = rs.getInt("aantalGewonnen");
                    int aantalGespeeld = rs.getInt("aantalGespeeld");

                    speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);               
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
    }

}
