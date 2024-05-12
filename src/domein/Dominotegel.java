package domein;

/**
 * De {@code Dominotegel} klasse representeert een individuele dominotegel met twee vakjes,
 * een getalwaarde, kroonaantal, en zijde-indicatie. Deze klasse biedt ook methoden om
 * de afbeeldingspaden voor de voor- en achterkant van de tegel te genereren.
 */
public class Dominotegel {

    private Vakje vakje1, vakje2;
    private int getal = 1;
    private int kroon;
    private int zijde;

    private String voorkantFotoPad;
    private String achterkantFotoPad;

    /**
     * Constructor voor {@code Dominotegel} met specificatie van alle eigenschappen.
     * Genereert ook de paden voor de afbeeldingen van de tegel.
     * 
     * @param vakje1 het eerste vakje van de dominotegel
     * @param vakje2 het tweede vakje van de dominotegel
     * @param getal het getal op de dominotegel dat de waarde vertegenwoordigt
     * @param kroon het aantal kronen op de dominotegel
     * @param zijde de zijde van de dominotegel
     */
    public Dominotegel(Vakje vakje1, Vakje vakje2, int getal, int kroon, int zijde) {
        setVakje1(vakje1);
        setVakje2(vakje2);
        setGetal(getal);
        setKroon(kroon);
        setZijde(zijde);
        genereerFotoPaden();
    }

    /**
     * Constructor voor {@code Dominotegel} zonder kroon specificatie.
     * 
     * @param vakje1 het eerste vakje van de dominotegel
     * @param vakje2 het tweede vakje van de dominotegel
     * @param getal het getal op de dominotegel
     * @param zijde de zijde van de dominotegel
     */
    public Dominotegel(Vakje vakje1, Vakje vakje2, int getal, int zijde) {
        setVakje1(vakje1);
        setVakje2(vakje2);
        setGetal(getal);
        setZijde(zijde);
        genereerFotoPaden();
    }

    /**
     * Standaard constructor voor {@code Dominotegel}.
     */
    public Dominotegel() {
        // Default constructor body
    }

    /**
     * Genereert de paden voor de voor- en achterkant foto's van de dominotegel.
     */
    public void genereerFotoPaden() {
        String nummerAlsString = String.format("%02d", this.getGetal());
        this.setVoorkantFotoPad(getClass().getResource("/img/tegel_" + nummerAlsString + "_voorkant.png").toExternalForm());
        this.setAchterkantFotoPad(getClass().getResource("/img/tegel_" + nummerAlsString + "_achterkant.png").toExternalForm());
    }

    /**
     * Geeft het getal op de dominotegel.
     * 
     * @return het getal op de dominotegel
     */
    public int getGetal() {
        return getal;
    }

    /**
     * Stelt het getal op de dominotegel in.
     * 
     * @param getal het nieuwe getal voor de dominotegel
     */
    public final void setGetal(int getal) {
        this.getal = getal;
    }

    /**
     * Geeft het aantal kronen op de dominotegel.
     * 
     * @return het aantal kronen
     */
    public int getKroon() {
        return kroon;
    }

    /**
     * Stelt het aantal kronen op de dominotegel in gebaseerd op de waarde van het getal.
     * 
     * @param kroon het aantal kronen op de tegel
     */
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

    /**
     * Geeft de zijde van de dominotegel.
     * 
     * @return de zijde van de dominotegel
     */
    public int getZijde() {
        return zijde;
    }

    /**
     * Stelt de zijde van de dominotegel in.
     * 
     * @param zijde de nieuwe zijde van de dominotegel
     */
    public void setZijde(int zijde) {
        this.zijde = zijde;
    }

    /**
     * Geeft het eerste vakje van de dominotegel.
     * 
     * @return het eerste vakje
     */
    public Vakje getVakje1() {
        return vakje1;
    }

    /**
     * Stelt het eerste vakje van de dominotegel in.
     * 
     * @param vakje1 het eerste vakje
     */
    public final void setVakje1(Vakje vakje1) {
        this.vakje1 = vakje1;
    }

    /**
     * Geeft het tweede vakje van de dominotegel.
     * 
     * @return het tweede vakje
     */
    public Vakje getVakje2() {
        return vakje2;
    }

    /**
     * Stelt het tweede vakje van de dominotegel in.
     * 
     * @param vakje2 het tweede vakje
     */
    public final void setVakje2(Vakje vakje2) {
        this.vakje2 = vakje2;
    }

    @Override
    public String toString() {
        return "Dominotegel [vakje1=" + vakje1.toString() + ", vakje2=" + vakje2.toString() + ", getal=" + getal + ", kroon=" + kroon + ", zijde=" + zijde + "]";
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
