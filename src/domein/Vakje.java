package domein;

public class Vakje {
	private Landschap landschap;
	private int aantalKronen;

	private int x;

	private int y;

	public Vakje() {
	}
	public Vakje(Landschap landschap, int aantalKronen, int y, int x){
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
	}

	public Vakje(Landschap landschap) {
		this.landschap = landschap;
	}

	public Vakje(Landschap landschap, int aantalKronen) {
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public final void setX(int x){
		this.x = x;
	}

	public final void setY(int y){
		this.y = y;
	}

	public Landschap getLandschap() {
		return landschap;
	}

	@Override
	public String toString() {
		return "[Landschap=" + landschap + "]";
	}

	public int getAantalKronen() {
		return aantalKronen;
	}

	public void setAantalKronen(int aantalKronen) {
		this.aantalKronen = aantalKronen;
	}

}