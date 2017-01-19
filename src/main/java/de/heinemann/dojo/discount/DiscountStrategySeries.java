package de.heinemann.dojo.discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscountStrategySeries extends DiscountStrategy {

	public DiscountStrategySeries() {
		addNextDiscounts(1, BigDecimal.ZERO);
		addNextDiscounts(9, new BigDecimal("0.1"));
		addNextDiscounts(40, new BigDecimal("0.2"));
		addNextDiscounts(1, new BigDecimal("0.25"));
	}

	@Override
	public void assignDiscounts(List<DiscountBook> discountBooks) {
		Map<Genre, List<DiscountBook>> genreBooks = discountBooks.stream()
				.collect(Collectors.groupingBy(discountBook -> discountBook.getBook().getGenre()));
		
		for (List<DiscountBook> genreDiscountBooks : genreBooks.values()) {
			assignDiscountsPerSeries(genreDiscountBooks);
		}
	}

	private void assignDiscountsPerSeries(List<DiscountBook> genreDiscountBooks) {
		Map<String, List<DiscountBook>> seriesBooks = genreDiscountBooks.stream()
				.filter(discountBook -> discountBook.getBook().getSeries() != null)
				.collect(Collectors.groupingBy(discountBook -> discountBook.getBook().getSeries()));
		
		for (List<DiscountBook> seriesDiscountBooks : seriesBooks.values()) {
			super.assignDiscounts(seriesDiscountBooks);
		}
	}	

}
