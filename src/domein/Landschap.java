package domein;

/**
 * De {@code Landschap} enum definieert de verschillende typen landschappen die
 * gebruikt kunnen worden binnen het spel. Elk landschapstype kan verschillende
 * regels en eigenschappen hebben afhankelijk van de spellogica.
 * <p>
 * Beschikbare landschapstypen zijn:
 * <ul>
 * <li>{@link #BOS} - Representeert een bosgebied, vaak geassocieerd met
 * houtproductie of natuur.</li>
 * <li>{@link #GRAS} - Representeert een grasland, vaak gebruikt voor landbouw
 * of als open speelruimte.</li>
 * <li>{@link #AARDE} - Vertegenwoordigt aardegebieden, mogelijk gebruikt voor
 * landbouw of constructie.</li>
 * <li>{@link #MIJN} - Vertegenwoordigt mijngebieden, belangrijk voor het
 * verkrijgen van mineralen of andere grondstoffen.</li>
 * <li>{@link #WATER} - Vertegenwoordigt waterlichamen zoals meren of rivieren,
 * essentieel voor bepaalde speltypes of voor vervoer.</li>
 * <li>{@link #ZAND} - Vertegenwoordigt zandgebieden zoals woestijnen of
 * stranden, met unieke uitdagingen en mogelijkheden.</li>
 * <li>{@link #KASTEEL} - Vertegenwoordigt kastelen of vestingwerken, vaak het
 * centrum van macht of belangrijke strategische punten.</li>
 * </ul>
 */
public enum Landschap {

	BOS,

	GRAS,

	AARDE,

	MIJN,

	WATER,

	ZAND,

	KASTEEL;
}