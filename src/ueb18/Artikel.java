package ueb18;
public class Artikel {

  private final int artikelnr;
  private String art;
  private int bestand;
  private double price;

  /**
   * Konstruktor für die Artikel Klasse
   *
   * @param artikelnr die Artikelnummer des Artikel
   * @param art die Art des Artikel
   * @param bestand der Initalbestand des Artikel
   * @param price der Preis des Artikels
   * @throws IllegalArgumentException Wenn <code>artikelnr</code> keine 4-stellige positive Zahl
   *     sein
   * @throws IllegalArgumentException Wenn <code>art</code> null oder leer ist
   * @throws IllegalArgumentException Wenn <code>bestand</code> kleiner 0 ist
   * @throws IllegalArgumentException Wenn <code>price</code> kleiner als 1 ist
   */
  public Artikel(int artikelnr, String art, int bestand, double price) {
    if (art == null || art.isBlank()) {
      throw new IllegalArgumentException("Art darf nicht null sein");
    }
    if (artikelnr < 1000 || artikelnr > 9999) {
      throw new IllegalArgumentException("Die Artikelnummer muss eine 4-stellige Zahl");
    }
    if (bestand < 0) {
      throw new IllegalArgumentException("Bestand muss größer als 0 sein");
    }
    if (price <= 0) {
      throw new IllegalArgumentException("Preis muss größer als 0 sein");
    }
    this.artikelnr = artikelnr;
    this.bestand = bestand;
    setArt(art);
    setPrice(price);
  }

  public Artikel(int artikelnr, String art, double price) {
    this(artikelnr, art, 0, price);
  }

  /**
   * Füge dem aktuellen Bestand die eingegebene Menge hinzu
   *
   * @param menge Die Anzahl der Element die zum Bestand hinzugefügt werden sollen
   * @throws IllegalArgumentException Wenn <code>menge</code> kleiner als 1 ist
   */
  public void bucheZugang(int menge) {
    if (menge < 1) {
      throw new IllegalArgumentException("Menge muss größer als 0 sein");
    }
    bestand += menge;
  }

  /**
   * Ziehe dem aktuellen Bestand die eingegebene Menge ab
   *
   * @param menge Die Anzahl der Elemente die vom Bestand entfernt werden soll
   * @throws IllegalArgumentException Wenn <code>menge</code> kleiner als 1 ist oder die Menge nach
   *     dem Abgang kleiner 0 wäre
   */
  public void bucheAbgang(int menge) {
    if (menge < 1) {
      throw new IllegalArgumentException("Menge muss größer als 0 sein");
    }
    if (bestand - menge < 0) {
      throw new IllegalArgumentException("Bestand muss nach Abgang größer 0 sein");
    }
    bestand -= menge;
  }

  /** @param prozent der Prozentsatz um den der Preis geändert werden soll */
  public void aenderePreis(double prozent) {
    double change = (prozent * getPrice()) / 100;
    double price = getPrice() + change;
    if (price > 0) {
      setPrice(price);
    }
  }

  /**
   * Ändert die Art des Artikels
   *
   * @param art die Art des Artikel
   * @throws IllegalArgumentException Wenn <code>art</code> null oder leer ist
   */
  public void setArt(String art) {
    if (art == null || art.isBlank()) {
      throw new IllegalArgumentException("Art darf nicht null sein");
    }
    this.art = art.trim();
  }

  /**
   * Ändert den Preis des Artikels
   *
   * @param price der Preis des Artikels
   * @throws IllegalArgumentException Wenn <code>price</code> kleiner als 1 ist
   */
  public void setPrice(double price) {
    if (price <= 0) {
      throw new IllegalArgumentException("Preis muss größer als 0 sein");
    }
    this.price = Math.round(price * 100.0) / 100.0;
  }

  public int getArtikelNr() {
    return artikelnr;
  }

  public int getBestand() {
    return bestand;
  }

  public String getArt() {
    return art;
  }

  public double getPrice() {
    return price;
  }

  public String getBeschreibung() {
    return art;
  }

  @Override
  public String toString() {
    return String.format(
        "Artikel: %d Art: %s Bestand: %d Preis: %f ", artikelnr, art, bestand, price);
  }
}