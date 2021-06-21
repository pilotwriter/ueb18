package ueb18;

public class Video extends Artikel {

	  private String titel;
	  private int spieldauer;
	  private int jahr;

	  public Video(int artikelNr, int bestand, double preis, String titel, int spieldauer, int jahr) {
	    super(artikelNr, "Video", bestand, preis);
	    if (jahr > 2020 || jahr < 1950) {
	      throw new IllegalArgumentException("Jahr muss zwischen 1950 und 2020 liegen");
	    }
	    if (spieldauer == 0) {
	      throw new IllegalArgumentException("Spieldauer darf nicht 0 sein");
	    }
	    if (titel == null || titel.isBlank()) {
	      throw new IllegalArgumentException("Titel darf nicht null sein");
	    }
	    this.titel = titel;
	    this.spieldauer = spieldauer;
	    this.jahr = jahr;
	  }

	  @Override
	  public String getBeschreibung() {
	    return titel;
	  }

	  /** @return the titel */
	  public String getTitel() {
	    return titel;
	  }

	  /** @return the spieldauer */
	  public int getSpieldauer() {
	    return spieldauer;
	  }

	  /** @return the jahr */
	  public int getJahr() {
	    return jahr;
	  }

	  @Override
	  public String toString() {
	    return super.toString()
	        + String.format("Titel: %s Spieldauer: %d Jahr: %d", titel, spieldauer, jahr);
	  }
	}