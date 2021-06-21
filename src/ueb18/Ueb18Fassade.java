package ueb18;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Comparator;

/**
 * <p>
 * Diese Klasse ist eine Fassade, hinter der Sie Ihre Loesung verstecken. Der
 * Test ruft nur die hier definierten Schnittstellenmethoden auf. Loeschen Sie
 * bitte keine Methoden. Wenn Sie eine Methode nicht implementieren moechten,
 * lassen Sie bitte die leere Implementierung stehen. Innerhalb der Methoden und
 * in allen anderen Klassen, die Sie noch benoetigen, haben Sie alle Freiheiten.
 * </p>
 * 
 * <p>
 * Wenn Sie Ihre Loesung mit JUnit testen moechten, testen Sie diese
 * Schnittstellenmethoden.
 * </p>
 * 
 * @author christopher
 *
 */
public class Ueb18Fassade {
	/**
	 * Loest die Aufgabe (c) i. <br />
	 * Sortierung nach den folgenden Kriterien:
	 * <ol>
	 * <li>Unterkategorie (alphabetisch)</li>
	 * <li>Bestand</li>
	 * <li>Preis</li>
	 * </ol>
	 * 
	 * @param lager Das Lager mit der unsortierten Artikelliste.
	 * @return Die sortierte Artikelliste.
	 */
	public Artikel[] aufgabe_c_i_a(Lager lager) {
		BiPredicate<Artikel, Artikel> predicateArt = (artikel1, artikel2) -> {
			return artikel1.getArt().compareTo(artikel2.getArt()) > 0 ? true : false;
		};

		return lager.getSorted(predicateArt);
	}

	public Artikel[] aufgabe_c_i_b(Lager lager) {
		// Sort according to artikel bestand
		BiPredicate<Artikel, Artikel> predicateBestand = (artikel1,
				artikel2) -> artikel1.getBestand() > artikel2.getBestand();
		return lager.getSorted(predicateBestand);
	}

	public Artikel[] aufgabe_c_i_c(Lager lager) {
		BiPredicate<Artikel, Artikel> predicatePrice = (artikel1,
				artikel2) -> artikel1.getPrice() > artikel2.getPrice();
		return lager.getSorted(predicatePrice);
	}

	/**
	 * Loest die Aufgabe (c) ii. <br />
	 * Der Preis aller Artikel wird um 10% reduziert.
	 * 
	 * @param lager Das Lager mit den Artikeln, deren Preise geaendert werden
	 *              sollen.
	 */
	public void aufgabe_c_ii(Lager lager) {
		final double DISCOUNT_AMOUNT = 0.1;
		Consumer<Artikel> changePriceWithPercentage = artikel -> artikel
				.setPrice(artikel.getPrice() - (artikel.getPrice() * DISCOUNT_AMOUNT));
		lager.applyToArticles(changePriceWithPercentage);
	}

	/**
	 * Loest die Aufgabe (c) iii. <br />
	 * An alle Artikelbezeichnungen wird das Suffix (Sonderangebot) angehaengt.
	 * 
	 * @param lager Das Lager mit den Artikeln, deren Bezeichnungen geaendert werden
	 *              sollen.
	 */
	public void aufgabe_c_iii(Lager lager) {
		final String SUFFIX = "Sonderangebot";
		Consumer<Artikel> changeSuffix = artikel -> artikel.setArt(artikel.getBeschreibung() + "  "+ SUFFIX);
		lager.applyToArticles(changeSuffix);
	}

	/**
	 * Loest die Aufgabe (c) iv. <br />
	 * Die beiden Operatoren aus den Aufgabenteilen ii und iii werden konkateniert,
	 * d.h. alle Preise werden um 10 % reduziert und alle Bezeichnungen werden um
	 * das Suffix (Sonderangebot) erweitert.
	 * 
	 * @param lager Das Lager mit den Artikeln, deren Preise und Bezeichnungen
	 *              geaendert werden sollen.
	 */
	public void aufgabe_c_iv(Lager lager) {
		final String SUFFIX = "Sonderangebot";
		final double DISCOUNT_AMOUNT = 0.1;
		Consumer<Artikel> changePriceAndSuffix = artikel -> {
			artikel.setArt(artikel.getBeschreibung() + "  "+  SUFFIX);
			artikel.setPrice(artikel.getPrice() - (artikel.getPrice() * DISCOUNT_AMOUNT));
		};
		lager.applyToArticles(changePriceAndSuffix);
	}

	/**
	 * Loest die Aufgabe (h) i. <br />
	 * Der Preis aller CDs wird um 10 % erhoeht.
	 * 
	 * @param lager Das Lager mit den Artikeln. Die Aenderungen werden direkt in
	 *              diesem Objekt vorgenommen.
	 */
	public void aufgabe_h_i(Lager lager) {
//		lager.applyToSomeArticles(a -> a instanceof CD, a -> a.aenderePreis(10));
		final double INCREASE_PERCENTAGE = 0.1;
		Consumer<Artikel> increasePrice = artikel -> artikel
				.setPrice(artikel.getPrice() + (artikel.getPrice() * INCREASE_PERCENTAGE));
		Predicate<Artikel> filter = artikel -> {
			if (artikel instanceof CD) {
				return true;
			}
			return false;
		};
		lager.applyToSomeArticels(increasePrice, filter);
	}

