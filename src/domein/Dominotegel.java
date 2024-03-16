package domein;

public class Dominotegel {

	private Vakje vakje1, vakje2;
	private int getal = 0;
	private int kroon;
	private int zijde;

	public Dominotegel(Vakje vakje1, Vakje vakje2, int getal, int kroon, int zijde) {
		setVakje1(vakje1);
		setVakje2(vakje2);
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

}
