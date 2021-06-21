package ueb18;

public class Buch extends Artikel {

	  private String titel;
	  private String autor;
	  private String verlag;

	  public Buch(int artikelNr, int bestand, double preis, String autor, String titel, String verlag) {
	    super(artikelNr, "Buch", bestand, preis);
	    if (titel == null || titel.isBlank()) {
	      throw new IllegalArgumentException("Titel darf nicht null sein");
	    }
	    if (autor == null || autor.isBlank()) {
	      throw new IllegalArgumentException("Autor darf nicht null sein");
	    }
	    if (verlag == null || verlag.isBlank()) {
	      throw new IllegalArgumentException("Verlag darf nicht null sein");
	    }
	    this.titel = titel;
	    this.autor = autor;
	    this.verlag = verlag;
	  }

	  @Override
	  public String getBeschreibung() {
	    return autor + ": " + titel;
	  }

	  /** @return the titel */
	  public String getTitel() {
	    return titel;
	  }

	  /** @return the autor */
	  public String getAutor() {
	    return autor;
	  }

	  /** @return the verlag */
	  public String getVerlag() {
	    return verlag;
	  }

	  @Override
	  public String toString() {
	    return super.toString() + String.format("Titel: %s Autor: %s Verlag: %s", titel, autor, verlag);
	  }
	}