	/**
	 * Loest die Aufgabe (h) ii. <br />
	 * Der Preis aller Artikel, von denen der Bestand hoechstes zwei ist, wird um 5
	 * % reduziert.
	 * 
	 * @param lager Das Lager mit den Artikeln. Die Aenderungen werden direkt in
	 *              diesem Objekt vorgenommen.
	 */
	public void aufgabe_h_ii(Lager lager) {
		final double DECREASE_PERCENTAGE = 0.05;
		Consumer<Artikel> decreasePrice = artikel -> artikel
				.setPrice(artikel.getPrice() - (artikel.getPrice() * DECREASE_PERCENTAGE));
		Predicate<Artikel> filter = artikel -> artikel.getBestand() <= 2;
		lager.applyToSomeArticels(decreasePrice, filter);
	}

	/**
	 * Loest die Aufgabe (h) iii. <br />
	 * Der Preis der Buecher eines bestimmten Autors wird um 5 % reduziert.
	 * 
	 * @param lager          Das Lager mit den Artikeln. Die Aenderungen werden
	 *                       direkt in diesem Objekt vorgenommen.
	 * @param gesuchterAutor Der Autor, dessen Buecher guenstiger werden sollen.
	 */
	public void aufgabe_h_iii(Lager lager, String gesuchterAutor) {
		final double DECREASE_PERCENTAGE = 0.05;
		Consumer<Artikel> decreasePrice = buch -> buch
				.setPrice(buch.getPrice() - (buch.getPrice() * DECREASE_PERCENTAGE));
		Predicate<Artikel> filter = buch -> {
			if (buch instanceof Buch) {
				final Buch parsedBuch = (Buch) buch;
				return ((Buch) buch).getAutor().equalsIgnoreCase(gesuchterAutor);
			}
			return false;
		};
		lager.applyToSomeArticels(decreasePrice, filter);
	}

	/**
	 * Loest die Aufgabe (h) iv. <br />
	 * Der Preis aller CDs wird um 10 % erhoeht und der Preis aller Artikel, von
	 * denen der Bestand hoechstes zwei ist, wird um 5 % reduziert.
	 * 
	 * @param lager Das Lager mit den Artikeln. Die Aenderungen werden direkt in
	 *              diesem Objekt vorgenommen.
	 */
	public void aufgabe_h_iv(Lager lager) {
		final double INCREASE_PRICE = 0.1;
		final double DECREASE_PRICE = 0.05;

		Predicate<Artikel> filter = artikel -> {
			if (artikel instanceof CD) {
				return true;
			} else {
				return artikel.getBestand() <= 2;
			}
		};

		Consumer<Artikel> increaseOrDecreasePrice = artikel -> {
			if (artikel instanceof CD) {
				artikel.setPrice(artikel.getPrice() + (artikel.getPrice() * INCREASE_PRICE));
			} else {
				artikel.setPrice(artikel.getPrice() - (artikel.getPrice() * DECREASE_PRICE));
			}
		};
		lager.applyToSomeArticels(increaseOrDecreasePrice, filter);
	}

	/**
	 * Loest die Aufgabe (h) v. <br />
	 * 
	 * @param lager Das Lager mit den Artikeln.
	 * @return Eine Liste mit allen Buechern, sortiert nach den Namen der Autoren.
	 */
	public Artikel[] aufgabe_h_v(Lager lager) {
		Predicate<Artikel> filter = buch -> buch instanceof Buch;
		Comparator<Artikel> compare = (buch1, buch2) -> {
			final Buch parsedBuch1 = (Buch) buch1;
			final Buch parsedBuch2 = (Buch) buch2;
			return parsedBuch1.getAutor().compareToIgnoreCase(parsedBuch2.getAutor());
		};

		return lager.getArticles(filter, compare);
	}

	/**
	 * Loest die Aufgabe (h) vi. <br />
	 * 
	 * @param lager          Das Lager, dessen Artikel gefiltert werden sollen.
	 * @param gesuchterAutor Der Autor, nach dem gefiltert werden soll.
	 * @param minPreis       Der kleinste Preis, den die zu filternden Buecher haben
	 *                       sollen.
	 * @param maxPreis       Der hoechste Preis, den die zu filternden Buecher haben
	 *                       sollen.
	 * @return Alle Buecher vom Autor autor und mit einem Preis, der zwischen
	 *         minPreis und maxPreis liegt.
	 */
	public Artikel[] aufgabe_h_vi(Lager lager, String gesuchterAutor, double minPreis, double maxPreis) {
		Predicate<Artikel> getBooks = buch -> buch instanceof Buch;
		Predicate<Artikel> autorFilter = buch -> {
			final Buch parsedBuch = (Buch) buch;
			return parsedBuch.getAutor().trim().equalsIgnoreCase(gesuchterAutor.trim());
		};
		Predicate<Artikel> preiseFilter = buch -> buch.getPrice() >= minPreis && buch.getPrice() <= maxPreis;

		return lager.filterAll(getBooks, autorFilter, preiseFilter);
	}
}