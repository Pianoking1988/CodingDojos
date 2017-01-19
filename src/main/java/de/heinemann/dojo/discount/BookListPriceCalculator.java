package de.heinemann.dojo.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates overall price for a list of books.
 * 
 * @author IKS Dojo Team
 */
public class BookListPriceCalculator {
	
	private final DiscountStrategy[] discountStrategies = new DiscountStrategy[] {
		new DiscountStrategyGenre(),
		new DiscountStrategySeries()
	};
	
	/**
	 * Calculates the overall price considering possible discounts.
	 * 
	 * @param bookList
	 * @return calculated price
	 */
	public BigDecimal calcPriceWithDiscount(List<Book> bookList) {
		List<DiscountBook> discountBooks = new ArrayList<DiscountBook>();
		bookList.forEach(book -> discountBooks.add(new DiscountBook(book, BigDecimal.ZERO)));
		
		for (DiscountStrategy discountStrategy : discountStrategies) {
			discountStrategy.assignDiscounts(discountBooks);
		}
				
		return discountBooks.stream()
				.map(DiscountBook::calculateNetPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}