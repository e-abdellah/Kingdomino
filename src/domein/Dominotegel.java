package domein;

public class Dominotegel {

	private Vakje vakje1, vakje2;
	private int getal = 1;
	private int kroon;
	private int zijde;

	private String voorkantFotoPad;
	private String achterkantFotoPad;

	public Dominotegel(Vakje vakje1, Vakje vakje2, int getal, int kroon, int zijde) {
		setVakje1(vakje1);
		setVakje2(vakje2);
		setGetal(getal);
		setKroon(kroon);
		setZijde(zijde);
		genereerFotoPaden();
	}

	public Dominotegel() {
		// TODO Auto-generated constructor stub
	}

	public void genereerFotoPaden() {
		String nummerAlsString = String.format("%02d", this.getGetal()); // Zorgt ervoor dat het getal altijd twee cijfers heeft
		this.setVoorkantFotoPad(
				getClass().getResource("/img/tegel_" + nummerAlsString + "_voorkant.png").toExternalForm());
		this.setAchterkantFotoPad(
				getClass().getResource("/img/tegel_" + nummerAlsString + "_achterkant.png").toExternalForm());
	}

	public int getGetal() {
		return getal;
	}

	public final void setGetal(int getal) {
		this.getal = getal++;

	}

	public int getKroon() {
		return kroon;
	}

	public void setKroon(int kroon) {
		if (getal <= 18)
			this.kroon = 0;

		else if (getal <= 40)
			this.kroon = 1;
		else if (getal <= 47)
			this.kroon = 2;
		else
			this.kroon = 3;

	}

	public int getZijde() {
		return zijde;
	}

	public void setZijde(int zijde) {
		this.zijde = zijde;
	}

	public Vakje getVakje1() {
		return vakje1;
	}

	public final void setVakje1(Vakje vakje1) {
		this.vakje1 = vakje1;
	}

	public Vakje getVakje2() {
		return vakje2;
	}

	public final void setVakje2(Vakje vakje2) {
		this.vakje2 = vakje2;
	}

	@Override
	public String toString() {
		return "Dominotegel [vakje=" + vakje1.toString() + " " + vakje2.toString() + ", getal=" + getal + ", kroon="
				+ kroon + ", zijde=" + zijde + "]";
	}

	public String getVoorkantFotoPad() {
		return voorkantFotoPad;
	}

	public final void setVoorkantFotoPad(String voorkantFotoPad) {
		this.voorkantFotoPad = voorkantFotoPad;
	}

	public String getAchterkantFotoPad() {
		return achterkantFotoPad;
	}

	public final void setAchterkantFotoPad(String achterkantFotoPad) {
		this.achterkantFotoPad = achterkantFotoPad;
	}

}
