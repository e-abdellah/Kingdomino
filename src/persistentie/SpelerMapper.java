package persistentie;

import static persistentie.Connectie.MYSQL_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO ID429772_g36.Speler (gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld)"
			+ "VALUES (?, ?, ?, ?)";

	public void voegToe(Speler speler) {
		Connectie ssh = new Connectie();

		try (Connection conn = DriverManager.getConnection(MYSQL_JDBC); // MYSQL_JDBC moet een correct geformatteerde
																		// JDBC URL zijn
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) { // INSERT_SPELER moet de SQL statement
																					// string zijn

			// Vult de parameters van het PreparedStatement in met de gegevens van de speler
			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getGeboortejaar());
			query.setInt(3, speler.getAantalGewonnen());
			query.setInt(4, speler.getAantalGespeeld());

			// Voert de update uit die de nieuwe speler aan de database toevoegt
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			ssh.closeConnection();
		}
	}

	public Speler geefSpeler(String gebruikersnaam) {
		Connectie ssh = new Connectie();
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(MYSQL_JDBC);
				PreparedStatement query = conn
						.prepareStatement("SELECT * FROM ID429772_g36.Speler WHERE gebruikersnaam = ?")) {
			query.setString(1, gebruikersnaam); // Stelt de parameter in voor de prepared statement om SQL injectie te
												// voorkomen.

			try (ResultSet rs = query.executeQuery()) { // Voert de query uit en verwerkt het resultaat.
				if (rs.next()) { // Controleert of er een resultaat is.
					int geboortejaar = rs.getInt("geboortejaar");
					int aantalGewonnen = rs.getInt("aantalGewonnen");
					int aantalGespeeld = rs.getInt("aantalGespeeld");

					// Creëert een nieuw Speler object op basis van de opgehaalde gegevens.
					speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			ssh.closeConnection();
		}
		return speler; // Retourneert het Speler object of null als het niet gevonden is.
	}

	public List<Speler> geefAlleSpelers() {
	    Connectie ssh = new Connectie();
	    List<Speler> spelers = new ArrayList<>(); // Een lijst om de opgehaalde spelers te bewaren.

	    try (Connection conn = DriverManager.getConnection(MYSQL_JDBC); // Verkrijgt een databaseverbinding.
	         PreparedStatement query = conn.prepareStatement("SELECT * FROM ID429772_g36.Speler")) { // Voorbereiden van een SQL statement om alle spelers op te halen.

	        try (ResultSet rs = query.executeQuery()) { // Uitvoeren van de query en verwerken van het resultaat.
	            while (rs.next()) { // Doorloopt elk record in het resultaat.
	                String gebruikersnaam = rs.getString("gebruikersnaam");
	                int geboortejaar = rs.getInt("geboortejaar");
	                int aantalGewonnen = rs.getInt("aantalGewonnen");
	                int aantalGespeeld = rs.getInt("aantalGespeeld");

	                Speler speler = new Speler(gebruikersnaam, geboortejaar, aantalGewonnen, aantalGespeeld);
	                spelers.add(speler); // Voegt elke nieuwe speler toe aan de lijst.
	            }
	        }
	    } catch (SQLException ex) { 
	        throw new RuntimeException(ex); 
	    } finally {
	        ssh.closeConnection(); 
	    }
	    return spelers; // Retourneert de lijst van spelers.
	}


}
