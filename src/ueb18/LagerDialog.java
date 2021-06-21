package ueb18;

import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class LagerDialog {
	public static Lager createAndGetLager() {
		Buch book1 = new Buch(7853, 2, 12, "Apoorva Kumar", "A Commentary and Digest on The Air, Act 1981", "yky");
		Buch book2 = new Buch(1268, 1, 11, "CAbhay K.	", "The Bloomsbury Anthology of Great Indian Poems", "yky");
		Buch book3 = new Buch(1234, 50, 85, "HAmit ", "Karmayoddha Granth", "yky");
		Buch book4 = new Buch(9999, 321, 3, "B P N Singh", "Politics of Opportunism", "yky");
		Buch book5 = new Buch(4678, 4, 90, "Vakkitham Achuthan Namboodri	", "Malayalam poetry", "yky");

		CD cd1 = new CD(8502, 3, 10, "english", "Alfred ", 20);
		CD cd2 = new CD(2218, 5, 5, "deutsch", "Tone Deaf", 17);
		CD cd3 = new CD(5755, 8, 15, "turkish", "Higher", 11);
		CD cd4 = new CD(1358, 1, 21, "italian", "Key (skit)", 3);
		CD cd5 = new CD(7856, 2, 36, "english", "Killer", 8);

		Video video1 = new Video(7654, 3, 10, "Premonition", 20, 1996);
		Video video2 = new Video(6543, 5, 5, "Unaccommodating", 17, 2001);
		Video video3 = new Video(1247, 8, 15, "Godzilla", 11, 2020);
		Video video4 = new Video(9875, 1, 21, "Darkness", 3, 1995);
		Video video5 = new Video(2479, 2, 36, "Stepdad", 8, 1976);
		Lager lager = new Lager(15);
		lager.legeAnArtikel(book1);
		lager.legeAnArtikel(book2);
		lager.legeAnArtikel(book3);
		lager.legeAnArtikel(book4);
		lager.legeAnArtikel(book5);
		lager.legeAnArtikel(cd1);
		lager.legeAnArtikel(cd2);
		lager.legeAnArtikel(cd3);
		lager.legeAnArtikel(cd4);
		lager.legeAnArtikel(cd5);
		lager.legeAnArtikel(video1);
		lager.legeAnArtikel(video2);
		lager.legeAnArtikel(video3);
		lager.legeAnArtikel(video4);
		lager.legeAnArtikel(video5);
		return lager;
	}

	private Lager lager = null;

	/** Methode zum interaktiven Testen der Artikel Klasse */
	public void readInput() {
		try (final Scanner scanner = new Scanner(System.in)) {
			while (true) {
				final String input = readString(scanner, "Command: ").toLowerCase().trim();
				if (lager == null && (!input.equalsIgnoreCase("erstellen") && !input.equalsIgnoreCase("stop"))) {
					System.out.println("Es wurde noch kein Lager mit *erstellen* erstellt!");
					continue;
				}
				executeCommand(scanner, input);
			}
		}
	}

	/**
	 * F�hrt den eingebenen Befehl aus
	 *
	 * @param scanner das genutzte Scanner Objekt
	 * @param input   der eingebene Command
	 */
	private void executeCommand(final Scanner scanner, String input) {
		try {
			switch (input) {
				case "erstellen":
					if (lager != null) {
						final String mode = readString(scanner,
								"Soll das aktuelle Lager �berschrieben werden? (Ja/Nein)").toLowerCase();
						if (mode.equals("nein") || !mode.equals("ja")) {
							System.out.println("Lager wird nicht �berschrieben");
							break;
						}
					}
					lager = new Lager(readInt(scanner, "Gr��e des Lagers: "));
					break;
				case "anlegen":
					lager.legeAnArtikel(createArtikel(scanner));
					break;
				case "entferne":
					lager.entferneArtikel(readInt(scanner, "Artikelnummer: "));
					break;
				case "zugang":
					lager.bucheZugang(readInt(scanner, "Artikelnummer: "), readInt(scanner, "Menge: "));
					break;
				case "abgang":
					lager.bucheAbgang(readInt(scanner, "Artikelnummer: "), readInt(scanner, "Menge: "));
					break;
				case "preis":
					lager.aenderePreisAllerArtikel(readDouble(scanner, "Prozent: "));
					break;
				case "get":
					final String mode = readString(scanner, "Index/Nummer: ").toLowerCase();
					Artikel artikel = null;
					if (mode.equals("index")) {
						artikel = lager.getArtikel(readInt(scanner, "Index: "));
					} else if (mode.equals("nummer")) {
						artikel = lager.getArtikelByNr(readInt(scanner, "Nummer: "));
					}
					System.out.println(artikel == null ? artikel : "Artikel existiert nicht");
					break;
				case "artikelanzahl":
					System.out.println(lager.getArtikelAnzahl());
					break;
				case "lagergr��e":
					System.out.println(lager.getLagerGroesse());
					break;
				case "ausgeben":
					System.out.println(lager);
					break;
				case "bestandsliste":
					lager.ausgebenBestandsListe();
					break;
				case "stop":
					System.exit(0);

				default:
					System.out.printf("Der Befehl %s existiert nicht\n", input);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Fehler: " + exception.getMessage());
		}
	}

	/**
	 * Erstellt ein neues Artikel Objekt
	 *
	 * @param scanner das genutzte Scanner Objekt
	 * @return das erstellte Artikel Objekt
	 */
	private Artikel createArtikel(Scanner scanner) {
		final int artikelnr = readInt(scanner, "Artikelnummer: ");
		final int bestand = readInt(scanner, "Bestand: ");
		final double preis = readDouble(scanner, "Preis: ");
		final String type = readString(scanner, "Typ (CD/Video/Buch/Artikel) : ");
		switch (type.toLowerCase()) {
			case "cd":
				final String interpret = readString(scanner, "Interpret: ");
				final String titel = readString(scanner, "Titel: ");
				final int anzahlTitel = readInt(scanner, "Anzahl der Titel: ");
				return new CD(artikelnr, bestand, preis, interpret, titel, anzahlTitel);
			case "video":
				final String videoTitel = readString(scanner, "Titel: ");
				final int spieldauer = readInt(scanner, "Spieldauer: ");
				final int jahr = readInt(scanner, "Jahr: ");
				return new Video(artikelnr, bestand, preis, videoTitel, spieldauer, jahr);
			case "buch":
				final String bookTitel = readString(scanner, "Titel: ");
				final String autor = readString(scanner, "Autor: ");
				final String verlag = readString(scanner, "Verlag: ");
				return new Buch(artikelnr, bestand, preis, autor, bookTitel, verlag);
			case "artikel":
				return new Artikel(artikelnr, readString(scanner, "Art: "), bestand, preis);
			default:
				return new Artikel(artikelnr, readString(scanner, "Art: "), bestand, preis);
		}
	}

	/**
	 * Wartet bis der Nutzer eine Nummer eingegeben hat
	 *
	 * @param scanner
	 * @param promtMessage
	 * @return die eingegebene Nummer
	 */
	private int readInt(final Scanner scanner, final String promtMessage) {
		System.out.print(promtMessage);
		while (!scanner.hasNextInt()) {
			scanner.nextLine();
			System.out.print(promtMessage);
		}
		final int number = scanner.nextInt();
		scanner.nextLine();
		return number;
	}

	/**
	 * Wartet bis der Nutzer eine Nummer eingegeben hat
	 *
	 * @param scanner
	 * @param promtMessage
	 * @return die eingegebene Nummer
	 */
	private double readDouble(final Scanner scanner, final String promtMessage) {
		System.out.print(promtMessage);
		while (!scanner.hasNextDouble()) {
			scanner.nextLine();
			System.out.print(promtMessage);
		}
		final double number = scanner.nextDouble();
		scanner.nextLine();
		return number;
	}

	/**
	 * @param scanner      das genutzte Scanner Objekt
	 * @param promtMessage
	 * @return der eingegebene String
	 */
	private String readString(final Scanner scanner, final String promtMessage) {
		System.out.print(promtMessage);
		return scanner.nextLine();
	}

	public static void printArray(Artikel[] artikels) {
		for (Artikel artikel : artikels) {
			System.out.print(artikel.getClass());
			System.out.println(artikel);
		}
	}
	/**
	 * 
	 * @param lager
	 * @return sorted according to price
	 */
	public static Artikel[] testGetSorted(Lager lager) {
		System.out.println("Example sort with price of articles");
		BiPredicate<Artikel, Artikel> sortCriteria = (artikel1, artikel2) -> {
			return artikel1.getPrice() > artikel2.getPrice();
		};
		return lager.getSorted(sortCriteria);
	}

	public static void testApplyToArticles(Lager lager) {
		Consumer<Artikel> sqrt = artikel -> artikel.setPrice(Math.pow(artikel.getPrice(), 2));
		lager.applyToArticles(sqrt);
	}
	/**
	 * 
	 * @param ueb18 functions to apply
	 * @param lager	lager object
	 * prints the array after given filter predicates, author, max and min price
	 */
	public static void filterWithAuthorAndPrice(Ueb18Fassade ueb18, Lager lager){
		System.out.println("Give the name of the author");
		Scanner stringReader = new Scanner(System.in);
		Scanner doubleReader = new Scanner(System.in);
		String authorName = stringReader.nextLine();
		System.out.println("Write the minimum price");
		double minPrice = doubleReader.nextDouble();
		System.out.println("Write the max price");
		double maxPrice = doubleReader.nextDouble();
		printArray(ueb18.aufgabe_h_vi(lager, authorName, minPrice, maxPrice));
	
	}

	public static void newDialog() {
		final Scanner scanner = new Scanner(System.in);
		Ueb18Fassade ueb18 = new Ueb18Fassade();
		Lager lager = createAndGetLager();
		System.out.println("For the ease of the test, the default array will be shown");
		printArray(lager.getArtikels());
		boolean END = false;
		final int AUFGABE_A = 1;
		final int AUFGABE_B = 2;
		final int AUFGABE_C_I = 3;
		final int AUFGABE_C_II = 4;
		final int AUFGABE_C_III = 5;
		final int AUFGABE_C_IV = 6;

		final int AUFGABE_H_I = 7;
		final int AUFGABE_H_II = 8;
		final int AUFGABE_H_III = 9;
		final int AUFGABE_H_IV = 10;
		final int AUFGABE_H_V = 11;
		final int AUFGABE_H_VI = 12;

		while (!END) {
			System.out.println("please choose and Option");
			System.out.println("-1: Exit");
			System.out.println("1: AUFGABE_A - Sort with price");
			System.out.println("2: AUFGABE_B - Take square of all the prices!");
			System.out.println("3: AUFGABE_C_I - Aufgabe c-i-a, sort unterkategorie - Aufgabe c-i-b, sort with bestand - Aufgabe c-i-c, sort with price ");
			System.out.println("4: AUFGABE_C_II - reduzieren price %10");
			System.out.println("5: AUFGABE_C_III - add suffix Sonderangebot");
			System.out.println("6: AUFGABE_C_IV - combine ii and iii");
			System.out.println("7: AUFGABE_H_I - increase cd prices with %10");
			System.out.println("8: AUFGABE_H_II - decrease  the  prices with %5,if there are less than 2 items left");
			System.out.println("9: AUFGABE_H_III - decrease the  prices of author with 5%");
			System.out.println("10: AUFGABE_H_IV - combine 1 and 2");
			System.out.println("11: AUFGABE_H_V - sort all books by author");
			System.out.println("12: AUFGABE_H_VI - filter books with author, max  and min price");
			final int input = scanner.nextInt();
			switch (input) {
				case -1:
					END = true;
					scanner.close();
					break;
				case AUFGABE_A:
					printArray((testGetSorted(lager)));
					break;
				case AUFGABE_B:
					System.out.println("Take square of all the prices!");
					testApplyToArticles(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_C_I:
					System.out.println("Aufgabe c-i-a, sort unterkategorie");
					printArray(ueb18.aufgabe_c_i_a(lager));
					

					System.out.println("Aufgabe c-i-b, sort with bestand");
					printArray(ueb18.aufgabe_c_i_b(lager));

					System.out.println("Aufgabe c-i-c, sort with price");
					printArray(ueb18.aufgabe_c_i_c(lager));
					break;
				case AUFGABE_C_II:
					System.out.println("Aufgabe c-ii, reduzieren price %10");
					ueb18.aufgabe_c_ii(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_C_III:
					System.out.println("Aufgae c-iii, add suffix Sonderangebot");
					ueb18.aufgabe_c_iii(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_C_IV:
					System.out.println("Aufgae c-iv, combine ii and iii");
					ueb18.aufgabe_c_iv(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_H_I:
					System.out.println("aufgabe h-i, increase cd prices with %10");
					ueb18.aufgabe_h_i(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_H_II:
					System.out
							.println("aufgabe h-ii, decrease  the  prices with %5,if there are less than 2 items left");
					ueb18.aufgabe_h_ii(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_H_III:
					System.out.println("aufgabe h-iii, decrease the  prices of author with 5%");
					ueb18.aufgabe_h_iii(lager, "Apoorva Kumar");
					printArray(lager.getArtikels());
					break;
				case AUFGABE_H_IV:
					System.out.println("aufgabe h-iv, combine 1 and 2,Increase price of cds with 10% and decrease price of articles, decrease the price with 5%, if the amount less than or equal to 2");
					ueb18.aufgabe_h_iv(lager);
					printArray(lager.getArtikels());
					break;
				case AUFGABE_H_V:
					System.out.println("aufgabe h-v, sort all books by author");
					printArray(ueb18.aufgabe_h_v(lager));
					break;
				case AUFGABE_H_VI:
					filterWithAuthorAndPrice(ueb18, lager);
					break;
			}

		}
	}

	public static void main(String... args) {
		newDialog();
	}

}