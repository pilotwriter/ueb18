package ueb18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
public class Lager {

	private Artikel[] artikelArray;
	private Predicate<Artikel>[] filterArg;

	public Lager(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Lager muss gr��er als 0 sein");
		}
		artikelArray = new Artikel[size];
	}

	public Artikel[] getArtikels() {
		return this.artikelArray;
	}

	public Lager() {
		this(10);
	}

	public void legeAnArtikel(Artikel artikel) {
		if (isFull()) {
			throw new IllegalArgumentException("Lager ist voll");
		}
		if (getArtikelByNr(artikel.getArtikelNr()) != null) {
			throw new IllegalArgumentException(String
					.format("Der Artikel mit der Nummer %d existiert bereits in dem Lager", artikel.getArtikelNr()));
		}
		artikelArray[getArtikelAnzahl()] = artikel;
	}

	public void entferneArtikel(int artikelNr) {
		for (int i = 0; i < artikelArray.length; i++) {
			if (artikelArray[i] != null && artikelArray[i].getArtikelNr() == artikelNr) {
				artikelArray[i] = artikelArray[getArtikelAnzahl() - 1];
				artikelArray[getArtikelAnzahl() - 1] = null;
				return;
			}
		}
		throw new NoSuchElementException(String.format("Der Artikel mit der Nummer %d existiert nicht", artikelNr));
	}

	public void bucheZugang(int artikelNr, int menge) {
		Artikel artikel = getArtikelByNr(artikelNr);
		if (artikel == null) {
			throw new NoSuchElementException(String.format("Der Artikel mit der Nummer %d existiert nicht", artikelNr));
		}
		artikel.bucheZugang(menge);
	}

	public void bucheAbgang(int artikelNr, int menge) {
		Artikel artikel = getArtikelByNr(artikelNr);
		if (artikel == null) {
			throw new NoSuchElementException(String.format("Der Artikel mit der Nummer %d existiert nicht", artikelNr));
		}
		artikel.bucheAbgang(menge);
	}

	public void aenderePreisAllerArtikel(double prozent) {
		for (Artikel artikel : artikelArray) {
			if (artikel != null) {
				artikel.aenderePreis(prozent);
			}
		}
	}

	public Artikel getArtikel(int index) {
		Artikel artikel = artikelArray[index];
		return artikel == null ? null : artikel;
	}

	public int getArtikelAnzahl() {
		int count = 0;
		for (Artikel artikel : artikelArray) {
			if (artikel != null) {
				count++;
			}
		}
		return count;
	}

	public int getLagerGroesse() {
		return artikelArray.length;
	}

	public Artikel getArtikelByNr(int artikelNr) {
		for (Artikel artikel : artikelArray) {
			if (artikel != null && artikel.getArtikelNr() == artikelNr) {
				return artikel;
			}
		}
		return null;
	}

	public boolean isFull() {
		return !((getArtikelAnzahl()) < getLagerGroesse());
	}

	public void ausgebenBestandsListe() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Artnr Beschreibung Preis Bestand Gesamt\n");
		double sumPrice = 0;
		for (Artikel artikel : artikelArray) {
			if (artikel != null) {
				sumPrice += artikel.getBestand() * artikel.getPrice();
				stringBuilder
						.append(String.format("%d %s %f %d %f\n", artikel.getArtikelNr(), artikel.getBeschreibung(),
								artikel.getPrice(), artikel.getBestand(), artikel.getBestand() * artikel.getPrice()));
			}
		}
		stringBuilder.append("Gesamtwer: " + sumPrice);
		System.out.println(stringBuilder.toString());
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		Arrays.asList(artikelArray).stream().filter(artikel -> artikel != null)
				.forEach(artikel -> stringBuilder.append(artikel + "\n"));
		return stringBuilder.toString();
	}

	/**
	 * sort the Array of articles according to given parameter
	 * 
	 * @param BiPredicate as sort criteria.
	 * @return Artikel[] sorted artikel array
	 */
	public Artikel[] getSorted(BiPredicate<Artikel, Artikel> criteria) {
		Artikel[] sortedArtikels = new Artikel[getLagerGroesse()];
		for (int i = 0; i < this.artikelArray.length; i++) {
			sortedArtikels[i] = this.artikelArray[i];
		}

		for (int i = 0; i < sortedArtikels.length - 1; i++) {
			for (int j = 0; j < sortedArtikels.length - i - 1; j++) {
				if (criteria.test(sortedArtikels[j], sortedArtikels[j + 1])) {
					Artikel temp = sortedArtikels[j];
					sortedArtikels[j] = sortedArtikels[j + 1];
					sortedArtikels[j + 1] = temp;
				}
			}
		}

		return sortedArtikels;
	}

	/**
	 * apply Consumer.accept method to the all elements of the array
	 * 
	 * @param Consumer<Artikel>
	 *
	 */
	public void applyToArticles(Consumer<Artikel> function) {
		for (Artikel artikel : this.artikelArray) {
			function.accept(artikel);
		}
	}

	/**
	 * sort the Array of articles according to given parameter
	 * 
	 * @param Predicate as filter criteria.
	 * @return Artikel[] filtered artikel array
	 */
	public Artikel[] filter(Predicate<Artikel> filterPredicate) {
		return (Artikel[]) Arrays.asList(this.artikelArray).stream().filter(filterPredicate).toArray();
	}

	/**
	 * filter the Array of articles according to given parameter and run operations
	 * on them
	 * 
	 * @param Consumer<Artikel>
	 * @param Predicate<Artikel>
	 * @return Artikel[] filtered and operated artikel array
	 */
	public void applyToSomeArticels(Consumer<Artikel> operation, Predicate<Artikel> filter) {
		Arrays.asList(this.artikelArray).stream().filter(filter).forEach(artikel -> operation.accept(artikel));
		;
	}

	/**
	 * sort and filterthe Array of articles according to given parameter
	 * @param Predicate as filter criteria and Comparator as a sort criteria.
	 * @return Artikel[] filtered and sorted artikel array
 	 */
	public Artikel[] getArticles(Predicate<Artikel> searchCriteria, Comparator<Artikel> compareParameter) {
		List<Artikel> sortedArtikels = new ArrayList<Artikel>();
		Arrays.asList(this.artikelArray).
		stream().
		filter(searchCriteria).
		sorted(compareParameter).
		forEach((artikel) -> sortedArtikels.add(artikel));;
		Artikel[] sortedArtikelArray = new Artikel[sortedArtikels.size()];
		for(int i = 0; i < sortedArtikels.size();i++) {
			sortedArtikelArray[i] = sortedArtikels.get(i);
		}
		return sortedArtikelArray;
	}

	/**
	 * sort the Array of articles according to given parameter
	 * 
	 * @param Predicate... as filter criteria.
	 * @return Artikel[] filtered artikel array which is filtered by all criterias
	 */
	public Artikel[] filterAll(Predicate<Artikel>... filterArg) {
		Artikel[] artikels = new Artikel[this.artikelArray.length];
		for (int i = 0; i < this.artikelArray.length; i++) {
			artikels[i] = this.artikelArray[i];
		}
		for (Predicate<Artikel> filter : filterArg) {
			artikels = (Artikel[]) Arrays.asList(artikels).stream().filter(filter).toArray(Artikel[]::new);
		}
		return artikels;
	}
}