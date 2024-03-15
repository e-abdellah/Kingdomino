package domein;

public class Dominotegel {

	private Vakje vakje;
	private int getal = 0;
	private int kroon;
	private int zijde;

	public Dominotegel(Vakje vakje1, Vakje vakje2, int getal, int kroon, int zijde) {
		setVakje(vakje1);
		setVakje(vakje2);
		setGetal(getal);
		setKroon(kroon);
		setZijde(zijde);
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

	public Vakje getVakje() {
		return vakje;
	}

	public void setVakje(Vakje vakje) {
		this.vakje = vakje;
	}

}
