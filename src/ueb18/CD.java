package ueb18;

public class CD extends Artikel {
	  private String interpret;
	  private String titel;
	  private int anzahlTitel;

	  public CD(int artikelNr, int bestand, double preis, String interpret, String titel, int anzahlTitel) {
	    super(artikelNr, "Cd", bestand, preis);
	    if (interpret == null || interpret.isBlank()) {
	      throw new IllegalArgumentException("Interpret darf nicht null sein");
	    }
	    if (titel == null || titel.isBlank()) {
	      throw new IllegalArgumentException("Titel darf nicht null sein");
	    }
	    if (anzahlTitel == 0) {
	      throw new IllegalArgumentException("CD darf nicht leer sein");
	    }
	    this.interpret = interpret;
	    this.titel = titel;
	    this.anzahlTitel = anzahlTitel;
	  }

	  @Override
	  public String getBeschreibung() {
	    return interpret + ": " + titel;
	  }

	  /** @return the interpret */
	  public String getInterpret() {
	    return interpret;
	  }

	  /** @return the titel */
	  public String getTitel() {
	    return titel;
	  }

	  /** @return the anzahlTitel */
	  public int getAnzahlTitel() {
	    return anzahlTitel;
	  }

	  @Override
	  public String toString() {
	    return super.toString()
	        + String.format(
	            "Titel: %s Interpret: %s Anzahl Musiktitel: %d", titel, interpret, anzahlTitel);
	  }
	}