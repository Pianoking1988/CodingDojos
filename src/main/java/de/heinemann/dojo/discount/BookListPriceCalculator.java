package de.heinemann.dojo.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Calculates overall price for a list of books.
 * 
 * @author IKS Dojo Team
 */
public class BookListPriceCalculator {
	
	private static final Discounts GENRE_DISCOUNTS = new Discounts()
			.addNextDiscounts(10, BigDecimal.ZERO)
			.addNextDiscounts(1, new BigDecimal("0.1"));

	private static final Discounts SERIES_DISCOUNTS = new Discounts()
			.addNextDiscounts(1, BigDecimal.ZERO)
			.addNextDiscounts(9, new BigDecimal("0.1"))
			.addNextDiscounts(40, new BigDecimal("0.2"))
			.addNextDiscounts(1, new BigDecimal("0.25"));

	/**
	 * Calculates the overall price considering possible discounts.
	 * 
	 * @param bookList
	 * @return calculated price
	 */
	public BigDecimal calcPriceWithDiscount(List<Book> bookList) {
		
		List<DiscountForBook> discountsForBook = new ArrayList<DiscountForBook>();
		
		bookList.forEach(book -> discountsForBook.add(new DiscountForBook(book, BigDecimal.ZERO)));
		
		Map<Genre, List<DiscountForBook>> genreMap = discountsForBook.stream()
				.collect(Collectors.groupingBy(discountForBook -> discountForBook.getBook().getGenre()));
		
		for (List<DiscountForBook> discountsForBookPerGenre : genreMap.values()) {
			calculateDiscountsForGenre(discountsForBookPerGenre);
		}
		
		return discountsForBook.stream()
				.map(DiscountForBook::calculateNetPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void calculateDiscountsForGenre(List<DiscountForBook> discountsForBookPerGenre) {
		for (int i = 0; i < discountsForBookPerGenre.size(); i++) {
			DiscountForBook discountForBook = discountsForBookPerGenre.get(i);
			discountForBook.setDiscountIfGreater(GENRE_DISCOUNTS.getDiscount(i));
		}
		
		Map<String, List<DiscountForBook>> seriesMap = discountsForBookPerGenre.stream()
				.filter(discountForBook -> discountForBook.getBook().getSeries() != null)
				.collect(Collectors.groupingBy(discountForBook -> discountForBook.getBook().getSeries()));
		
		for (List<DiscountForBook> discountsForBookPerSeries : seriesMap.values()) {
			addPricesForSeries(discountsForBookPerSeries);
		}
	}

	private void addPricesForSeries(List<DiscountForBook> discountsForBookPerSeries) {
		for (int i = 0; i < discountsForBookPerSeries.size(); i++) {
			DiscountForBook discountForBook = discountsForBookPerSeries.get(i);
			discountForBook.setDiscountIfGreater(SERIES_DISCOUNTS.getDiscount(i));
		}
	}
}