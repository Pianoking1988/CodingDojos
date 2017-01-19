package de.heinemann.dojo.discount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscountStrategyGenre extends DiscountStrategy {

	public DiscountStrategyGenre() {
		addNextDiscounts(10, BigDecimal.ZERO);
		addNextDiscounts(1, new BigDecimal("0.1"));
	}

	@Override
	public void assignDiscounts(List<DiscountBook> discountBooks) {
		Map<Genre, List<DiscountBook>> genreBooks = discountBooks.stream()
				.collect(Collectors.groupingBy(discountBook -> discountBook.getBook().getGenre()));
		
		for (List<DiscountBook> genreDiscountBooks : genreBooks.values()) {
			super.assignDiscounts(genreDiscountBooks );
		}
	}
	
}
