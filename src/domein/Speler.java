package domein;


public class Speler 
{
    private String gebruikersnaam;
    private int geboortejaar;
    private int aantalGewonnen, aantalGespeeld;
    

    public Speler(String gebruikersnaam,int  geboortejaar) 
    {
    	this(gebruikersnaam,geboortejaar,0,0);
    }
    
    public Speler(String gebruikersnaam,int  geboortejaar, int aantalGewonnen, int aantalGespeeld) 
    {
    	setGebruikersnaam(gebruikersnaam);
    	setGeboortejaar(geboortejaar);
    	setAantalGewonnen(aantalGewonnen);
    	setAantalGespeeld(aantalGespeeld);
    }
    
    

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
		this.geboortejaar = geboortejaar;
	}

	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	private void setAantalGewonnen(int aantalGewonnen) {
		this.aantalGewonnen = aantalGewonnen;
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	private void setAantalGespeeld(int aantalGespeeld) {
		this.aantalGespeeld = aantalGespeeld;
	}

}
